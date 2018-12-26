package com.tmj.tms.finance.bussinesslayer.managerimpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.LocalCreditNoteManagementControllerManager;
import com.tmj.tms.finance.datalayer.modal.*;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteFinanceChargeDetailForExpVoucher;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteFinancialChargeDetail;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteSummaryDetail;
import com.tmj.tms.finance.datalayer.service.*;
import com.tmj.tms.finance.utility.EntryType;
import com.tmj.tms.finance.utility.ExchangeRateConversion;
import com.tmj.tms.finance.utility.FinancialNumberGenerator;
import com.tmj.tms.finance.utility.FunctionalType;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.TaxRegistrationService;
import com.tmj.tms.master.datalayer.service.TaxTypeService;
import com.tmj.tms.utility.NumberFormatter;
import com.tmj.tms.utility.ReportGenerator;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LocalCreditNoteManagementControllerManagerImpl implements LocalCreditNoteManagementControllerManager {

    private final LocalInvoiceService localInvoiceService;
    private final ExpenseVoucherService expenseVoucherService;
    private final TaxRegistrationService taxRegistrationService;
    private final LocalCreditNoteHeaderService localCreditNoteHeaderService;
    private final FinancialChargeService financialChargeService;
    private final ExchangeRateConversion exchangeRateConversion;
    private final FinancialChargeDetailService financialChargeDetailService;
    private final ReportGenerator reportGenerator;
    private final StakeholderService stakeholderService;
    private final ModuleService moduleService;
    private final FinancialNumberGenerator financialNumberGenerator;
    private final TaxTypeService taxTypeService;

    @Autowired
    public LocalCreditNoteManagementControllerManagerImpl(LocalInvoiceService localInvoiceService,
                                                          ExpenseVoucherService expenseVoucherService,
                                                          TaxRegistrationService taxRegistrationService,
                                                          LocalCreditNoteHeaderService localCreditNoteHeaderService,
                                                          FinancialChargeService financialChargeService,
                                                          ExchangeRateConversion exchangeRateConversion,
                                                          FinancialChargeDetailService financialChargeDetailService,
                                                          ReportGenerator reportGenerator,
                                                          StakeholderService stakeholderService,
                                                          ModuleService moduleService,
                                                          FinancialNumberGenerator financialNumberGenerator,
                                                          TaxTypeService taxTypeService) {
        this.localInvoiceService = localInvoiceService;
        this.expenseVoucherService = expenseVoucherService;
        this.taxRegistrationService = taxRegistrationService;
        this.localCreditNoteHeaderService = localCreditNoteHeaderService;
        this.financialChargeService = financialChargeService;
        this.exchangeRateConversion = exchangeRateConversion;
        this.financialChargeDetailService = financialChargeDetailService;
        this.reportGenerator = reportGenerator;
        this.stakeholderService = stakeholderService;
        this.moduleService = moduleService;
        this.financialNumberGenerator = financialNumberGenerator;
        this.taxTypeService = taxTypeService;
    }

    @Override
    public ResponseObject saveLocalCreditNote(LocalCreditNoteHeader localCreditNoteHeader, HttpServletRequest request) {
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        ResponseObject responseObject;
        LocalCreditNoteSummaryDetail localCreditNoteSummaryDetail = null;
        if (localCreditNoteHeader.getLocalInvoiceSeq() != null) {
            localCreditNoteSummaryDetail = this.getLocalCreditNoteSummaryForLocalInvoice(localCreditNoteHeader, request);
            Double vatDifferent = NumberFormatter.round(localCreditNoteSummaryDetail.getTotalInvoicedVatAmount(), 2) - NumberFormatter.round(localCreditNoteSummaryDetail.getTotalCurrentVatAmount(), 2);
            LocalInvoice localInvoice = this.localInvoiceService.findOne(localCreditNoteHeader.getLocalInvoiceSeq());
            Stakeholder dbStakeholder = this.stakeholderService.findOne(localInvoice.getStakeholderSeq());
            if (dbStakeholder.getStakeholderSvatTypeSeq() != null
                    && dbStakeholder.getStakeholderSvatTypeSeq().equals(0)
                    && dbStakeholder.getSuspendedTaxRegNo() != null
                    && vatDifferent > 0) {
                localCreditNoteHeader.setsVatStatus(1);
            } else {
                localCreditNoteHeader.setsVatStatus(0);
            }

            List<LocalCreditNoteHeader> localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByLocalInvoiceSeqAndStatusNotIn(localInvoice.getLocalInvoiceSeq(), MasterDataStatus.DELETED.getStatusSeq());
            if (localCreditNoteHeaderList.size() > 0) {
                LocalCreditNoteHeader LastCreatedLocalCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(LocalCreditNoteHeader::getCreatedDate));
                localCreditNoteHeader.setCreditNoteCount(LastCreatedLocalCreditNoteHeader.getCreditNoteCount() + 1);
            } else {
                localCreditNoteHeader.setCreditNoteCount(1);
            }
            localCreditNoteHeader.setFinancialChargeSeq(localInvoice.getFinancialChargeSeq());
        } else {
//            localCreditNoteSummaryDetail = this.getLocalCreditNoteSummaryForExpenseVoucher(localCreditNoteHeader, request);
            ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(localCreditNoteHeader.getExpenseVoucherSeq());
            Stakeholder dbStakeholder = this.stakeholderService.findOne(expenseVoucher.getStakeholderSeq());
            Double vatDifferent = localCreditNoteSummaryDetail.getTotalInvoicedVatAmount() - localCreditNoteSummaryDetail.getTotalCurrentVatAmount();
            if (dbStakeholder.getStakeholderSvatTypeSeq() != null
                    && dbStakeholder.getStakeholderSvatTypeSeq().equals(0)
                    && dbStakeholder.getSuspendedTaxRegNo() != null
                    && vatDifferent != 0.0) {
                localCreditNoteHeader.setsVatStatus(1);
            } else {
                localCreditNoteHeader.setsVatStatus(0);
            }

            List<LocalCreditNoteHeader> localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByLocalInvoiceSeqAndStatusNotIn(expenseVoucher.getExpenseVoucherSeq(), MasterDataStatus.DELETED.getStatusSeq());
            if (localCreditNoteHeaderList.size() > 0) {
                LocalCreditNoteHeader LastCreatedLocalCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(LocalCreditNoteHeader::getCreatedDate));
                localCreditNoteHeader.setCreditNoteCount(LastCreatedLocalCreditNoteHeader.getCreditNoteCount() + 1);
            } else {
                localCreditNoteHeader.setCreditNoteCount(1);
            }
            localCreditNoteHeader.setFinancialChargeSeq(expenseVoucher.getFinancialChargeSeq());
        }
        if (Math.abs(localCreditNoteSummaryDetail.getBalanceToBeCreditedAmount()) > 0) {
            localCreditNoteHeader.setTotalCurrentAmount(localCreditNoteSummaryDetail.getTotalCurrentAmount());
            localCreditNoteHeader.setTotalCurrentNbtAmount(localCreditNoteSummaryDetail.getTotalCurrentNbtAmount());
            localCreditNoteHeader.setTotalCurrentVatAmount(localCreditNoteSummaryDetail.getTotalCurrentVatAmount());
            localCreditNoteHeader.setTotalInvoicedAmount(localCreditNoteSummaryDetail.getTotalInvoicedAmount());
            localCreditNoteHeader.setTotalInvoicedNbtAmount(localCreditNoteSummaryDetail.getTotalInvoicedNbtAmount());
            localCreditNoteHeader.setTotalInvoicedVatAmount(localCreditNoteSummaryDetail.getTotalInvoicedVatAmount());
            localCreditNoteHeader.setFinalTotalInvoiceAmount(localCreditNoteSummaryDetail.getFinalTotalInvoiceAmount());
            localCreditNoteHeader.setFinalTotalCurrentAmount(localCreditNoteSummaryDetail.getFinalTotalCurrentAmount());
            localCreditNoteHeader.setBalanceToBeCreditedAmount(localCreditNoteSummaryDetail.getBalanceToBeCreditedAmount());

            List<LocalCreditNoteChargeDetail> lcnChargeDetailList = new ArrayList<>();
            List<LocalCreditNoteTaxRateMapping> taxRateMappingList = new ArrayList<>();
            String[] financialChargeDetailSeqList = request.getParameterValues("financialChargeDetailSeq[]");
            List<Integer> intFinancialChargeDetailSeqList = Arrays.stream(financialChargeDetailSeqList).map(Integer::valueOf).collect(Collectors.toList());
            for (Integer intFinancialChargeDetailSeq : intFinancialChargeDetailSeqList) {
                lcnChargeDetailList.add(localCreditNoteHeader.getLocalCreditNoteChargeDetailList().stream().filter(i -> i.getFinancialChargeDetailSeq().equals(intFinancialChargeDetailSeq)).findFirst().orElse(null));
                taxRateMappingList.add(localCreditNoteHeader.getLocalCreditNoteTaxRateMappingList().stream().filter(i -> i.getFinancialChargeDetailSeq().equals(intFinancialChargeDetailSeq)).findFirst().orElse(null));
            }

            localCreditNoteHeader.setLocalCreditNoteChargeDetailList(lcnChargeDetailList);
            localCreditNoteHeader.setLocalCreditNoteTaxRateMappingList(taxRateMappingList);

            localCreditNoteHeader.getLocalCreditNoteChargeDetailList().forEach(i -> i.setAmountDifferential(i.getInvoicedAmount() - i.getCurrentAmount()));
            localCreditNoteHeader.getLocalCreditNoteChargeDetailList().forEach(i -> i.setStatus(MasterDataStatus.APPROVED.getStatusSeq()));
            localCreditNoteHeader.getLocalCreditNoteTaxRateMappingList().forEach(i -> i.setStatus(MasterDataStatus.APPROVED.getStatusSeq()));
            localCreditNoteHeader.getLocalCreditNoteTaxRateMappingList().forEach(i -> {
                i.setVatAmount(i.getVatAmount() != null ? i.getVatAmount() : 0.0);
                i.setOtherTaxAmount(i.getOtherTaxAmount() != null ? i.getOtherTaxAmount() : 0.0);
                i.setVatDifference(i.getVatDifference() != null ? i.getVatDifference() : 0.0);
                i.setNbtDifference(i.getNbtDifference() != null ? i.getNbtDifference() : 0.0);
            });

            String letterCode;
            if (localCreditNoteHeader.getBalanceToBeCreditedAmount() > 0) {
                letterCode = "C";
                localCreditNoteHeader.setEntryType(EntryType.CREDIT.getEntryTypeSeq());
            } else {
                letterCode = "D";
                localCreditNoteHeader.setEntryType(EntryType.DEBIT.getEntryTypeSeq());
            }
            localCreditNoteHeader.setLocalCreditNoteNo(this.financialNumberGenerator.generateLocalInvoiceNo(companyProfileSeq, localCreditNoteHeader.getDepartmentSeq(), letterCode));
            localCreditNoteHeader.setBalanceToBeCreditedAmount(Math.abs(localCreditNoteSummaryDetail.getBalanceToBeCreditedAmount()));
            localCreditNoteHeader.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            localCreditNoteHeader.setCompanyProfileSeq(companyProfileSeq);

            //updated new charge line status
            List<FinancialChargeDetail> financialChargeDetailList = this.financialChargeDetailService.findByLiStatusNotAndEvStatusNotAndStatusNotAndFinancialChargeDetailSeqIn(MasterDataStatus.CLOSED.getStatusSeq(), MasterDataStatus.CLOSED.getStatusSeq(), MasterDataStatus.DELETED.getStatusSeq(), intFinancialChargeDetailSeqList);

            if (localCreditNoteHeader.getLocalInvoiceSeq() != null) {
                localCreditNoteHeader.setFunctionalType(FunctionalType.LOCAL_INVOICE.getInvoiceTypeSeq());

                if (financialChargeDetailList.size() > 0) {
                    financialChargeDetailList.forEach(i -> {
                        i.setLiStatus(MasterDataStatus.CLOSED.getStatusSeq());
                        i.setLcnStatus(MasterDataStatus.OPEN.getStatusSeq());
                    });
                }

            } else if (localCreditNoteHeader.getExpenseVoucherSeq() != null) {
                localCreditNoteHeader.setFunctionalType(FunctionalType.EXPENSE_VOUCHER.getInvoiceTypeSeq());

                if (financialChargeDetailList.size() > 0) {
                    financialChargeDetailList.forEach(i -> {
                        i.setEvStatus(MasterDataStatus.CLOSED.getStatusSeq());
                        i.setLcnStatus(MasterDataStatus.OPEN.getStatusSeq());
                    });
                }
            }
            localCreditNoteHeader = this.localCreditNoteHeaderService.save(localCreditNoteHeader);
            responseObject = new ResponseObject("Local Credit Note Saved Successfully", true);
            responseObject.setObject(this.localCreditNoteHeaderService.findOne(localCreditNoteHeader.getLocalCreditNoteHeaderSeq()));
        } else {
            responseObject = new ResponseObject("Credit Amount Should Be Greater Than 0", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteLocalCreditNote(Integer localCreditNoteHeaderSeq, HttpServletRequest request) {
        LocalCreditNoteHeader dbLocalCreditNoteHeader = this.localCreditNoteHeaderService.findOne(localCreditNoteHeaderSeq);
        ResponseObject responseObject;
        if (dbLocalCreditNoteHeader.getStatus().equals(MasterDataStatus.CLOSED.getStatusSeq())) {
            responseObject = new ResponseObject("Can not delete with Closed Status", false);
        } else if (dbLocalCreditNoteHeader.getFinanceIntegration().equals(0)) {
            responseObject = new ResponseObject("Oracle Integration Pending, Please Delete Within a Few Minute", false);
        } else {
            List<LocalCreditNoteHeader> localCreditNoteHeaderList;
            if (dbLocalCreditNoteHeader.getLocalInvoiceSeq() != null) {
                localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByLocalInvoiceSeqAndStatusNotIn(dbLocalCreditNoteHeader.getLocalInvoiceSeq(), MasterDataStatus.DELETED.getStatusSeq());
            } else {
                localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByExpenseVoucherSeqAndStatusNotIn(dbLocalCreditNoteHeader.getExpenseVoucherSeq(), MasterDataStatus.DELETED.getStatusSeq());
            }
            LocalCreditNoteHeader toBeDeletedLocalCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(LocalCreditNoteHeader::getCreatedDate));
            if (toBeDeletedLocalCreditNoteHeader != null) {
                toBeDeletedLocalCreditNoteHeader.getLocalCreditNoteChargeDetailList().forEach(i -> i.setStatus(MasterDataStatus.DELETED.getStatusSeq()));
                toBeDeletedLocalCreditNoteHeader.getLocalCreditNoteTaxRateMappingList().forEach(i -> i.setStatus(MasterDataStatus.DELETED.getStatusSeq()));
                toBeDeletedLocalCreditNoteHeader.setStatus(MasterDataStatus.DELETED.getStatusSeq());

                if (dbLocalCreditNoteHeader.getLocalInvoiceSeq() != null) {
                    List<Integer> financialChargeDetailSeqList = toBeDeletedLocalCreditNoteHeader.getLocalCreditNoteChargeDetailList().stream().map(LocalCreditNoteChargeDetail::getFinancialChargeDetailSeq).collect(Collectors.toList());
                    List<FinancialChargeDetail> financialChargeDetailList = this.financialChargeDetailService.findByLcnStatusAndStatusNotAndFinancialChargeDetailSeqIn(MasterDataStatus.OPEN.getStatusSeq(), MasterDataStatus.DELETED.getStatusSeq(), financialChargeDetailSeqList);
                    if (financialChargeDetailList.size() > 0) {
                        financialChargeDetailList.forEach(i -> {
                            i.setLiStatus(MasterDataStatus.OPEN.getStatusSeq());
                            i.setLcnStatus(MasterDataStatus.DELETED.getStatusSeq());
                        });
                    }
                } else {
                    List<Integer> financialChargeDetailSeqList = toBeDeletedLocalCreditNoteHeader.getLocalCreditNoteChargeDetailList().stream().map(LocalCreditNoteChargeDetail::getFinancialChargeDetailSeq).collect(Collectors.toList());
                    List<FinancialChargeDetail> financialChargeDetailList = this.financialChargeDetailService.findByLcnStatusAndStatusNotAndFinancialChargeDetailSeqIn(1, MasterDataStatus.DELETED.getStatusSeq(), financialChargeDetailSeqList);
                    if (financialChargeDetailList.size() > 0) {
                        financialChargeDetailList.forEach(i -> {
                            i.setEvStatus(MasterDataStatus.OPEN.getStatusSeq());
                            i.setLcnStatus(MasterDataStatus.DELETED.getStatusSeq());
                        });
                    }
                }
                this.localCreditNoteHeaderService.save(dbLocalCreditNoteHeader);
            }
            responseObject = new ResponseObject("Local Credit Note deleted successfully", true);
        }
        responseObject.setObject(dbLocalCreditNoteHeader);
        return responseObject;
    }

    @Override
    public LocalCreditNoteSummaryDetail getLocalInvoiceTotalCreditNoteSummaryDetails(List<LocalCreditNoteFinancialChargeDetail> localCreditNoteFinancialChargeDetailList, Integer localCreditNoteHeaderSeq) {
        Double totalInvoicedAmount = 0.0, totalInvoicedNtbAmount = 0.0, totalInvoicedVatAmount = 0.0, finalTotalInvoiceAmount;
        Double totalCurrentAmount = 0.0, totalCurrentNtbAmount = 0.0, totalCurrentVatAmount = 0.0, balanceToBeCreditedAmount, finalTotalCurrentAmount;
        Double newTotalCurrentVatAmount = 0.0, newTotalCurrentNtbAmount = 0.0;
        LocalCreditNoteSummaryDetail localCreditNoteSummaryDetail = new LocalCreditNoteSummaryDetail();
        Integer vat = this.taxTypeService.findByTaxTypeNameAndStatus("VAT", MasterDataStatus.APPROVED.getStatusSeq()).getTaxTypeSeq();
        Integer other = this.taxTypeService.findByTaxTypeNameAndStatus("OTHER", MasterDataStatus.APPROVED.getStatusSeq()).getTaxTypeSeq();
        if (localCreditNoteHeaderSeq == null) {
            List<LocalCreditNoteHeader> localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByLocalInvoiceSeqAndStatusNotIn(localCreditNoteFinancialChargeDetailList.get(0).getLocalInvoiceSeq(), MasterDataStatus.DELETED.getStatusSeq());
            LocalInvoice localInvoice = this.localInvoiceService.findOne(localCreditNoteFinancialChargeDetailList.get(0).getLocalInvoiceSeq());
            totalInvoicedVatAmount = localInvoice.getFinalVatAmount();
            totalInvoicedNtbAmount = localInvoice.getFinalOtherTaxAmount();
            if (localInvoice.getCurrency() != null) {
                localCreditNoteSummaryDetail.setCurrencyCode(localInvoice.getCurrency().getCurrencyCode());
            }

            if (localCreditNoteHeaderList.size() == 0) {
                totalCurrentAmount = localCreditNoteFinancialChargeDetailList.stream().map(LocalCreditNoteFinancialChargeDetail::getCurrentAmount).mapToDouble(Double::doubleValue).sum();
                totalInvoicedAmount = localCreditNoteFinancialChargeDetailList.stream().map(LocalCreditNoteFinancialChargeDetail::getInvoicedAmount).mapToDouble(Double::doubleValue).sum();
                for (LocalCreditNoteFinancialChargeDetail localCreditNoteFinancialChargeDetail : localCreditNoteFinancialChargeDetailList) {
                    if (localCreditNoteFinancialChargeDetail.getLocalInvoiceChargeDetailTaxList().size() > 0) {
                        for (LocalInvoiceChargeDetailTax localInvTaxRateMapping : localCreditNoteFinancialChargeDetail.getLocalInvoiceChargeDetailTaxList()) {
                            Double taxRate = localInvTaxRateMapping.getTaxPercentage() / 100;
                            if (localInvTaxRateMapping.getTaxRegistrationSeq() != null) {
                                if (localInvTaxRateMapping.getTaxTypeSeq().equals(vat)) {
                                    newTotalCurrentVatAmount = (newTotalCurrentNtbAmount + localCreditNoteFinancialChargeDetail.getCurrentAmount()) * taxRate;
                                } else if (localInvTaxRateMapping.getTaxTypeSeq().equals(vat)) {
                                    newTotalCurrentNtbAmount = localCreditNoteFinancialChargeDetail.getCurrentAmount() * taxRate;
                                }

                            }
                            totalCurrentVatAmount += newTotalCurrentVatAmount;
                            totalCurrentNtbAmount += newTotalCurrentNtbAmount;
                        }
                    }
                }
                localCreditNoteSummaryDetail.setTotalCurrentNbtAmount(totalCurrentNtbAmount);
                localCreditNoteSummaryDetail.setTotalCurrentVatAmount(totalCurrentVatAmount);
                localCreditNoteSummaryDetail.setTotalInvoicedNbtAmount(totalInvoicedNtbAmount);
                localCreditNoteSummaryDetail.setTotalInvoicedVatAmount(totalInvoicedVatAmount);

                finalTotalInvoiceAmount = totalInvoicedAmount + totalInvoicedNtbAmount + totalInvoicedVatAmount;
                finalTotalCurrentAmount = totalCurrentAmount + totalCurrentNtbAmount + totalCurrentVatAmount;
                balanceToBeCreditedAmount = finalTotalInvoiceAmount - finalTotalCurrentAmount;

                localCreditNoteSummaryDetail.setTotalCurrentAmount(totalCurrentAmount);
                localCreditNoteSummaryDetail.setTotalInvoicedAmount(totalInvoicedAmount);
                localCreditNoteSummaryDetail.setFinalTotalInvoiceAmount(finalTotalInvoiceAmount);
                localCreditNoteSummaryDetail.setFinalTotalCurrentAmount(finalTotalCurrentAmount);
                localCreditNoteSummaryDetail.setBalanceToBeCreditedAmount(Math.abs(balanceToBeCreditedAmount));
                if (balanceToBeCreditedAmount > 0) {
                    localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.CREDIT.getEntryTypeSeq());
                } else {
                    localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.DEBIT.getEntryTypeSeq());
                }
            } else {
                List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
                LocalCreditNoteHeader localCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(LocalCreditNoteHeader::getCreatedDate));
                localCreditNoteHeader.getLocalCreditNoteChargeDetailList().forEach(i -> financialChargeDetailList.add(i.getFinancialChargeDetail()));
                this.exchangeRateConversion.dynamicConversion(financialChargeDetailList, localInvoice.getCurrencySeq(), localInvoice.getExchangeRateSeq());
                localCreditNoteSummaryDetail = this.setLocalCreditNoteSummaryDetail(localCreditNoteHeader, localCreditNoteSummaryDetail);
            }
        } else {
            LocalCreditNoteHeader localCreditNoteHeader = this.localCreditNoteHeaderService.findOne(localCreditNoteHeaderSeq);
            localCreditNoteSummaryDetail.setCurrencyCode(localCreditNoteHeader.getLocalInvoice().getCurrency().getCurrencyCode());
            localCreditNoteSummaryDetail.setTotalCurrentAmount(localCreditNoteHeader.getTotalCurrentAmount());
            localCreditNoteSummaryDetail.setTotalInvoicedAmount(localCreditNoteHeader.getTotalInvoicedAmount());
            localCreditNoteSummaryDetail.setTotalInvoicedNbtAmount(localCreditNoteHeader.getTotalInvoicedNbtAmount());
            localCreditNoteSummaryDetail.setTotalInvoicedVatAmount(localCreditNoteHeader.getTotalInvoicedVatAmount());
            localCreditNoteSummaryDetail.setTotalCurrentNbtAmount(localCreditNoteHeader.getTotalCurrentNbtAmount());
            localCreditNoteSummaryDetail.setTotalCurrentVatAmount(localCreditNoteHeader.getTotalCurrentVatAmount());
            localCreditNoteSummaryDetail.setFinalTotalInvoiceAmount(localCreditNoteHeader.getFinalTotalInvoiceAmount());
            localCreditNoteSummaryDetail.setFinalTotalCurrentAmount(localCreditNoteHeader.getFinalTotalCurrentAmount());
            localCreditNoteSummaryDetail.setBalanceToBeCreditedAmount(localCreditNoteHeader.getBalanceToBeCreditedAmount());
            if (localCreditNoteHeader.getBalanceToBeCreditedAmount() > 0) {
                localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.CREDIT.getEntryTypeSeq());
            } else {
                localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.DEBIT.getEntryTypeSeq());
            }
        }
        return localCreditNoteSummaryDetail;
    }

    @Override
    public LocalCreditNoteSummaryDetail getLocalInvoiceTotalCreditNoteSummaryDetailsForExpenseVoucher(List<LocalCreditNoteFinanceChargeDetailForExpVoucher> localCreditNoteFinanceChargeDetailForExpVoucherList, Integer localCreditNoteHeaderSeq) {
        Double totalInvoicedAmount = 0.0, totalInvoicedNtbAmount = 0.0, totalInvoicedVatAmount = 0.0, finalTotalInvoiceAmount = 0.0;
        Double totalCurrentAmount = 0.0, totalCurrentNtbAmount = 0.0, totalCurrentVatAmount = 0.0, finalTotalCurrentAmount = 0.0;
        Double balanceToBeCreditedAmount = 0.0;
        Double newTotalCurrentVatAmount = 0.0, newTotalCurrentNtbAmount = 0.0;
        LocalCreditNoteSummaryDetail localCreditNoteSummaryDetail = new LocalCreditNoteSummaryDetail();

        /*if (localCreditNoteHeaderSeq == null) {
            List<LocalCreditNoteHeader> localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByExpenseVoucherSeqAndStatusNotIn(localCreditNoteFinanceChargeDetailForExpVoucherList.get(0).getExpenseVoucherSeq(), MasterDataStatus.DELETED.getStatusSeq());
            ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(localCreditNoteFinanceChargeDetailForExpVoucherList.get(0).getExpenseVoucherSeq());
            totalInvoicedVatAmount = expenseVoucher.getFinalVatAmount();
            totalInvoicedNtbAmount = expenseVoucher.getFinalOtherTaxAmount();
            if (expenseVoucher.getCurrency() != null) {
                localCreditNoteSummaryDetail.setCurrencyCode(expenseVoucher.getCurrency().getCurrencyCode());
            }

            if (localCreditNoteHeaderList.size() == 0) {
                totalCurrentAmount = localCreditNoteFinanceChargeDetailForExpVoucherList.stream().map(LocalCreditNoteFinanceChargeDetailForExpVoucher::getCurrentAmount).mapToDouble(f -> f.doubleValue()).sum();
                totalInvoicedAmount = localCreditNoteFinanceChargeDetailForExpVoucherList.stream().map(LocalCreditNoteFinanceChargeDetailForExpVoucher::getInvoicedAmount).mapToDouble(f -> f.doubleValue()).sum();

                for (LocalCreditNoteFinanceChargeDetailForExpVoucher localCreditNoteFinanceChargeDetailForExpVoucher : localCreditNoteFinanceChargeDetailForExpVoucherList) {
                    if (localCreditNoteFinanceChargeDetailForExpVoucher.getExpenseVoucherChargeDetailTaxList().size() > 0) {
                        for (ExpenseVouTaxRateMapping expenseVouTaxRateMapping : localCreditNoteFinanceChargeDetailForExpVoucher.getExpenseVouTaxRateMappingList()) {
                            if (localCreditNoteFinanceChargeDetailForExpVoucher.getFinancialChargeDetailSeq().equals(expenseVouTaxRateMapping.getFinancialChargeDetailSeq())) {
                                Double vatTaxRate = expenseVouTaxRateMapping.getTaxVatRate() / 100;
                                Double nbtTaxRate = expenseVouTaxRateMapping.getTaxOtherRate() / 100;

                                if (expenseVouTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                    newTotalCurrentNtbAmount = localCreditNoteFinanceChargeDetailForExpVoucher.getCurrentAmount() * nbtTaxRate;
                                    if (expenseVouTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                        newTotalCurrentVatAmount = (newTotalCurrentNtbAmount + localCreditNoteFinanceChargeDetailForExpVoucher.getCurrentAmount()) * vatTaxRate;
                                    }
                                } else if (expenseVouTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    if (newTotalCurrentNtbAmount != 0.0) {
                                        newTotalCurrentVatAmount = ((newTotalCurrentNtbAmount + localCreditNoteFinanceChargeDetailForExpVoucher.getCurrentAmount()) * vatTaxRate);
                                    } else {
                                        newTotalCurrentVatAmount = localCreditNoteFinanceChargeDetailForExpVoucher.getCurrentAmount() * vatTaxRate;
                                    }
                                }
                                totalCurrentVatAmount += newTotalCurrentVatAmount;
                                totalCurrentNtbAmount += newTotalCurrentNtbAmount;
                                break;
                            }
                        }
                    }
                }
                localCreditNoteSummaryDetail.setTotalCurrentNbtAmount(totalCurrentNtbAmount);
                localCreditNoteSummaryDetail.setTotalCurrentVatAmount(totalCurrentVatAmount);
                localCreditNoteSummaryDetail.setTotalInvoicedNbtAmount(totalInvoicedNtbAmount);
                localCreditNoteSummaryDetail.setTotalInvoicedVatAmount(totalInvoicedVatAmount);

                finalTotalInvoiceAmount = totalInvoicedAmount + totalInvoicedNtbAmount + totalInvoicedVatAmount;
                finalTotalCurrentAmount = totalCurrentAmount + totalCurrentNtbAmount + totalCurrentVatAmount;
                balanceToBeCreditedAmount = this.convertValueIntoTwoDecimal(finalTotalInvoiceAmount) - this.convertValueIntoTwoDecimal(finalTotalCurrentAmount);

                localCreditNoteSummaryDetail.setTotalCurrentAmount(totalCurrentAmount);
                localCreditNoteSummaryDetail.setTotalInvoicedAmount(totalInvoicedAmount);
                localCreditNoteSummaryDetail.setFinalTotalInvoiceAmount(finalTotalInvoiceAmount);
                localCreditNoteSummaryDetail.setFinalTotalCurrentAmount(finalTotalCurrentAmount);
                localCreditNoteSummaryDetail.setBalanceToBeCreditedAmount(Math.abs(balanceToBeCreditedAmount));
                if (balanceToBeCreditedAmount > 0) {
                    localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.CREDIT.getEntryTypeSeq());
                } else {
                    localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.DEBIT.getEntryTypeSeq());
                }
            } else {
                List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
                LocalCreditNoteHeader localCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(i -> i.getCreatedDate()));
                localCreditNoteHeader.getLcnChargeDetailList().forEach(i -> financialChargeDetailList.add(i.getFinancialChargeDetail()));
                this.exchangeRateConversion.currencyConversionForExpenseVoucherFinChaDetail(financialChargeDetailList, expenseVoucher.getExpenseVoucherSeq(), expenseVoucher.getBaseCurrencySeq(), expenseVoucher.getExchangeRateSeq());
                localCreditNoteSummaryDetail = this.setLocalCreditNoteSummaryDetail(localCreditNoteHeader, localCreditNoteSummaryDetail);
            }
        } else {
            LocalCreditNoteHeader localCreditNoteHeader = this.localCreditNoteHeaderService.findOne(localCreditNoteHeaderSeq);
            if (localCreditNoteHeader.getExpenseVoucher() != null) {
                localCreditNoteSummaryDetail.setCurrencyCode(localCreditNoteHeader.getExpenseVoucher().getCurrency().getCurrencyCode());
            }
            localCreditNoteSummaryDetail.setTotalCurrentAmount(localCreditNoteHeader.getTotalCurrentAmount());
            localCreditNoteSummaryDetail.setTotalInvoicedAmount(localCreditNoteHeader.getTotalInvoicedAmount());
            localCreditNoteSummaryDetail.setTotalInvoicedNbtAmount(localCreditNoteHeader.getTotalInvoicedNbtAmount());
            localCreditNoteSummaryDetail.setTotalInvoicedVatAmount(localCreditNoteHeader.getTotalInvoicedVatAmount());
            localCreditNoteSummaryDetail.setTotalCurrentNbtAmount(localCreditNoteHeader.getTotalCurrentNbtAmount());
            localCreditNoteSummaryDetail.setTotalCurrentVatAmount(localCreditNoteHeader.getTotalCurrentVatAmount());
            localCreditNoteSummaryDetail.setFinalTotalInvoiceAmount(localCreditNoteHeader.getFinalTotalInvoiceAmount());
            localCreditNoteSummaryDetail.setFinalTotalCurrentAmount(localCreditNoteHeader.getFinalTotalCurrentAmount());
            localCreditNoteSummaryDetail.setBalanceToBeCreditedAmount(localCreditNoteHeader.getBalanceToBeCreditedAmount());
            if (localCreditNoteHeader.getBalanceToBeCreditedAmount() > 0) {
                localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.CREDIT.getEntryTypeSeq());
            } else {
                localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.DEBIT.getEntryTypeSeq());
            }
        }*/
        return localCreditNoteSummaryDetail;
    }

    @Override
    public List<LocalCreditNoteFinancialChargeDetail> findLocalCreditNoteChargeDetailForLocalInvoice(Integer localInvoiceSeq, Integer localCreditNoteHeaderSeq) {
        List<LocalCreditNoteFinancialChargeDetail> localCreditNoteFinancialChargeDetails = new ArrayList<>();
        /*if (localCreditNoteHeaderSeq == null) {
            localCreditNoteFinancialChargeDetails = this.prepareNewLocalCreditNoteFinancialChargeDetailListForLocalInvoice(localInvoiceSeq);
        } else {
            LocalCreditNoteHeader localCreditNoteHeader = this.localCreditNoteHeaderService.findOne(localCreditNoteHeaderSeq);
            for (LcnChargeDetail lcnChargeDetail : localCreditNoteHeader.getLcnChargeDetailList()) {
                LocalCreditNoteFinancialChargeDetail localCreditNoteFinancialChargeDetail = new LocalCreditNoteFinancialChargeDetail();
                localCreditNoteFinancialChargeDetail.setChargeSeq(lcnChargeDetail.getChargeSeq());
                if (lcnChargeDetail.getCharge() != null) {
                    localCreditNoteFinancialChargeDetail.setChargeName(lcnChargeDetail.getCharge().getChargeName());
                }
                if (lcnChargeDetail.getFinancialCharge() != null) {
                    localCreditNoteFinancialChargeDetail.setHouseBlSeq(lcnChargeDetail.getFinancialCharge().getHouseBlSeq());
                }
                if (lcnChargeDetail.getFinancialCharge().getHouseBl() != null) {
                    localCreditNoteFinancialChargeDetail.setHawbNo(lcnChargeDetail.getFinancialCharge().getHouseBl().getHawbNo());
                } else if (lcnChargeDetail.getFinancialCharge().getMasterBlAux() != null) {
                    localCreditNoteFinancialChargeDetail.setMasterAwbNo(lcnChargeDetail.getFinancialCharge().getMasterBlAux().getMasterAwbNo());
                } else if (lcnChargeDetail.getFinancialCharge().getInbondSeq() != null) {
                    if (lcnChargeDetail.getFinancialCharge().getInbond().getBlTypeSeq().equals(BlType.MBL.getBlTypeSeq())) {
                        localCreditNoteFinancialChargeDetail.setMasterAwbNo(lcnChargeDetail.getFinancialCharge().getInbond().getBlNo());
                    } else {
                        localCreditNoteFinancialChargeDetail.setHawbNo(lcnChargeDetail.getFinancialCharge().getInbond().getBlNo());
                    }
                }
                localCreditNoteFinancialChargeDetail.setCurrencySeq(lcnChargeDetail.getCurrencySeq());
                if (lcnChargeDetail.getCurrency() != null) {
                    localCreditNoteFinancialChargeDetail.setCurrencyCode(lcnChargeDetail.getCurrency().getCurrencyCode());

                }
                localCreditNoteFinancialChargeDetail.setInvoicedAmount(lcnChargeDetail.getInvoicedAmount());
                localCreditNoteFinancialChargeDetail.setCurrentAmount(lcnChargeDetail.getCurrentAmount());
                localCreditNoteFinancialChargeDetail.setFinancialChargeSeq(lcnChargeDetail.getFinancialChargeSeq());
                localCreditNoteFinancialChargeDetail.setFinancialChargeDetailSeq(lcnChargeDetail.getFinancialChargeDetailSeq());
                localCreditNoteFinancialChargeDetail.setLocalInvoiceFinChrMapSeq(lcnChargeDetail.getLocalInvoiceFinChrMapSeq());
                localCreditNoteFinancialChargeDetail.setLcnChargeDetailSeq(lcnChargeDetail.getLcnChargeDetailSeq());
                LcnTaxRateMapping lcnTaxRateMapping = this.lcnTaxRateMappingService.findByLocalCreditNoteHeaderSeqAndFinancialChargeDetailSeq(localCreditNoteHeaderSeq, lcnChargeDetail.getFinancialChargeDetailSeq());
                if (lcnTaxRateMapping != null) {
                    LocalInvTaxRateMapping localInvTaxRateMapping = new LocalInvTaxRateMapping();
                    List<LocalInvTaxRateMapping> localInvTaxRateMappingList = new ArrayList<>();
                    if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null || lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                        localInvTaxRateMapping.setTaxRegistrationVatSeq(lcnTaxRateMapping.getTaxRegistrationVatSeq());
                        localInvTaxRateMapping.setTaxRegistrationOtherTaxSeq(lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq());
                        localInvTaxRateMappingList.add(localInvTaxRateMapping);
                    }
                    localCreditNoteFinancialChargeDetail.setLocalInvTaxRateMappingList(localInvTaxRateMappingList);
                }
                localCreditNoteFinancialChargeDetails.add(localCreditNoteFinancialChargeDetail);
            }
        }*/
        return localCreditNoteFinancialChargeDetails;
    }

    @Override
    public List<LocalCreditNoteFinanceChargeDetailForExpVoucher> findLocalCreditNoteChargeDetailForExpenseVoucher(Integer expenseVoucherSeq, Integer localCreditNoteHeaderSeq) {
        List<LocalCreditNoteFinanceChargeDetailForExpVoucher> localCreditNoteFinanceChargeDetailForExpVouchers = new ArrayList<>();
        /*if (localCreditNoteHeaderSeq == null) {
            localCreditNoteFinanceChargeDetailForExpVouchers = this.prepareNewLocalCreditNoteFinancialChargeDetailListForExpenseVoucher(expenseVoucherSeq);
        } else {
            LocalCreditNoteHeader localCreditNoteHeader = this.localCreditNoteHeaderService.findOne(localCreditNoteHeaderSeq);
            for (LcnChargeDetail lcnChargeDetail : localCreditNoteHeader.getLcnChargeDetailList()) {
                LocalCreditNoteFinanceChargeDetailForExpVoucher localCreditNoteFinanceChargeDetailForExpVoucher = new LocalCreditNoteFinanceChargeDetailForExpVoucher();
                localCreditNoteFinanceChargeDetailForExpVoucher.setChargeSeq(lcnChargeDetail.getChargeSeq());
                if (lcnChargeDetail.getCharge() != null) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setChargeName(lcnChargeDetail.getCharge().getChargeName());
                }
                if (lcnChargeDetail.getFinancialCharge() != null) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setHouseBlSeq(lcnChargeDetail.getFinancialCharge().getHouseBlSeq());
                }
                if (lcnChargeDetail.getFinancialCharge().getHouseBl() != null) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setHawbNo(lcnChargeDetail.getFinancialCharge().getHouseBl().getHawbNo());
                } else if (lcnChargeDetail.getFinancialCharge().getMasterBlAux() != null) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setMasterAwbNo(lcnChargeDetail.getFinancialCharge().getMasterBlAux().getMasterAwbNo());
                } else if (lcnChargeDetail.getFinancialCharge().getInbondSeq() != null) {
                    if (lcnChargeDetail.getFinancialCharge().getInbond().getBlTypeSeq().equals(BlType.MBL.getBlTypeSeq())) {
                        localCreditNoteFinanceChargeDetailForExpVoucher.setMasterAwbNo(lcnChargeDetail.getFinancialCharge().getInbond().getBlNo());
                    } else {
                        localCreditNoteFinanceChargeDetailForExpVoucher.setHawbNo(lcnChargeDetail.getFinancialCharge().getInbond().getBlNo());
                    }
                }
                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrencySeq(lcnChargeDetail.getCurrencySeq());
                if (lcnChargeDetail.getCurrency() != null) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setCurrencyCode(lcnChargeDetail.getCurrency().getCurrencyCode());
                }
                localCreditNoteFinanceChargeDetailForExpVoucher.setInvoicedAmount(lcnChargeDetail.getInvoicedAmount());
                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrentAmount(lcnChargeDetail.getCurrentAmount());
                localCreditNoteFinanceChargeDetailForExpVoucher.setFinancialChargeSeq(lcnChargeDetail.getFinancialChargeSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setFinancialChargeDetailSeq(lcnChargeDetail.getFinancialChargeDetailSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setExpenseVouFinChrMapSeq(lcnChargeDetail.getExpenseVouFinChrMapSeq());
                LcnTaxRateMapping lcnTaxRateMapping = this.lcnTaxRateMappingService.findByLocalCreditNoteHeaderSeqAndFinancialChargeDetailSeq(localCreditNoteHeaderSeq, lcnChargeDetail.getFinancialChargeDetailSeq());
                if (lcnTaxRateMapping != null) {
                    ExpenseVouTaxRateMapping expenseVouTaxRateMapping = new ExpenseVouTaxRateMapping();
                    List<ExpenseVouTaxRateMapping> expenseVouTaxRateMappingList = new ArrayList<>();
                    if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null || lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                        expenseVouTaxRateMapping.setTaxRegistrationVatSeq(lcnTaxRateMapping.getTaxRegistrationVatSeq());
                        expenseVouTaxRateMapping.setTaxRegistrationOtherTaxSeq(lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq());
                        expenseVouTaxRateMappingList.add(expenseVouTaxRateMapping);
                        localCreditNoteFinanceChargeDetailForExpVoucher.setExpenseVouTaxRateMappingList(expenseVouTaxRateMappingList);
                    }
                }
                localCreditNoteFinanceChargeDetailForExpVoucher.setLcnChargeDetailSeq(lcnChargeDetail.getLcnChargeDetailSeq());
                localCreditNoteFinanceChargeDetailForExpVouchers.add(localCreditNoteFinanceChargeDetailForExpVoucher);
            }
        }*/
        return localCreditNoteFinanceChargeDetailForExpVouchers;
    }

    @Override
    public ResponseObject loadSummaryDetails(LocalCreditNoteHeader localCreditNoteHeader, HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject(false, "Error");
        LocalCreditNoteSummaryDetail localCreditNoteSummaryDetail = null;
        if (localCreditNoteHeader.getLocalInvoiceSeq() != null) {
            localCreditNoteSummaryDetail = this.getLocalCreditNoteSummaryForLocalInvoice(localCreditNoteHeader, request);
        } else {
//            localCreditNoteSummaryDetail = this.getLocalCreditNoteSummaryForExpenseVoucher(localCreditNoteHeader, request);
        }
        localCreditNoteSummaryDetail.setBalanceToBeCreditedAmount(Math.abs(localCreditNoteSummaryDetail.getBalanceToBeCreditedAmount()));
        if (localCreditNoteSummaryDetail.getBalanceToBeCreditedAmount() > 0) {
            localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.CREDIT.getEntryTypeSeq());
        } else {
            localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.DEBIT.getEntryTypeSeq());
        }
        responseObject.setMessage("Success");
        responseObject.setStatus(true);
        responseObject.setObject(localCreditNoteSummaryDetail);
        return responseObject;
    }

    @Override
    public Integer getReport(Integer localCreditNoteHeaderSeq, String reportName, Integer pdf, Integer rtf, Integer xls, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Integer uploadDocumentSeq = null;
        /*try {
            LocalCreditNoteHeader localCreditNoteHeader = this.localCreditNoteHeaderService.findOne(localCreditNoteHeaderSeq);
            String module = "finance";
            String reportType;
            Map<String, Object> param = new HashMap<>();
            param.put("localCreditNoteHeaderSeq", localCreditNoteHeaderSeq);
            param.put("amountInWord", this.amountInWord.getAmountInWord(localCreditNoteHeader.getBalanceToBeCreditedAmount()));
            Double balanceToBeCreditAmount = localCreditNoteHeader.getFinalTotalInvoiceAmount() - localCreditNoteHeader.getFinalTotalCurrentAmount();
            if (balanceToBeCreditAmount > 0) {
                reportType = "CREDIT";
            } else {
                reportType = "DEBIT";
            }
            param.put("reportType", reportType);

            String houseBlNos = localCreditNoteHeader.getLcnChargeDetailList().stream().map(i -> i.getFinancialChargeDetail().getFinancialCharge().getHouseBl()).
                    filter(i -> i != null).map(h -> h.getHawbNo()).collect(Collectors.toSet()).stream().collect(Collectors.joining(","));
            param.put("houseBlNos", houseBlNos);

            if (pdf != null && pdf == 1) {
                uploadDocumentSeq = this.reportGenerator.saveReport(param,
                        module,
                        reportName,
                        localCreditNoteHeader.getLocalCreditNoteNo(),
                        ".pdf",
                        "application/pdf",
                        httpServletRequest);
            }
            if (rtf != null && rtf == 1) {
                uploadDocumentSeq = this.reportGenerator.saveReport(param,
                        module,
                        reportName,
                        localCreditNoteHeader.getLocalCreditNoteNo(),
                        ".rtf",
                        "application/rtf",
                        httpServletRequest);
            }
            if (xls != null && xls == 1) {
                uploadDocumentSeq = this.reportGenerator.saveReport(param,
                        module,
                        reportName,
                        localCreditNoteHeader.getLocalCreditNoteNo(),
                        ".xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        httpServletRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return uploadDocumentSeq;
    }

    @Override
    public List<LocalInvoice> findInvoiceList(String searchParam, Integer invoiceTypeSeq, Integer moduleSeq, HttpServletRequest request) {
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        ArrayList<Integer> statusSeqList = new ArrayList<>(Arrays.asList(MasterDataStatus.CLOSED.getStatusSeq(),
                MasterDataStatus.DELETED.getStatusSeq()));
//        return this.localInvoiceService.findByLocalInvoiceNoContainsIgnoreCaseAndModuleSeqAndCompanyProfileSeqAndStatusIsNotIn(searchParam, moduleSeq, companyProfileSeq, statusSeqList);
        return null;
    }

    @Override
    public List<ExpenseVoucher> findExpenseVoucherList(String searchParam, Integer invoiceTypeSeq, Integer moduleSeq, HttpServletRequest request) {
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        ArrayList<Integer> statusSeqList = new ArrayList<>(Arrays.asList(MasterDataStatus.CLOSED.getStatusSeq(),
                MasterDataStatus.DELETED.getStatusSeq()));
//        return this.expenseVoucherService.findByExpenseVoucherNoContainsIgnoreCaseAndModuleSeqAndCompanyProfileSeqAndStatusIsNotIn(searchParam, moduleSeq, companyProfileSeq, statusSeqList);
        return null;
    }

    private LocalCreditNoteSummaryDetail getLocalCreditNoteSummaryForLocalInvoice(LocalCreditNoteHeader localCreditNoteHeader, HttpServletRequest request) {
        LocalCreditNoteSummaryDetail localCreditNoteSummaryDetail = null;
        /*List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
        List<LocalInvFinChargeMapping> localInvFinChargeMappingList = new ArrayList<>();
        List<LcnChargeDetail> lcnChargeDetailList = new ArrayList<>();

        String[] financialChargeDetailSeqList = request.getParameterValues("financialChargeDetailSeq[]");
        List<Integer> intFinancialChargeDetailSeqList = Arrays.asList(financialChargeDetailSeqList).stream().map(Integer::valueOf).collect(Collectors.toList());
        LocalInvoice localInvoice = this.localInvoiceService.findOne(localCreditNoteHeader.getLocalInvoiceSeq());
        List<LcnTaxRateMapping> taxRateMappingList = localCreditNoteHeader.getLcnTaxRateMappingList().stream().filter(i -> i.getTaxRegistrationVatSeq() != null || i.getTaxRegistrationOtherTaxSeq() != null).collect(Collectors.toList());

        List<LocalCreditNoteHeader> localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByLocalInvoiceSeqAndStatusNotIn(localCreditNoteHeader.getLocalInvoiceSeq(), MasterDataStatus.DELETED.getStatusSeq());
        if (localCreditNoteHeaderList.size() > 0) {
            List<Integer> newFinancialChargeDetailSeqList = new ArrayList<>();
            for (Integer intFinancialChargeDetailSeq : intFinancialChargeDetailSeqList) {
                LocalCreditNoteHeader dbLocalCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(i -> i.getCreatedDate()));
                Optional<LcnChargeDetail> financialChargeDetailSeq = dbLocalCreditNoteHeader.getLcnChargeDetailList().stream().filter(i -> i.getFinancialChargeDetailSeq().equals(intFinancialChargeDetailSeq)).findFirst();
                if (financialChargeDetailSeq.isPresent()) {
                    lcnChargeDetailList.add(financialChargeDetailSeq.get());
                } else {
                    newFinancialChargeDetailSeqList.add(intFinancialChargeDetailSeq);
                }
            }

            for (Integer intFinancialChargeDetailSeq : newFinancialChargeDetailSeqList) {
                FinancialChargeDetail financialChargeDetail = this.financialChargeDetailService.findOne(intFinancialChargeDetailSeq);
                financialChargeDetailList.add(financialChargeDetail);
            }

            if (financialChargeDetailList.size() > 0) {
                financialChargeDetailList = this.exchangeRateConversion.currencyConversionForLocalInvoiceFinChaDetail(financialChargeDetailList, localInvoice.getLocalInvoiceSeq(), localInvoice.getBaseCurrencySeq(), localInvoice.getExchangeRateSeq());
            }
        } else {
            for (Integer intFinancialChargeDetailSeq : intFinancialChargeDetailSeqList) {
                LocalInvFinChargeMapping localInvFinChargeMapping = this.localInvFinChargeMappingService.findByLocalInvoiceSeqAndFinancialChargeDetailSeq(localCreditNoteHeader.getLocalInvoiceSeq(), intFinancialChargeDetailSeq);
                if (localInvFinChargeMapping != null) {
                    localInvFinChargeMappingList.add(localInvFinChargeMapping);
                } else {
                    FinancialChargeDetail financialChargeDetail = this.financialChargeDetailService.findOne(intFinancialChargeDetailSeq);
                    financialChargeDetailList.add(financialChargeDetail);
                }
            }
            if (localInvFinChargeMappingList.size() > 0) {
                localInvFinChargeMappingList = this.exchangeRateConversion.currencyConversionForLocalInvoice(localInvFinChargeMappingList, localInvoice.getExchangeRateSeq());
            }
            if (financialChargeDetailList.size() > 0) {
                financialChargeDetailList = this.exchangeRateConversion.currencyConversionForLocalInvoiceFinChaDetail(financialChargeDetailList, localInvoice.getLocalInvoiceSeq(), localInvoice.getBaseCurrencySeq(), localInvoice.getExchangeRateSeq());
            }
        }
        localCreditNoteSummaryDetail = this.getLocalInvoiceSummaryDetail(localInvFinChargeMappingList, financialChargeDetailList, taxRateMappingList, lcnChargeDetailList, localInvoice, localCreditNoteHeaderList);
*/
        return localCreditNoteSummaryDetail;
    }

    /*private LocalCreditNoteSummaryDetail getLocalInvoiceSummaryDetail(List<LocalInvoicFinChargeMapping> localInvFinChargeMappingList,
                                                                      List<FinancialChargeDetail> financialChargeDetailList,
                                                                      List<LcnTaxRateMapping> taxRateMappingList,
                                                                      List<LcnChargeDetail> lcnChargeDetailListNew,
                                                                      LocalInvoice localInvoice,
                                                                      List<LocalCreditNoteHeader> localCreditNoteHeaderList) {
        Double totalInvoicedAmount = 0.0, totalInvoicedNtbAmount = 0.0, totalInvoicedVatAmount = 0.0, finalTotalInvoiceAmount = 0.0;
        Double totalInvoicedCurrentAmount = 0.0, totalCurrentAmount = 0.0, totalNewChargeCurrentAmount = 0.0, finalTotalCurrentAmount = 0.0, totalCurrentNtbAmount = 0.0, totalCurrentVatAmount = 0.0;
        Double balanceToBeCreditedAmount = 0.0;
        LocalCreditNoteSummaryDetail localCreditNoteSummaryDetail;

        TaxRegistration VAT = this.taxRegistrationService.findByTaxTypeSeqAndStatusAndCompanyProfileSeq(1, MasterDataStatus.APPROVED.getStatusSeq(), localInvoice.getCompanyProfileSeq());
        TaxRegistration NBT = this.taxRegistrationService.findByTaxTypeSeqAndStatusAndCompanyProfileSeq(2, MasterDataStatus.APPROVED.getStatusSeq(), localInvoice.getCompanyProfileSeq());
        Double vatTaxRate;
        Double nbtTaxRate;
        localCreditNoteSummaryDetail = new LocalCreditNoteSummaryDetail();
        Double totalCurrentInvoicedVatAmount = 0.0, totalCurrentInvoicedNtbAmount = 0.0;
        Double totalCurrentNewChargeVatAmount = 0.0, totalCurrentNewChargeNtbAmount = 0.0;

        if (lcnChargeDetailListNew.size() > 0) {
            List<FinancialChargeDetail> newFinancialChargeDetail = new ArrayList<>();
            lcnChargeDetailListNew.forEach(i -> newFinancialChargeDetail.add(i.getFinancialChargeDetail()));
            this.exchangeRateConversion.currencyConversionForLocalInvoiceFinChaDetail(newFinancialChargeDetail, localInvoice.getLocalInvoiceSeq(), localInvoice.getBaseCurrencySeq(), localInvoice.getExchangeRateSeq());
            LocalCreditNoteHeader localCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(i -> i.getCreatedDate()));
            List<LcnTaxRateMapping> dbLcnTaxRateMappingList = this.lcnTaxRateMappingService.findByLocalCreditNoteHeaderSeq(lcnChargeDetailListNew.get(0).getLocalCreditNoteHeaderSeq());

            for (LcnChargeDetail newLcnChargeDetail : lcnChargeDetailListNew) {
                for (LcnTaxRateMapping frontLcnTaxRateMapping : taxRateMappingList) {
                    frontLcnTaxRateMapping.setVatAmount(0.0);
                    frontLcnTaxRateMapping.setOtherTaxAmount(0.0);
                    if (dbLcnTaxRateMappingList.size() > 0) {
                        LcnTaxRateMapping dbLcnTaxRateMapping = dbLcnTaxRateMappingList.get(0);
                        vatTaxRate = dbLcnTaxRateMapping.getTaxVatRate() / 100;
                        nbtTaxRate = dbLcnTaxRateMapping.getTaxOtherRate() / 100;

                        if (newLcnChargeDetail.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (frontLcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                totalCurrentInvoicedNtbAmount += (newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate);
                                if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    totalCurrentInvoicedVatAmount += ((newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate) + newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount()) * vatTaxRate;
                                    frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                                }
                                frontLcnTaxRateMapping.setOtherTaxAmount(totalCurrentInvoicedNtbAmount);
                            } else if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                totalCurrentInvoicedVatAmount += newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * vatTaxRate;
                                frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                            }
                        }

                        //set vat and nbt difference for multiple credit note
                        for (LcnTaxRateMapping taxRateMapping : localCreditNoteHeader.getLcnTaxRateMappingList()) {
                            if (taxRateMapping.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                                frontLcnTaxRateMapping.setVatDifference((taxRateMapping.getVatAmount() != null ? taxRateMapping.getVatAmount() : 0) - totalCurrentInvoicedVatAmount);
                                frontLcnTaxRateMapping.setNbtDifference((taxRateMapping.getOtherTaxAmount() != null ? taxRateMapping.getOtherTaxAmount() : 0) - totalCurrentInvoicedNtbAmount);
                                frontLcnTaxRateMapping.setLocalInvTaxRateMappingSeq(taxRateMapping.getLocalInvTaxRateMappingSeq());
                            } else {
                                frontLcnTaxRateMapping.setVatDifference(0 - totalCurrentInvoicedVatAmount);
                                frontLcnTaxRateMapping.setNbtDifference(0 - totalCurrentInvoicedNtbAmount);
                            }
                        }
                    } else {
                        vatTaxRate = VAT.getTaxRate() / 100;
                        nbtTaxRate = NBT.getTaxRate() / 100;
                        if (newLcnChargeDetail.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (frontLcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) { // when NBT
                                totalCurrentInvoicedNtbAmount += newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate;
                                if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) { // When NBT And VAT
                                    totalCurrentInvoicedVatAmount += ((newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate) + newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount()) * vatTaxRate;
                                    frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedNtbAmount);
                                }
                                frontLcnTaxRateMapping.setOtherTaxAmount(totalCurrentInvoicedNtbAmount);
                            } else if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) { // When VAT
                                totalCurrentInvoicedVatAmount += newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * vatTaxRate;
                                frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                            }
                        }

                        frontLcnTaxRateMapping.setVatDifference(0 - totalCurrentInvoicedVatAmount);
                        frontLcnTaxRateMapping.setNbtDifference(0 - totalCurrentInvoicedNtbAmount);
                    }
                }
            }

            vatTaxRate = VAT.getTaxRate() / 100;
            nbtTaxRate = NBT.getTaxRate() / 100;
            for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
                if (taxRateMappingList.size() > 0) {
                    for (LcnTaxRateMapping lcnTaxRateMapping : taxRateMappingList) {
                        lcnTaxRateMapping.setVatAmount(0.0);
                        lcnTaxRateMapping.setOtherTaxAmount(0.0);
                        if (financialChargeDetail.getFinancialChargeDetailSeq().equals(lcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                totalCurrentNewChargeNtbAmount += financialChargeDetail.getConvertedAmount() * nbtTaxRate;
                                if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    totalCurrentNewChargeVatAmount += ((financialChargeDetail.getConvertedAmount() * nbtTaxRate) + financialChargeDetail.getConvertedAmount()) * vatTaxRate;
                                    lcnTaxRateMapping.setVatAmount(totalCurrentNewChargeVatAmount);
                                }
                                lcnTaxRateMapping.setOtherTaxAmount(totalCurrentNewChargeNtbAmount);
                            } else if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                totalCurrentNewChargeVatAmount += financialChargeDetail.getConvertedAmount() * vatTaxRate;
                                lcnTaxRateMapping.setVatAmount(totalCurrentNewChargeVatAmount);
                            }
                        }
                        lcnTaxRateMapping.setVatDifference(0 - totalCurrentNewChargeVatAmount);
                        lcnTaxRateMapping.setNbtDifference(0 - totalCurrentNewChargeNtbAmount);
                    }
                }
            }

            if (lcnChargeDetailListNew.size() > 0) {
                totalInvoicedNtbAmount = this.convertValueIntoTwoDecimal(localCreditNoteHeader.getTotalCurrentNbtAmount());
                totalInvoicedVatAmount = this.convertValueIntoTwoDecimal(localCreditNoteHeader.getTotalCurrentVatAmount());
            }

            for (LcnChargeDetail lcnChargeDetail : lcnChargeDetailListNew) {
                totalInvoicedAmount += lcnChargeDetail.getCurrentAmount();
                totalInvoicedCurrentAmount += lcnChargeDetail.getFinancialChargeDetail().getConvertedAmount();
            }
        } else {
            for (LocalInvFinChargeMapping localInvFinChargeMapping : localInvFinChargeMappingList) {
                List<LocalInvTaxRateMapping> localInvTaxRateMappingList = this.localInvTaxRateMappingService.findByLocalInvoiceSeq(localInvFinChargeMapping.getLocalInvoiceSeq());
                for (LcnTaxRateMapping lcnTaxRateMapping : taxRateMappingList) {
                    lcnTaxRateMapping.setVatAmount(0.0);
                    lcnTaxRateMapping.setOtherTaxAmount(0.0);
                    if (localInvTaxRateMappingList.size() > 0) {
                        LocalInvTaxRateMapping dbLocalInvTaxRateMapping = localInvTaxRateMappingList.get(0);
                        vatTaxRate = dbLocalInvTaxRateMapping.getTaxVatRate() / 100;
                        nbtTaxRate = dbLocalInvTaxRateMapping.getTaxOtherRate() / 100;

                        if (localInvFinChargeMapping.getFinancialChargeDetailSeq().equals(lcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                totalCurrentInvoicedNtbAmount += (localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate);
                                if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    totalCurrentInvoicedVatAmount += ((localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate) + localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount()) * vatTaxRate;
                                    lcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                                }
                                lcnTaxRateMapping.setOtherTaxAmount(totalCurrentInvoicedNtbAmount);
                            } else if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                totalCurrentInvoicedVatAmount += localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * vatTaxRate;
                                lcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                            }
                        }
                        //set vat and nbt difference
                        LocalInvTaxRateMapping localInvTaxRateMapping = this.localInvTaxRateMappingService.findByLocalInvoiceFinChrMapSeq(localInvFinChargeMapping.getLocalInvoiceFinChrMapSeq());
                        if (localInvTaxRateMapping != null) {
                            lcnTaxRateMapping.setVatDifference((localInvTaxRateMapping.getVatAmount() != null ? localInvTaxRateMapping.getVatAmount() : 0) - totalCurrentInvoicedVatAmount);
                            lcnTaxRateMapping.setNbtDifference((localInvTaxRateMapping.getOtherTaxAmount() != null ? localInvTaxRateMapping.getOtherTaxAmount() : 0) - totalCurrentInvoicedNtbAmount);
                            lcnTaxRateMapping.setLocalInvTaxRateMappingSeq(localInvTaxRateMapping.getLocalInvTaxRateMappingSeq());
                        } else {
                            lcnTaxRateMapping.setVatDifference(0 - totalCurrentInvoicedVatAmount);
                            lcnTaxRateMapping.setNbtDifference(0 - totalCurrentInvoicedNtbAmount);
                        }
                    } else {
                        vatTaxRate = VAT.getTaxRate() / 100;
                        nbtTaxRate = NBT.getTaxRate() / 100;
                        if (localInvFinChargeMapping.getFinancialChargeDetailSeq().equals(lcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) { // when NBT
                                totalCurrentInvoicedNtbAmount += localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate;
                                if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null) { // When NBT And VAT
                                    totalCurrentInvoicedVatAmount += ((localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate) + localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount()) * vatTaxRate;
                                    lcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                                }
                                lcnTaxRateMapping.setOtherTaxAmount(totalCurrentInvoicedNtbAmount);
                            } else if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null) { // When VAT
                                totalCurrentInvoicedVatAmount += localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * vatTaxRate;
                                lcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                            }
                        }
                        //set vat and nbt difference for invoiced charge lines
                        lcnTaxRateMapping.setVatDifference(0 - totalCurrentInvoicedVatAmount);
                        lcnTaxRateMapping.setNbtDifference(0 - totalCurrentInvoicedNtbAmount);
                    }
                }
            }

            vatTaxRate = VAT.getTaxRate() / 100;
            nbtTaxRate = NBT.getTaxRate() / 100;
            for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
                if (taxRateMappingList.size() > 0) {
                    for (LcnTaxRateMapping lcnTaxRateMapping : taxRateMappingList) {
                        lcnTaxRateMapping.setVatAmount(0.0);
                        lcnTaxRateMapping.setOtherTaxAmount(0.0);
                        if (financialChargeDetail.getFinancialChargeDetailSeq().equals(lcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                totalCurrentNewChargeNtbAmount += financialChargeDetail.getConvertedAmount() * nbtTaxRate;
                                if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    totalCurrentNewChargeVatAmount += ((financialChargeDetail.getConvertedAmount() * nbtTaxRate) + financialChargeDetail.getConvertedAmount()) * vatTaxRate;
                                    lcnTaxRateMapping.setVatAmount(totalCurrentNewChargeVatAmount);
                                }
                                lcnTaxRateMapping.setOtherTaxAmount(totalCurrentNewChargeNtbAmount);
                            } else if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                totalCurrentNewChargeVatAmount += financialChargeDetail.getConvertedAmount() * vatTaxRate;
                                lcnTaxRateMapping.setVatAmount(totalCurrentNewChargeVatAmount);
                            }
                        }
                        //set vat and nbt difference for new charge line
                        lcnTaxRateMapping.setVatDifference(0 - totalCurrentNewChargeVatAmount);
                        lcnTaxRateMapping.setNbtDifference(0 - totalCurrentNewChargeNtbAmount);
                    }
                }
            }

            if (localInvFinChargeMappingList.size() > 0) {
                totalInvoicedNtbAmount = localInvoice.getFinalNbtAmount();
                totalInvoicedVatAmount = localInvoice.getFinalVatAmount();
            }

            for (LocalInvFinChargeMapping localInvFinChargeMapping : localInvFinChargeMappingList) {
                totalInvoicedAmount += localInvFinChargeMapping.getAmount();
                totalInvoicedCurrentAmount += localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount();
            }
        }

        for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
            totalNewChargeCurrentAmount += financialChargeDetail.getConvertedAmount();
        }

        totalCurrentNtbAmount = totalCurrentInvoicedNtbAmount + totalCurrentNewChargeNtbAmount;
        totalCurrentVatAmount = totalCurrentInvoicedVatAmount + totalCurrentNewChargeVatAmount;

        finalTotalInvoiceAmount = totalInvoicedAmount + totalInvoicedNtbAmount + totalInvoicedVatAmount;
        localCreditNoteSummaryDetail.setTotalInvoicedVatAmount(totalInvoicedVatAmount);
        localCreditNoteSummaryDetail.setTotalInvoicedNbtAmount(totalInvoicedNtbAmount);
        localCreditNoteSummaryDetail.setTotalInvoicedAmount(totalInvoicedAmount);
        localCreditNoteSummaryDetail.setFinalTotalInvoiceAmount(finalTotalInvoiceAmount);

        totalCurrentAmount = totalInvoicedCurrentAmount + totalNewChargeCurrentAmount;
        finalTotalCurrentAmount = totalCurrentAmount + totalCurrentNtbAmount + totalCurrentVatAmount;
        localCreditNoteSummaryDetail.setTotalCurrentNbtAmount(totalCurrentNtbAmount);
        localCreditNoteSummaryDetail.setTotalCurrentVatAmount(totalCurrentVatAmount);
        localCreditNoteSummaryDetail.setTotalCurrentAmount(totalCurrentAmount);
        localCreditNoteSummaryDetail.setFinalTotalCurrentAmount(finalTotalCurrentAmount);

        balanceToBeCreditedAmount = this.convertValueIntoTwoDecimal(finalTotalInvoiceAmount) - this.convertValueIntoTwoDecimal(finalTotalCurrentAmount);
        localCreditNoteSummaryDetail.setBalanceToBeCreditedAmount(this.convertValueIntoTwoDecimal(balanceToBeCreditedAmount));
        if (balanceToBeCreditedAmount > 0) {
            localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.CREDIT.getEntryTypeSeq());
        } else {
            localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.DEBIT.getEntryTypeSeq());
        }
        localCreditNoteSummaryDetail.setCurrencyCode(localInvoice.getCurrency().getCurrencyCode());
        return localCreditNoteSummaryDetail;
    }

    private LocalCreditNoteSummaryDetail getLocalCreditNoteSummaryForExpenseVoucher(LocalCreditNoteHeader localCreditNoteHeader, HttpServletRequest request) {
        LocalCreditNoteSummaryDetail localCreditNoteSummaryDetail;
        List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
        List<ExpenseVouFinChargeMapping> expenseVouFinChargeMappingList = new ArrayList<>();
        List<LcnChargeDetail> lcnChargeDetailList = new ArrayList<>();

        String[] financialChargeDetailSeqList = request.getParameterValues("financialChargeDetailSeq[]");
        List<Integer> intFinancialChargeDetailSeqList = Arrays.asList(financialChargeDetailSeqList).stream().map(Integer::valueOf).collect(Collectors.toList());
        ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(localCreditNoteHeader.getExpenseVoucherSeq());
        List<LcnTaxRateMapping> taxRateMappingList = localCreditNoteHeader.getLcnTaxRateMappingList().stream().filter(i -> i.getTaxRegistrationVatSeq() != null || i.getTaxRegistrationOtherTaxSeq() != null).collect(Collectors.toList());
        List<LocalCreditNoteHeader> localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByExpenseVoucherSeqAndStatusNotIn(localCreditNoteHeader.getExpenseVoucherSeq(), MasterDataStatus.DELETED.getStatusSeq());

        if (localCreditNoteHeaderList.size() > 0) {
            LocalCreditNoteHeader dbLocalCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(i -> i.getCreatedDate()));
            List<Integer> newFinancialChargeDetailSeqList = new ArrayList<>();
            for (Integer intFinancialChargeDetailSeq : intFinancialChargeDetailSeqList) {
                Optional<LcnChargeDetail> financialChargeDetailSeq = dbLocalCreditNoteHeader.getLcnChargeDetailList().stream().filter(i -> i.getFinancialChargeDetailSeq().equals(intFinancialChargeDetailSeq)).findFirst();
                if (financialChargeDetailSeq.isPresent()) {
                    lcnChargeDetailList.add(financialChargeDetailSeq.get());
                } else {
                    newFinancialChargeDetailSeqList.add(intFinancialChargeDetailSeq);
                }
            }

            for (Integer intFinancialChargeDetailSeq : newFinancialChargeDetailSeqList) {
                FinancialChargeDetail financialChargeDetail = this.financialChargeDetailService.findOne(intFinancialChargeDetailSeq);
                financialChargeDetailList.add(financialChargeDetail);
            }

            if (financialChargeDetailList.size() > 0) {
                financialChargeDetailList = this.exchangeRateConversion.currencyConversionForExpenseVoucherFinChaDetail(financialChargeDetailList, expenseVoucher.getExpenseVoucherSeq(), expenseVoucher.getBaseCurrencySeq(), expenseVoucher.getExchangeRateSeq());
            }
        } else {
            for (Integer intFinancialChargeDetailSeq : intFinancialChargeDetailSeqList) {
                ExpenseVouFinChargeMapping expenseVouFinChargeMapping = this.expenseVouFinChargeMappingService.findByExpenseVoucherSeqAndFinancialChargeDetailSeq(localCreditNoteHeader.getExpenseVoucherSeq(), intFinancialChargeDetailSeq);
                if (expenseVouFinChargeMapping != null) {
                    expenseVouFinChargeMappingList.add(expenseVouFinChargeMapping);
                } else {
                    FinancialChargeDetail financialChargeDetail = this.financialChargeDetailService.findOne(intFinancialChargeDetailSeq);
                    financialChargeDetailList.add(financialChargeDetail);
                }
            }
            if (expenseVouFinChargeMappingList.size() > 0) {
                expenseVouFinChargeMappingList = this.exchangeRateConversion.currencyConversionForExpenseVoucher(expenseVouFinChargeMappingList, expenseVoucher.getExchangeRateSeq());
            }
            if (financialChargeDetailList.size() > 0) {
                financialChargeDetailList = this.exchangeRateConversion.currencyConversionForExpenseVoucherFinChaDetail(financialChargeDetailList, expenseVoucher.getExpenseVoucherSeq(), expenseVoucher.getBaseCurrencySeq(), expenseVoucher.getExchangeRateSeq());
            }
        }
        localCreditNoteSummaryDetail = this.getExpenseVoucherSummaryDetail(expenseVouFinChargeMappingList, financialChargeDetailList, taxRateMappingList, lcnChargeDetailList, expenseVoucher, localCreditNoteHeaderList);
        return localCreditNoteSummaryDetail;
    }

    private LocalCreditNoteSummaryDetail getExpenseVoucherSummaryDetail(List<ExpenseVouFinChargeMapping> expenseVouFinChargeMappingList,
                                                                        List<FinancialChargeDetail> financialChargeDetailList,
                                                                        List<LcnTaxRateMapping> taxRateMappingList,
                                                                        List<LcnChargeDetail> newLcnChargeDetailList,
                                                                        ExpenseVoucher expenseVoucher,
                                                                        List<LocalCreditNoteHeader> localCreditNoteHeaderList) {
        Double totalInvoicedAmount = 0.0, totalInvoicedNtbAmount = 0.0, totalInvoicedVatAmount = 0.0, finalTotalInvoiceAmount = 0.0;
        Double totalInvoicedCurrentAmount = 0.0, totalCurrentAmount = 0.0, totalNewChargeCurrentAmount = 0.0,
                finalTotalCurrentAmount = 0.0, totalCurrentNtbAmount = 0.0, totalCurrentVatAmount = 0.0;
        Double balanceToBeCreditedAmount = 0.0;
        LocalCreditNoteSummaryDetail localCreditNoteSummaryDetail;

        TaxRegistration VAT = this.taxRegistrationService.findByTaxTypeSeqAndStatusAndCompanyProfileSeq(1, MasterDataStatus.APPROVED.getStatusSeq(), expenseVoucher.getCompanyProfileSeq());
        TaxRegistration NBT = this.taxRegistrationService.findByTaxTypeSeqAndStatusAndCompanyProfileSeq(2, MasterDataStatus.APPROVED.getStatusSeq(), expenseVoucher.getCompanyProfileSeq());
        Double vatTaxRate;
        Double nbtTaxRate;

        localCreditNoteSummaryDetail = new LocalCreditNoteSummaryDetail();
        Double totalCurrentInvoicedVatAmount = 0.0, totalCurrentInvoicedNtbAmount = 0.0;

        if (newLcnChargeDetailList.size() > 0) {

            List<FinancialChargeDetail> newFinancialChargeDetail = new ArrayList<>();
            newLcnChargeDetailList.forEach(i -> newFinancialChargeDetail.add(i.getFinancialChargeDetail()));
            this.exchangeRateConversion.currencyConversionForExpenseVoucherFinChaDetail(newFinancialChargeDetail, expenseVoucher.getExpenseVoucherSeq(), expenseVoucher.getBaseCurrencySeq(), expenseVoucher.getExchangeRateSeq());
            LocalCreditNoteHeader localCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(i -> i.getCreatedDate()));
            List<LcnTaxRateMapping> lcnTaxRateMappingList = this.lcnTaxRateMappingService.findByLocalCreditNoteHeaderSeq(newLcnChargeDetailList.get(0).getLocalCreditNoteHeaderSeq());

            for (LcnChargeDetail newLcnChargeDetail : newLcnChargeDetailList) {
                for (LcnTaxRateMapping frontLcnTaxRateMapping : taxRateMappingList) {
                    frontLcnTaxRateMapping.setVatAmount(0.0);
                    frontLcnTaxRateMapping.setOtherTaxAmount(0.0);
                    if (lcnTaxRateMappingList.size() > 0) {
                        LcnTaxRateMapping dbLcnTaxRateMapping = lcnTaxRateMappingList.get(0);
                        vatTaxRate = dbLcnTaxRateMapping.getTaxVatRate() / 100;
                        nbtTaxRate = dbLcnTaxRateMapping.getTaxOtherRate() / 100;
                        if (newLcnChargeDetail.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (frontLcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                totalCurrentInvoicedNtbAmount += (newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate);
                                if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    totalCurrentInvoicedVatAmount += ((newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate) + newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount()) * vatTaxRate;
                                    frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                                }
                                frontLcnTaxRateMapping.setOtherTaxAmount(totalCurrentInvoicedNtbAmount);
                            } else if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                totalCurrentInvoicedVatAmount += newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * vatTaxRate;
                                frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                            }
                        }

                        //set vat and nbt difference for multiple credit note
                        for (LcnTaxRateMapping taxRateMapping : localCreditNoteHeader.getLcnTaxRateMappingList()) {
                            if (taxRateMapping.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                                frontLcnTaxRateMapping.setVatDifference((taxRateMapping.getVatAmount() != null ? taxRateMapping.getVatAmount() : 0) - totalCurrentInvoicedVatAmount);
                                frontLcnTaxRateMapping.setNbtDifference((taxRateMapping.getOtherTaxAmount() != null ? taxRateMapping.getOtherTaxAmount() : 0) - totalCurrentInvoicedNtbAmount);
                                frontLcnTaxRateMapping.setLocalInvTaxRateMappingSeq(taxRateMapping.getLocalInvTaxRateMappingSeq());
                            } else {
                                frontLcnTaxRateMapping.setVatDifference(0 - totalCurrentInvoicedVatAmount);
                                frontLcnTaxRateMapping.setNbtDifference(0 - totalCurrentInvoicedNtbAmount);
                            }
                        }
                    } else {
                        vatTaxRate = VAT.getTaxRate() / 100;
                        nbtTaxRate = NBT.getTaxRate() / 100;
                        if (newLcnChargeDetail.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (frontLcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) { // when NBT
                                totalCurrentInvoicedNtbAmount += newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate;
                                if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) { // When NBT And VAT
                                    totalCurrentInvoicedVatAmount += ((newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate) + newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount()) * vatTaxRate;
                                    frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedNtbAmount);
                                }
                                frontLcnTaxRateMapping.setOtherTaxAmount(totalCurrentInvoicedNtbAmount);
                            } else if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) { // When VAT
                                totalCurrentInvoicedVatAmount += newLcnChargeDetail.getFinancialChargeDetail().getConvertedAmount() * vatTaxRate;
                                frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedNtbAmount);
                            }
                        }
                        frontLcnTaxRateMapping.setVatDifference(0 - totalCurrentInvoicedVatAmount);
                        frontLcnTaxRateMapping.setNbtDifference(0 - totalCurrentInvoicedNtbAmount);
                    }
                }
            }

            vatTaxRate = VAT.getTaxRate() / 100;
            nbtTaxRate = NBT.getTaxRate() / 100;
            Double totalCurrentNewChargeVatAmount = 0.0, totalCurrentNewChargeNtbAmount = 0.0;
            for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
                if (taxRateMappingList.size() > 0) {
                    for (LcnTaxRateMapping frontLcnTaxRateMapping : taxRateMappingList) {
                        if (financialChargeDetail.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (frontLcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                totalCurrentNewChargeNtbAmount += financialChargeDetail.getConvertedAmount() * nbtTaxRate;
                                if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    totalCurrentNewChargeVatAmount += ((financialChargeDetail.getConvertedAmount() * nbtTaxRate) + financialChargeDetail.getConvertedAmount()) * vatTaxRate;
                                    frontLcnTaxRateMapping.setVatAmount(totalCurrentNewChargeVatAmount);
                                }
                                frontLcnTaxRateMapping.setOtherTaxAmount(totalCurrentNewChargeNtbAmount);
                            } else if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                totalCurrentNewChargeVatAmount += financialChargeDetail.getConvertedAmount() * vatTaxRate;
                                frontLcnTaxRateMapping.setVatAmount(totalCurrentNewChargeVatAmount);
                            }
                        }
                        frontLcnTaxRateMapping.setVatDifference(0 - totalCurrentNewChargeVatAmount);
                        frontLcnTaxRateMapping.setNbtDifference(0 - totalCurrentNewChargeNtbAmount);
                    }
                }
            }

            totalCurrentNtbAmount = totalCurrentInvoicedNtbAmount + totalCurrentNewChargeNtbAmount;
            totalCurrentVatAmount = totalCurrentInvoicedVatAmount + totalCurrentNewChargeVatAmount;

            if (newLcnChargeDetailList.size() > 0) {
                totalInvoicedNtbAmount = this.convertValueIntoTwoDecimal(localCreditNoteHeader.getTotalCurrentNbtAmount());
                totalInvoicedVatAmount = this.convertValueIntoTwoDecimal(localCreditNoteHeader.getTotalCurrentVatAmount());
            }

            for (LcnChargeDetail lcnChargeDetail : newLcnChargeDetailList) {
                totalInvoicedAmount += lcnChargeDetail.getCurrentAmount();
                totalInvoicedCurrentAmount += lcnChargeDetail.getFinancialChargeDetail().getConvertedAmount();
            }
        } else {
            for (ExpenseVouFinChargeMapping expenseVouFinChargeMapping : expenseVouFinChargeMappingList) {
                List<ExpenseVouTaxRateMapping> expenseVouTaxRateMappingList = this.expenseVouTaxRateMappingService.findByExpenseVoucherSeq(expenseVouFinChargeMapping.getExpenseVoucherSeq());
                for (LcnTaxRateMapping frontLcnTaxRateMapping : taxRateMappingList) {
                    frontLcnTaxRateMapping.setVatAmount(0.0);
                    frontLcnTaxRateMapping.setOtherTaxAmount(0.0);
                    if (expenseVouTaxRateMappingList.size() > 0) {
                        ExpenseVouTaxRateMapping dbExpenseVouTaxRateMapping = expenseVouTaxRateMappingList.get(0);
                        vatTaxRate = dbExpenseVouTaxRateMapping.getTaxVatRate() / 100;
                        nbtTaxRate = dbExpenseVouTaxRateMapping.getTaxOtherRate() / 100;

                        if (expenseVouFinChargeMapping.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (frontLcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                totalCurrentInvoicedNtbAmount += expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate;
                                if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    totalCurrentInvoicedVatAmount += ((expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate) + expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount()) * vatTaxRate;
                                    frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                                }
                                frontLcnTaxRateMapping.setOtherTaxAmount(totalCurrentInvoicedNtbAmount);
                            } else if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                totalCurrentInvoicedVatAmount += (expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * vatTaxRate);
                                frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                            }
                        }
                        //set vat and nbt difference
                        *//*ExpenseVouTaxRateMapping expenseVouTaxRateMapping = this.expenseVouTaxRateMappingService.findByLocalInvoiceFinChrMapSeq(localInvFinChargeMapping.getLocalInvoiceFinChrMapSeq()); if (localInvTaxRateMapping != null) { frontLcnTaxRateMapping.setVatDifference((localInvTaxRateMapping.getVatAmount() != null ? localInvTaxRateMapping.getVatAmount() : 0) - totalCurrentInvoicedVatAmount); frontLcnTaxRateMapping.setNbtDifference((localInvTaxRateMapping.getOtherTaxAmount() != null ? localInvTaxRateMapping.getOtherTaxAmount() : 0) - totalCurrentInvoicedNtbAmount); frontLcnTaxRateMapping.setLocalInvTaxRateMappingSeq(localInvTaxRateMapping.getLocalInvTaxRateMappingSeq()); } else { frontLcnTaxRateMapping.setVatDifference(0 - totalCurrentInvoicedVatAmount); frontLcnTaxRateMapping.setNbtDifference(0 - totalCurrentInvoicedNtbAmount); }*//*
                    } else {
                        vatTaxRate = VAT.getTaxRate() / 100;
                        nbtTaxRate = NBT.getTaxRate() / 100;
                        if (expenseVouFinChargeMapping.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (frontLcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                totalCurrentInvoicedNtbAmount += expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate;
                                if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    totalCurrentInvoicedVatAmount += ((expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * nbtTaxRate) + expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount()) * vatTaxRate;
                                    frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                                }
                                frontLcnTaxRateMapping.setOtherTaxAmount(totalCurrentInvoicedNtbAmount);
                            } else if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                totalCurrentInvoicedVatAmount += expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount() * vatTaxRate;
                                frontLcnTaxRateMapping.setVatAmount(totalCurrentInvoicedVatAmount);
                            }
                        }
                        //set vat and nbt difference for invoiced charge lines
                        frontLcnTaxRateMapping.setVatDifference(0 - totalCurrentInvoicedVatAmount);
                        frontLcnTaxRateMapping.setNbtDifference(0 - totalCurrentInvoicedNtbAmount);
                    }
                }
            }

            vatTaxRate = VAT.getTaxRate() / 100;
            nbtTaxRate = NBT.getTaxRate() / 100;
            Double totalCurrentNewChargeVatAmount = 0.0, totalCurrentNewChargeNtbAmount = 0.0;
            for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
                if (taxRateMappingList.size() > 0) {
                    for (LcnTaxRateMapping frontLcnTaxRateMapping : taxRateMappingList) {
                        if (financialChargeDetail.getFinancialChargeDetailSeq().equals(frontLcnTaxRateMapping.getFinancialChargeDetailSeq())) {
                            if (frontLcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                                totalCurrentNewChargeNtbAmount += financialChargeDetail.getConvertedAmount() * nbtTaxRate;
                                if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                    totalCurrentNewChargeVatAmount += ((financialChargeDetail.getConvertedAmount() * nbtTaxRate) + financialChargeDetail.getConvertedAmount()) * vatTaxRate;
                                    frontLcnTaxRateMapping.setVatAmount(totalCurrentNewChargeVatAmount);
                                }
                                frontLcnTaxRateMapping.setOtherTaxAmount(totalCurrentNewChargeNtbAmount);
                            } else if (frontLcnTaxRateMapping.getTaxRegistrationVatSeq() != null) {
                                totalCurrentNewChargeVatAmount += financialChargeDetail.getConvertedAmount() * vatTaxRate;
                                frontLcnTaxRateMapping.setVatAmount(totalCurrentNewChargeVatAmount);
                            }
                        }
                        //set vat and nbt difference for new charge line
                        frontLcnTaxRateMapping.setVatDifference(0 - totalCurrentNewChargeVatAmount);
                        frontLcnTaxRateMapping.setNbtDifference(0 - totalCurrentNewChargeNtbAmount);
                    }
                }
            }

            totalCurrentNtbAmount = totalCurrentInvoicedNtbAmount + totalCurrentNewChargeNtbAmount;
            totalCurrentVatAmount = totalCurrentInvoicedVatAmount + totalCurrentNewChargeVatAmount;

            if (expenseVouFinChargeMappingList.size() > 0) {
                totalInvoicedNtbAmount = expenseVoucher.getFinalNbtAmount();
                totalInvoicedVatAmount = expenseVoucher.getFinalVatAmount();
            }

            for (ExpenseVouFinChargeMapping expenseVouFinChargeMapping : expenseVouFinChargeMappingList) {
                totalInvoicedAmount += expenseVouFinChargeMapping.getAmount();
                totalInvoicedCurrentAmount += expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount();
            }
        }
        finalTotalInvoiceAmount = totalInvoicedAmount + totalInvoicedNtbAmount + totalInvoicedVatAmount;
        localCreditNoteSummaryDetail.setTotalInvoicedVatAmount(totalInvoicedVatAmount);
        localCreditNoteSummaryDetail.setTotalInvoicedNbtAmount(totalInvoicedNtbAmount);
        localCreditNoteSummaryDetail.setTotalInvoicedAmount(totalInvoicedAmount);
        localCreditNoteSummaryDetail.setFinalTotalInvoiceAmount(finalTotalInvoiceAmount);

        for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
            totalNewChargeCurrentAmount += financialChargeDetail.getConvertedAmount();
        }

        totalCurrentAmount = totalInvoicedCurrentAmount + totalNewChargeCurrentAmount;
        finalTotalCurrentAmount = totalCurrentAmount + totalCurrentNtbAmount + totalCurrentVatAmount;
        localCreditNoteSummaryDetail.setTotalCurrentVatAmount(totalCurrentVatAmount);
        localCreditNoteSummaryDetail.setTotalCurrentNbtAmount(totalCurrentNtbAmount);
        localCreditNoteSummaryDetail.setTotalCurrentAmount(totalCurrentAmount);
        localCreditNoteSummaryDetail.setFinalTotalCurrentAmount(finalTotalCurrentAmount);

        balanceToBeCreditedAmount = this.convertValueIntoTwoDecimal(finalTotalInvoiceAmount) - this.convertValueIntoTwoDecimal(finalTotalCurrentAmount);
        localCreditNoteSummaryDetail.setBalanceToBeCreditedAmount(this.convertValueIntoTwoDecimal(balanceToBeCreditedAmount));
        if (balanceToBeCreditedAmount > 0) {
            localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.CREDIT.getEntryTypeSeq());
        } else {
            localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.DEBIT.getEntryTypeSeq());
        }
        localCreditNoteSummaryDetail.setCurrencyCode(expenseVoucher.getCurrency().getCurrencyCode());
        return localCreditNoteSummaryDetail;
    }*/

    private List<LocalCreditNoteFinancialChargeDetail> prepareNewLocalCreditNoteFinancialChargeDetailListForLocalInvoice(Integer localInvoiceSeq) {
        List<LocalCreditNoteHeader> localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByLocalInvoiceSeqAndStatusNotIn(localInvoiceSeq, MasterDataStatus.DELETED.getStatusSeq());
        LocalInvoice localInvoice = this.localInvoiceService.findOne(localInvoiceSeq);
        FinancialCharge financialCharge = this.financialChargeService.findOne(localInvoice.getFinancialChargeSeq(), EntityGraphUtils.fromName("FinancialCharge.create"));
        List<LocalCreditNoteFinancialChargeDetail> localCreditNoteFinancialChargeDetailList = new ArrayList<>();
        Integer AI = this.moduleService.findByModuleCode("AI").getModuleSeq(); // moduleSeq = 10;
        Integer AE = this.moduleService.findByModuleCode("AE").getModuleSeq(); // moduleSeq = 13;
        Integer OI = this.moduleService.findByModuleCode("OI").getModuleSeq(); // moduleSeq = 11;
        Integer OE = this.moduleService.findByModuleCode("OE").getModuleSeq(); // moduleSeq = 14;
        Integer WHARF = this.moduleService.findByModuleCode("WHARF").getModuleSeq(); // moduleSeq = 12;
        Integer CLEARANCE = this.moduleService.findByModuleCode("CLEARANCE").getModuleSeq(); // moduleSeq = 107;

        /*if (localCreditNoteHeaderList.size() > 0) {
            LocalCreditNoteHeader localCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(i -> i.getCreatedDate()));
            List<FinancialChargeDetail> existFinancialChargeDetailList = new ArrayList<>();
            List<LcnChargeDetail> lcnChargeDetailList = localCreditNoteHeader.getLcnChargeDetailList();
            lcnChargeDetailList.forEach(i -> existFinancialChargeDetailList.add(i.getFinancialChargeDetail()));
            this.exchangeRateConversion.currencyConversionForLocalInvoiceFinChaDetail(existFinancialChargeDetailList, localInvoice.getLocalInvoiceSeq(), localInvoice.getBaseCurrencySeq(), localInvoice.getExchangeRateSeq());
            for (LcnChargeDetail lcnChargeDetail : lcnChargeDetailList) {
                LocalCreditNoteFinancialChargeDetail localCreditNoteFinancialChargeDetail = new LocalCreditNoteFinancialChargeDetail();
                localCreditNoteFinancialChargeDetail.setChargeSeq(lcnChargeDetail.getChargeSeq());
                localCreditNoteFinancialChargeDetail.setChargeName(lcnChargeDetail.getCharge().getChargeName());
                if (lcnChargeDetail.getFinancialCharge().getHouseBlSeq() != null) {
                    localCreditNoteFinancialChargeDetail.setHouseBlSeq(lcnChargeDetail.getFinancialCharge().getHouseBlSeq());
                    localCreditNoteFinancialChargeDetail.setHawbNo(lcnChargeDetail.getFinancialCharge().getHouseBl().getHawbNo());
                } else if (lcnChargeDetail.getFinancialCharge().getMasterBlSeq() != null) {
                    localCreditNoteFinancialChargeDetail.setMasterBlSeq(lcnChargeDetail.getFinancialCharge().getMasterBlSeq());
                    localCreditNoteFinancialChargeDetail.setMasterAwbNo(lcnChargeDetail.getFinancialCharge().getMasterBlAux().getMasterAwbNo());
                } else if (lcnChargeDetail.getFinancialCharge().getInbondSeq() != null) {
                    localCreditNoteFinancialChargeDetail.setInbondSeq(lcnChargeDetail.getFinancialCharge().getInbondSeq());
                    if (lcnChargeDetail.getFinancialCharge().getInbond().getBlTypeSeq().equals(BlType.MBL.getBlTypeSeq())) {
                        localCreditNoteFinancialChargeDetail.setMasterAwbNo(lcnChargeDetail.getFinancialCharge().getInbond().getBlNo());
                    } else {
                        localCreditNoteFinancialChargeDetail.setHawbNo(lcnChargeDetail.getFinancialCharge().getInbond().getBlNo());
                    }
                }
                localCreditNoteFinancialChargeDetail.setCurrencySeq(lcnChargeDetail.getCurrencySeq());
                localCreditNoteFinancialChargeDetail.setCurrencyCode(lcnChargeDetail.getCurrency().getCurrencyCode());
                localCreditNoteFinancialChargeDetail.setInvoicedAmount(lcnChargeDetail.getCurrentAmount());
                localCreditNoteFinancialChargeDetail.setCurrentAmount(lcnChargeDetail.getFinancialChargeDetail().getConvertedAmount());
                localCreditNoteFinancialChargeDetail.setFinancialChargeSeq(lcnChargeDetail.getFinancialChargeSeq());
                localCreditNoteFinancialChargeDetail.setFinancialChargeDetailSeq(lcnChargeDetail.getFinancialChargeDetailSeq());
                localCreditNoteFinancialChargeDetail.setLocalInvoiceFinChrMapSeq(lcnChargeDetail.getLocalInvoiceFinChrMapSeq());
                localCreditNoteFinancialChargeDetail.setLocalInvoiceSeq(localCreditNoteHeader.getLocalInvoiceSeq());
                localCreditNoteFinancialChargeDetail.setLcnChargeDetailSeq(lcnChargeDetail.getLcnChargeDetailSeq());

                LcnTaxRateMapping lcnTaxRateMapping = this.lcnTaxRateMappingService.findByLocalCreditNoteHeaderSeqAndFinancialChargeDetailSeq(localCreditNoteHeader.getLocalCreditNoteHeaderSeq(), lcnChargeDetail.getFinancialChargeDetailSeq());
                if (lcnTaxRateMapping != null) {
                    LocalInvTaxRateMapping localInvTaxRateMapping = new LocalInvTaxRateMapping();
                    List<LocalInvTaxRateMapping> localInvTaxRateMappingList = new ArrayList<>();
                    if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null || lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                        localInvTaxRateMapping.setTaxRegistrationVatSeq(lcnTaxRateMapping.getTaxRegistrationVatSeq());
                        localInvTaxRateMapping.setTaxRegistrationOtherTaxSeq(lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq());
                        localInvTaxRateMappingList.add(localInvTaxRateMapping);
                        localCreditNoteFinancialChargeDetail.setLocalInvTaxRateMappingList(localInvTaxRateMappingList);
                    }
                }
                localCreditNoteFinancialChargeDetailList.add(localCreditNoteFinancialChargeDetail);
            }

            List<FinancialChargeDetail> newFinancialChargeDetailList = new ArrayList<>();
            if (localInvoice.getModuleSeq().equals(AI) || localInvoice.getModuleSeq().equals(OI) || localInvoice.getModuleSeq().equals(CLEARANCE)) {
                newFinancialChargeDetailList = financialCharge.getFinancialChargeDetails().stream().filter(i -> i.getLiStatus() != 3
                        && i.getEvStatus() != 3 && i.getIntInvStatus() != 3 && i.getFinanceIncurredTypeSeq().equals(FinanceIncurredAtType.DESTINATION.getIncurredAtTypeSeq())
                        && i.getFinanceChargeTypeSeq().equals(FinanceChargeType.REVENUE.getChargeTypeSeq())).collect(Collectors.toList());

            } else if (localInvoice.getModuleSeq().equals(AE) || localInvoice.getModuleSeq().equals(OE) || localInvoice.getModuleSeq().equals(WHARF)) {
                newFinancialChargeDetailList = financialCharge.getFinancialChargeDetails().stream().filter(i -> i.getLiStatus() != 3
                        && i.getEvStatus() != 3 && i.getIntInvStatus() != 3 && i.getFinanceIncurredTypeSeq().equals(FinanceIncurredAtType.ORIGIN.getIncurredAtTypeSeq())
                        && i.getFinanceChargeTypeSeq().equals(FinanceChargeType.REVENUE.getChargeTypeSeq())).collect(Collectors.toList());
            }
            this.exchangeRateConversion.currencyConversionForLocalInvoiceFinChaDetail(newFinancialChargeDetailList, localInvoice.getLocalInvoiceSeq(), localInvoice.getBaseCurrencySeq(), localInvoice.getExchangeRateSeq());
            localCreditNoteFinancialChargeDetailList = this.setLocalCreditNoteFinancialChargeDetailForLocalInvoice(localCreditNoteFinancialChargeDetailList, newFinancialChargeDetailList, financialCharge, localInvoice);
        } else {
            List<LocalInvFinChargeMapping> localInvFinChargeMappingList = this.localInvFinChargeMappingService.findByLocalInvoiceSeqAndStatusNotIn(localInvoiceSeq, MasterDataStatus.DELETED.getStatusSeq());

            //currency conversion
            this.exchangeRateConversion.currencyConversionForLocalInvoice(localInvFinChargeMappingList, localInvoice.getExchangeRateSeq());
            for (LocalInvFinChargeMapping localInvFinChargeMapping : localInvFinChargeMappingList) {
                LocalCreditNoteFinancialChargeDetail localCreditNoteFinancialChargeDetail = new LocalCreditNoteFinancialChargeDetail();
                localCreditNoteFinancialChargeDetail.setChargeSeq(localInvFinChargeMapping.getChargeSeq());
                localCreditNoteFinancialChargeDetail.setChargeName(localInvFinChargeMapping.getCharge().getChargeName());

                if (localInvFinChargeMapping.getFinancialCharge().getHouseBlSeq() != null) {
                    localCreditNoteFinancialChargeDetail.setHouseBlSeq(localInvFinChargeMapping.getFinancialCharge().getHouseBlSeq());
                    localCreditNoteFinancialChargeDetail.setHawbNo(localInvFinChargeMapping.getFinancialCharge().getHouseBl().getHawbNo());
                } else if (localInvFinChargeMapping.getFinancialCharge().getMasterBlSeq() != null) {
                    localCreditNoteFinancialChargeDetail.setMasterBlSeq(localInvFinChargeMapping.getFinancialCharge().getMasterBlSeq());
                    localCreditNoteFinancialChargeDetail.setMasterAwbNo(localInvFinChargeMapping.getFinancialCharge().getMasterBlAux().getMasterAwbNo());
                } else if (localInvFinChargeMapping.getFinancialCharge().getInbondSeq() != null) {
                    localCreditNoteFinancialChargeDetail.setInbondSeq(localInvFinChargeMapping.getFinancialCharge().getInbondSeq());
                    if (localInvFinChargeMapping.getFinancialCharge().getInbond().getBlTypeSeq().equals(BlType.MBL.getBlTypeSeq())) {
                        localCreditNoteFinancialChargeDetail.setMasterAwbNo(localInvFinChargeMapping.getFinancialCharge().getInbond().getBlNo());
                    } else {
                        localCreditNoteFinancialChargeDetail.setHawbNo(localInvFinChargeMapping.getFinancialCharge().getInbond().getBlNo());
                    }
                }
                localCreditNoteFinancialChargeDetail.setCurrencySeq(localInvFinChargeMapping.getLocalInvoice().getBaseCurrencySeq());
                localCreditNoteFinancialChargeDetail.setCurrencyCode(localInvFinChargeMapping.getLocalInvoice().getCurrency().getCurrencyCode());
                localCreditNoteFinancialChargeDetail.setInvoicedAmount(localInvFinChargeMapping.getAmount());
                localCreditNoteFinancialChargeDetail.setCurrentAmount(localInvFinChargeMapping.getFinancialChargeDetail().getConvertedAmount());
                localCreditNoteFinancialChargeDetail.setFinancialChargeSeq(localInvFinChargeMapping.getFinancialChargeSeq());
                localCreditNoteFinancialChargeDetail.setFinancialChargeDetailSeq(localInvFinChargeMapping.getFinancialChargeDetailSeq());
                localCreditNoteFinancialChargeDetail.setLocalInvoiceFinChrMapSeq(localInvFinChargeMapping.getLocalInvoiceFinChrMapSeq());
                localCreditNoteFinancialChargeDetail.setLocalInvoiceSeq(localInvFinChargeMapping.getLocalInvoiceSeq());
                localCreditNoteFinancialChargeDetail.setCheckedStatus(localInvFinChargeMapping.getFinancialChargeDetail().getCheckedStatus());

                List<LocalInvTaxRateMapping> localInvTaxRateMappingList = this.localInvTaxRateMappingService.findByLocalInvoiceSeqAndFinancialChargeDetailSeq(localInvFinChargeMapping.getLocalInvoiceSeq(), localInvFinChargeMapping.getFinancialChargeDetailSeq());
                localCreditNoteFinancialChargeDetail.setLocalInvTaxRateMappingList(localInvTaxRateMappingList);
                localCreditNoteFinancialChargeDetailList.add(localCreditNoteFinancialChargeDetail);
            }

            this.exchangeRateConversion.currencyConversionForLocalInvoiceFinChaDetail(financialCharge.getFinancialChargeDetails(), localInvoice.getLocalInvoiceSeq(), localInvoice.getBaseCurrencySeq(), localInvoice.getExchangeRateSeq());
            List<FinancialChargeDetail> financialChargeDetailList = null;
            if (localInvoice.getModuleSeq().equals(AI) || localInvoice.getModuleSeq().equals(OI) || localInvoice.getModuleSeq().equals(CLEARANCE)) {
                financialChargeDetailList = financialCharge.getFinancialChargeDetails().stream().filter(i -> i.getLiStatus() != 3
                        && i.getEvStatus() != 3 && i.getIntInvStatus() != 3 && i.getFinanceIncurredTypeSeq().equals(FinanceIncurredAtType.DESTINATION.getIncurredAtTypeSeq())
                        && i.getFinanceChargeTypeSeq().equals(FinanceChargeType.REVENUE.getChargeTypeSeq())).collect(Collectors.toList());

            } else if (localInvoice.getModuleSeq().equals(AE) || localInvoice.getModuleSeq().equals(OE) || localInvoice.getModuleSeq().equals(WHARF)) {
                financialChargeDetailList = financialCharge.getFinancialChargeDetails().stream().filter(i -> i.getLiStatus() != 3
                        && i.getEvStatus() != 3 && i.getIntInvStatus() != 3 && i.getFinanceIncurredTypeSeq().equals(FinanceIncurredAtType.ORIGIN.getIncurredAtTypeSeq())
                        && i.getFinanceChargeTypeSeq().equals(FinanceChargeType.REVENUE.getChargeTypeSeq())).collect(Collectors.toList());
            }
            localCreditNoteFinancialChargeDetailList = this.setLocalCreditNoteFinancialChargeDetailForLocalInvoice(localCreditNoteFinancialChargeDetailList, financialChargeDetailList, financialCharge, localInvoice);
        }*/
        return localCreditNoteFinancialChargeDetailList;
    }

    private List<LocalCreditNoteFinanceChargeDetailForExpVoucher> prepareNewLocalCreditNoteFinancialChargeDetailListForExpenseVoucher(Integer expenseVoucherSeq) {
        List<LocalCreditNoteHeader> localCreditNoteHeaderList = this.localCreditNoteHeaderService.findByExpenseVoucherSeqAndStatusNotIn(expenseVoucherSeq, MasterDataStatus.DELETED.getStatusSeq());
        ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(expenseVoucherSeq);
        FinancialCharge financialCharge = this.financialChargeService.findOne(expenseVoucher.getFinancialChargeSeq(), EntityGraphUtils.fromName("FinancialCharge.create"));
        List<LocalCreditNoteFinanceChargeDetailForExpVoucher> localCreditNoteFinanceChargeDetailForExpVoucherList = new ArrayList<>();
        Integer AI = this.moduleService.findByModuleCode("AI").getModuleSeq(); // moduleSeq = 10;
        Integer AE = this.moduleService.findByModuleCode("AE").getModuleSeq(); // moduleSeq = 13;
        Integer OI = this.moduleService.findByModuleCode("OI").getModuleSeq(); // moduleSeq = 11;
        Integer OE = this.moduleService.findByModuleCode("OE").getModuleSeq(); // moduleSeq = 14;
        Integer WHARF = this.moduleService.findByModuleCode("WHARF").getModuleSeq(); // moduleSeq = 12;
        Integer CLEARANCE = this.moduleService.findByModuleCode("CLEARANCE").getModuleSeq(); // moduleSeq = 107;

        /*if (localCreditNoteHeaderList.size() > 0) {
            LocalCreditNoteHeader localCreditNoteHeader = Collections.max(localCreditNoteHeaderList, Comparator.comparing(i -> i.getCreatedDate()));
            List<FinancialChargeDetail> existFinancialChargeDetailList = new ArrayList<>();
            List<LcnChargeDetail> lcnChargeDetailList = localCreditNoteHeader.getLcnChargeDetailList();
            lcnChargeDetailList.forEach(i -> existFinancialChargeDetailList.add(i.getFinancialChargeDetail()));
            this.exchangeRateConversion.currencyConversionForExpenseVoucherFinChaDetail(existFinancialChargeDetailList, expenseVoucher.getExpenseVoucherSeq(), expenseVoucher.getBaseCurrencySeq(), expenseVoucher.getExchangeRateSeq());
            for (LcnChargeDetail lcnChargeDetail : lcnChargeDetailList) {
                LocalCreditNoteFinanceChargeDetailForExpVoucher localCreditNoteFinanceChargeDetailForExpVoucher = new LocalCreditNoteFinanceChargeDetailForExpVoucher();
                localCreditNoteFinanceChargeDetailForExpVoucher.setChargeSeq(lcnChargeDetail.getChargeSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setChargeName(lcnChargeDetail.getCharge().getChargeName());
                localCreditNoteFinanceChargeDetailForExpVoucher.setHouseBlSeq(lcnChargeDetail.getFinancialCharge().getHouseBlSeq());
                if (lcnChargeDetail.getFinancialCharge().getHouseBl() != null) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setHawbNo(lcnChargeDetail.getFinancialCharge().getHouseBl().getHawbNo());
                } else if (lcnChargeDetail.getFinancialCharge().getMasterBlSeq() != null) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setMasterAwbNo(lcnChargeDetail.getFinancialCharge().getMasterBlAux().getMasterAwbNo());
                } else if (lcnChargeDetail.getFinancialCharge().getInbondSeq() != null) {
                    if (lcnChargeDetail.getFinancialCharge().getInbond().getBlTypeSeq().equals(BlType.MBL.getBlTypeSeq())) {
                        localCreditNoteFinanceChargeDetailForExpVoucher.setMasterAwbNo(lcnChargeDetail.getFinancialCharge().getInbond().getBlNo());
                    } else {
                        localCreditNoteFinanceChargeDetailForExpVoucher.setHawbNo(lcnChargeDetail.getFinancialCharge().getInbond().getBlNo());
                    }
                }
                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrencySeq(lcnChargeDetail.getCurrencySeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrencyCode(lcnChargeDetail.getCurrency().getCurrencyCode());
                localCreditNoteFinanceChargeDetailForExpVoucher.setInvoicedAmount(lcnChargeDetail.getCurrentAmount());
                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrentAmount(lcnChargeDetail.getFinancialChargeDetail().getConvertedAmount());
                localCreditNoteFinanceChargeDetailForExpVoucher.setFinancialChargeSeq(lcnChargeDetail.getFinancialChargeSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setFinancialChargeDetailSeq(lcnChargeDetail.getFinancialChargeDetailSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setExpenseVouFinChrMapSeq(lcnChargeDetail.getExpenseVouFinChrMapSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setExpenseVoucherSeq(localCreditNoteHeader.getExpenseVoucherSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setLcnChargeDetailSeq(lcnChargeDetail.getLcnChargeDetailSeq());

                LcnTaxRateMapping lcnTaxRateMapping = this.lcnTaxRateMappingService.findByLocalCreditNoteHeaderSeqAndFinancialChargeDetailSeq(localCreditNoteHeader.getLocalCreditNoteHeaderSeq(), lcnChargeDetail.getFinancialChargeDetailSeq());
                if (lcnTaxRateMapping != null) {
                    ExpenseVouTaxRateMapping expenseVouTaxRateMapping = new ExpenseVouTaxRateMapping();
                    List<ExpenseVouTaxRateMapping> expenseVouTaxRateMappingList = new ArrayList<>();
                    if (lcnTaxRateMapping.getTaxRegistrationVatSeq() != null || lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq() != null) {
                        expenseVouTaxRateMapping.setTaxRegistrationVatSeq(lcnTaxRateMapping.getTaxRegistrationVatSeq());
                        expenseVouTaxRateMapping.setTaxRegistrationOtherTaxSeq(lcnTaxRateMapping.getTaxRegistrationOtherTaxSeq());
                        expenseVouTaxRateMappingList.add(expenseVouTaxRateMapping);
                        localCreditNoteFinanceChargeDetailForExpVoucher.setExpenseVouTaxRateMappingList(expenseVouTaxRateMappingList);
                    }
                }
                localCreditNoteFinanceChargeDetailForExpVoucherList.add(localCreditNoteFinanceChargeDetailForExpVoucher);
            }

            List<FinancialChargeDetail> financialChargeDetailList = null;
            if (expenseVoucher.getModuleSeq().equals(AI) || expenseVoucher.getModuleSeq().equals(OI) || expenseVoucher.getModuleSeq().equals(CLEARANCE)) {
                financialChargeDetailList = financialCharge.getFinancialChargeDetails().stream().filter(i -> i.getLiStatus() != 3
                        && i.getEvStatus() != 3 && i.getIntInvStatus() != 3 && i.getFinanceIncurredTypeSeq().equals(FinanceIncurredAtType.DESTINATION.getIncurredAtTypeSeq())
                        && i.getFinanceChargeTypeSeq().equals(FinanceChargeType.COST.getChargeTypeSeq())).collect(Collectors.toList());

            } else if (expenseVoucher.getModuleSeq().equals(AE) || expenseVoucher.getModuleSeq().equals(OE) || expenseVoucher.getModuleSeq().equals(WHARF)) {
                financialChargeDetailList = financialCharge.getFinancialChargeDetails().stream().filter(i -> i.getLiStatus() != 3
                        && i.getEvStatus() != 3 && i.getIntInvStatus() != 3 && i.getFinanceIncurredTypeSeq().equals(FinanceIncurredAtType.ORIGIN.getIncurredAtTypeSeq())
                        && i.getFinanceChargeTypeSeq().equals(FinanceChargeType.COST.getChargeTypeSeq())).collect(Collectors.toList());
            }
            this.exchangeRateConversion.currencyConversionForExpenseVoucherFinChaDetail(financialChargeDetailList, expenseVoucher.getExpenseVoucherSeq(), expenseVoucher.getBaseCurrencySeq(), expenseVoucher.getExchangeRateSeq());
            this.setLocalCreditNoteFinancialChargeDetailForExpenseVoucher(localCreditNoteFinanceChargeDetailForExpVoucherList, financialChargeDetailList, financialCharge, expenseVoucher);
        } else {
            List<ExpenseVouFinChargeMapping> expenseVouFinChargeMappingList = this.expenseVouFinChargeMappingService.findByExpenseVoucherSeqAndStatusNotIn(expenseVoucherSeq, MasterDataStatus.DELETED.getStatusSeq());
            //currency conversion
            this.exchangeRateConversion.currencyConversionForExpenseVoucher(expenseVouFinChargeMappingList, expenseVoucher.getExchangeRateSeq());
            for (ExpenseVouFinChargeMapping expenseVouFinChargeMapping : expenseVouFinChargeMappingList) {
                LocalCreditNoteFinanceChargeDetailForExpVoucher localCreditNoteFinanceChargeDetailForExpVoucher = new LocalCreditNoteFinanceChargeDetailForExpVoucher();
                localCreditNoteFinanceChargeDetailForExpVoucher.setChargeSeq(expenseVouFinChargeMapping.getChargeSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setChargeName(expenseVouFinChargeMapping.getCharge().getChargeName());
                localCreditNoteFinanceChargeDetailForExpVoucher.setHouseBlSeq(expenseVouFinChargeMapping.getFinancialCharge().getHouseBlSeq());
                if (expenseVouFinChargeMapping.getFinancialCharge().getHouseBl() != null) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setHawbNo(expenseVouFinChargeMapping.getFinancialCharge().getHouseBl().getHawbNo());
                } else if (expenseVouFinChargeMapping.getFinancialCharge().getMasterBlAux() != null) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setMasterAwbNo(expenseVouFinChargeMapping.getFinancialCharge().getMasterBlAux().getMasterAwbNo());
                } else if (expenseVouFinChargeMapping.getFinancialCharge().getInbondSeq() != null) {
                    if (expenseVouFinChargeMapping.getFinancialCharge().getInbond().getBlTypeSeq().equals(BlType.MBL.getBlTypeSeq())) {
                        localCreditNoteFinanceChargeDetailForExpVoucher.setMasterAwbNo(expenseVouFinChargeMapping.getFinancialCharge().getInbond().getBlNo());
                    } else {
                        localCreditNoteFinanceChargeDetailForExpVoucher.setHawbNo(expenseVouFinChargeMapping.getFinancialCharge().getInbond().getBlNo());
                    }
                }
                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrencySeq(expenseVouFinChargeMapping.getExpenseVoucher().getBaseCurrencySeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrencyCode(expenseVouFinChargeMapping.getExpenseVoucher().getCurrency().getCurrencyCode());
                localCreditNoteFinanceChargeDetailForExpVoucher.setInvoicedAmount(expenseVouFinChargeMapping.getAmount());
                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrentAmount(expenseVouFinChargeMapping.getFinancialChargeDetail().getConvertedAmount());
                localCreditNoteFinanceChargeDetailForExpVoucher.setFinancialChargeSeq(expenseVouFinChargeMapping.getFinancialChargeSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setFinancialChargeDetailSeq(expenseVouFinChargeMapping.getFinancialChargeDetailSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setCheckedStatus(expenseVouFinChargeMapping.getFinancialChargeDetail().getCheckedStatus());
                localCreditNoteFinanceChargeDetailForExpVoucher.setExpenseVouFinChrMapSeq(expenseVouFinChargeMapping.getExpenseVouFinChrMapSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setExpenseVoucherSeq(expenseVouFinChargeMapping.getExpenseVoucherSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setCheckedStatus(expenseVouFinChargeMapping.getFinancialChargeDetail().getCheckedStatus());

                List<ExpenseVouTaxRateMapping> expenseVouTaxRateMappingList = this.expenseVouTaxRateMappingService.findByExpenseVoucherSeqAndFinancialChargeDetailSeq(expenseVouFinChargeMapping.getExpenseVoucherSeq(), expenseVouFinChargeMapping.getFinancialChargeDetailSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setExpenseVouTaxRateMappingList(expenseVouTaxRateMappingList);
                localCreditNoteFinanceChargeDetailForExpVoucherList.add(localCreditNoteFinanceChargeDetailForExpVoucher);
            }

            this.exchangeRateConversion.currencyConversionForExpenseVoucherFinChaDetail(financialCharge.getFinancialChargeDetails(), expenseVoucher.getExpenseVoucherSeq(), expenseVoucher.getBaseCurrencySeq(), expenseVoucher.getExchangeRateSeq());
            List<FinancialChargeDetail> financialChargeDetailList = null;
            if (expenseVoucher.getModuleSeq().equals(AI) || expenseVoucher.getModuleSeq().equals(OI) || expenseVoucher.getModuleSeq().equals(CLEARANCE)) {
                financialChargeDetailList = financialCharge.getFinancialChargeDetails().stream().filter(i -> i.getLiStatus() != 3
                        && i.getEvStatus() != 3 && i.getIntInvStatus() != 3 && i.getFinanceIncurredTypeSeq().equals(FinanceIncurredAtType.DESTINATION.getIncurredAtTypeSeq())
                        && i.getFinanceChargeTypeSeq().equals(FinanceChargeType.COST.getChargeTypeSeq())).collect(Collectors.toList());

            } else if (expenseVoucher.getModuleSeq().equals(AE) || expenseVoucher.getModuleSeq().equals(OE) || expenseVoucher.getModuleSeq().equals(WHARF)) {
                financialChargeDetailList = financialCharge.getFinancialChargeDetails().stream().filter(i -> i.getLiStatus() != 3
                        && i.getEvStatus() != 3 && i.getIntInvStatus() != 3 && i.getFinanceIncurredTypeSeq().equals(FinanceIncurredAtType.ORIGIN.getIncurredAtTypeSeq())
                        && i.getFinanceChargeTypeSeq().equals(FinanceChargeType.COST.getChargeTypeSeq())).collect(Collectors.toList());
            }
            localCreditNoteFinanceChargeDetailForExpVoucherList = this.setLocalCreditNoteFinancialChargeDetailForExpenseVoucher(localCreditNoteFinanceChargeDetailForExpVoucherList, financialChargeDetailList, financialCharge, expenseVoucher);
        }*/
        return localCreditNoteFinanceChargeDetailForExpVoucherList;
    }

    private List<LocalCreditNoteFinancialChargeDetail> setLocalCreditNoteFinancialChargeDetailForLocalInvoice(List<LocalCreditNoteFinancialChargeDetail> localCreditNoteFinancialChargeDetails,
                                                                                                              List<FinancialChargeDetail> financialChargeDetailList,
                                                                                                              FinancialCharge financialCharge,
                                                                                                              LocalInvoice localInvoice) {
        try {
            List<LocalCreditNoteFinancialChargeDetail> localCreditNoteFinancialChargeDetailList = new ArrayList<>();
            for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
                LocalCreditNoteFinancialChargeDetail localCreditNoteFinancialChargeDetail = new LocalCreditNoteFinancialChargeDetail();
                localCreditNoteFinancialChargeDetail.setChargeSeq(financialChargeDetail.getChargeSeq());
                localCreditNoteFinancialChargeDetail.setChargeName(financialChargeDetail.getCharge().getChargeName());
                localCreditNoteFinancialChargeDetail.setTransportBookingSeq(financialCharge.getReferenceSeq());
                localCreditNoteFinancialChargeDetail.setCurrencySeq(localInvoice.getCurrencySeq());
                if (financialChargeDetail.getCheckedStatus() != null && financialChargeDetail.getCheckedStatus().equals("disabled")) {
                    localCreditNoteFinancialChargeDetail.setCurrencyCode(financialChargeDetail.getCurrency().getCurrencyCode());
                } else {
                    localCreditNoteFinancialChargeDetail.setCurrencyCode(localInvoice.getCurrency().getCurrencyCode());
                }
                localCreditNoteFinancialChargeDetail.setInvoicedAmount(0.00);
                localCreditNoteFinancialChargeDetail.setCurrentAmount(financialChargeDetail.getAmount());
                localCreditNoteFinancialChargeDetail.setFinancialChargeSeq(financialChargeDetail.getFinancialChargeSeq());
                localCreditNoteFinancialChargeDetail.setFinancialChargeDetailSeq(financialChargeDetail.getFinancialChargeDetailSeq());
                localCreditNoteFinancialChargeDetail.setLocalInvoiceFinChrMapSeq(null);
                localCreditNoteFinancialChargeDetail.setCheckedStatus(financialChargeDetail.getCheckedStatus());
                localCreditNoteFinancialChargeDetailList.add(localCreditNoteFinancialChargeDetail);
            }
            localCreditNoteFinancialChargeDetails.addAll(localCreditNoteFinancialChargeDetailList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localCreditNoteFinancialChargeDetails;
    }

    private List<LocalCreditNoteFinanceChargeDetailForExpVoucher> setLocalCreditNoteFinancialChargeDetailForExpenseVoucher(List<LocalCreditNoteFinanceChargeDetailForExpVoucher> localCreditNoteFinanceChargeDetailForExpVouchers,
                                                                                                                           List<FinancialChargeDetail> financialChargeDetailList,
                                                                                                                           FinancialCharge financialCharge,
                                                                                                                           ExpenseVoucher expenseVoucher) {
        try {
            List<LocalCreditNoteFinanceChargeDetailForExpVoucher> localCreditNoteFinanceChargeDetailForExpVoucherList = new ArrayList<>();
            for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
                LocalCreditNoteFinanceChargeDetailForExpVoucher localCreditNoteFinanceChargeDetailForExpVoucher = new LocalCreditNoteFinanceChargeDetailForExpVoucher();
                localCreditNoteFinanceChargeDetailForExpVoucher.setChargeSeq(financialChargeDetail.getChargeSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setChargeName(financialChargeDetail.getCharge().getChargeName());
                localCreditNoteFinanceChargeDetailForExpVoucher.setTransportBookingSeq(financialCharge.getReferenceSeq());

                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrencySeq(expenseVoucher.getCurrencySeq());
                if (financialChargeDetail.getCheckedStatus() != null && financialChargeDetail.getCheckedStatus().equals("disabled")) {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setCurrencyCode(financialChargeDetail.getCurrency().getCurrencyCode());
                } else {
                    localCreditNoteFinanceChargeDetailForExpVoucher.setCurrencyCode(expenseVoucher.getCurrency().getCurrencyCode());
                }
                localCreditNoteFinanceChargeDetailForExpVoucher.setInvoicedAmount(0.00);
                localCreditNoteFinanceChargeDetailForExpVoucher.setCurrentAmount(financialChargeDetail.getAmount());
                localCreditNoteFinanceChargeDetailForExpVoucher.setFinancialChargeSeq(financialChargeDetail.getFinancialChargeSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setFinancialChargeDetailSeq(financialChargeDetail.getFinancialChargeDetailSeq());
                localCreditNoteFinanceChargeDetailForExpVoucher.setExpenseVouFinChrMapSeq(null);
                localCreditNoteFinanceChargeDetailForExpVoucher.setCheckedStatus(financialChargeDetail.getCheckedStatus());
                localCreditNoteFinanceChargeDetailForExpVoucherList.add(localCreditNoteFinanceChargeDetailForExpVoucher);
            }
            localCreditNoteFinanceChargeDetailForExpVouchers.addAll(localCreditNoteFinanceChargeDetailForExpVoucherList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localCreditNoteFinanceChargeDetailForExpVouchers;
    }

    private LocalCreditNoteSummaryDetail setLocalCreditNoteSummaryDetail(LocalCreditNoteHeader localCreditNoteHeader,
                                                                         LocalCreditNoteSummaryDetail localCreditNoteSummaryDetail) {
        Double totalCurrentAmount = 0.0;
        Double totalInvoicedAmount = 0.0, totalInvoicedNtbAmount = 0.0, totalInvoicedVatAmount = 0.0;
        Double finalTotalInvoiceAmount = 0.0, finalTotalCurrentAmount = 0.0, balanceToBeCreditedAmount = 0.0;

        try {
            List<LocalCreditNoteChargeDetail> lcnChargeDetailList = localCreditNoteHeader.getLocalCreditNoteChargeDetailList();
            totalCurrentAmount = lcnChargeDetailList.stream().mapToDouble(f -> f.getFinancialChargeDetail().getAmount()).sum();
            totalInvoicedAmount = lcnChargeDetailList.stream().mapToDouble(LocalCreditNoteChargeDetail::getCurrentAmount).sum();
            totalInvoicedNtbAmount = this.convertValueIntoTwoDecimal(localCreditNoteHeader.getTotalCurrentNbtAmount());
            totalInvoicedVatAmount = this.convertValueIntoTwoDecimal(localCreditNoteHeader.getTotalCurrentVatAmount());

            localCreditNoteSummaryDetail.setTotalCurrentNbtAmount(totalInvoicedNtbAmount);
            localCreditNoteSummaryDetail.setTotalCurrentVatAmount(totalInvoicedVatAmount);
            localCreditNoteSummaryDetail.setTotalInvoicedNbtAmount(localCreditNoteHeader.getTotalCurrentNbtAmount());
            localCreditNoteSummaryDetail.setTotalInvoicedVatAmount(localCreditNoteHeader.getTotalCurrentVatAmount());

            finalTotalInvoiceAmount = totalInvoicedAmount + totalInvoicedNtbAmount + totalInvoicedVatAmount;
            finalTotalCurrentAmount = totalCurrentAmount + totalInvoicedNtbAmount + totalInvoicedVatAmount;
            balanceToBeCreditedAmount = finalTotalInvoiceAmount - finalTotalCurrentAmount;

            localCreditNoteSummaryDetail.setTotalCurrentAmount(totalCurrentAmount);
            localCreditNoteSummaryDetail.setTotalInvoicedAmount(totalInvoicedAmount);
            localCreditNoteSummaryDetail.setFinalTotalInvoiceAmount(finalTotalInvoiceAmount);
            localCreditNoteSummaryDetail.setFinalTotalCurrentAmount(finalTotalCurrentAmount);
            localCreditNoteSummaryDetail.setBalanceToBeCreditedAmount(Math.abs(balanceToBeCreditedAmount));
            if (balanceToBeCreditedAmount > 0) {
                localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.CREDIT.getEntryTypeSeq());
            } else {
                localCreditNoteSummaryDetail.setEntryTypeSeq(EntryType.DEBIT.getEntryTypeSeq());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return localCreditNoteSummaryDetail;
    }

    private Double convertValueIntoTwoDecimal(Double value) {
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        return Double.parseDouble(decimalFormat.format(value));
    }
}
