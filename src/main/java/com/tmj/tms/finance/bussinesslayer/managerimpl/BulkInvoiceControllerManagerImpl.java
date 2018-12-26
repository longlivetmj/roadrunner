package com.tmj.tms.finance.bussinesslayer.managerimpl;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.datalayer.service.ReportUploadService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.BulkInvoiceControllerManager;
import com.tmj.tms.finance.bussinesslayer.manager.LocalInvoiceControllerManager;
import com.tmj.tms.finance.datalayer.modal.*;
import com.tmj.tms.finance.datalayer.modal.auxiliary.BulkInvoiceSearchAux;
import com.tmj.tms.finance.datalayer.modal.auxiliary.SummedCharge;
import com.tmj.tms.finance.datalayer.service.BulkInvoiceService;
import com.tmj.tms.finance.datalayer.service.ExchangeRateService;
import com.tmj.tms.finance.datalayer.service.FinancialChargeDetailService;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.finance.utility.*;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.master.datalayer.service.*;
import com.tmj.tms.master.utility.AddressBookUtils;
import com.tmj.tms.transport.datalayer.modal.QTransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingFeedback;
import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import com.tmj.tms.transport.datalayer.service.TransportBookingFeedbackService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.utility.BooleanBuilderDuplicateRemover;
import com.tmj.tms.transport.utility.ViaLocationFormatter;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.NumberFormatter;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class BulkInvoiceControllerManagerImpl implements BulkInvoiceControllerManager {

    private final LocalInvoiceControllerManager localInvoiceControllerManager;
    private final ModuleService moduleService;
    private final TransportBookingStatusService transportBookingStatusService;
    private final BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover;
    private final TransportBookingService transportBookingService;
    private final FinanceDocumentCalculator financeDocumentCalculator;
    private final ViaLocationFormatter viaLocationFormatter;
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateConversion exchangeRateConversion;
    private final FinancialChargeDetailService financialChargeDetailService;
    private final ChargeService chargeService;
    private final UnitService unitService;
    private final CurrencyService currencyService;
    private final TaxRegistrationService taxRegistrationService;
    private final HttpSession httpSession;
    private final StakeholderService stakeholderService;
    private final FinancialNumberGenerator financialNumberGenerator;
    private final BulkInvoiceService bulkInvoiceService;
    private final FinancialChargeService financialChargeService;
    private final ReportUploadService reportUploadService;
    private final TransportBookingFeedbackService transportBookingFeedbackService;

    @Autowired
    public BulkInvoiceControllerManagerImpl(LocalInvoiceControllerManager localInvoiceControllerManager,
                                            ModuleService moduleService,
                                            TransportBookingStatusService transportBookingStatusService,
                                            BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover,
                                            TransportBookingService transportBookingService,
                                            FinanceDocumentCalculator financeDocumentCalculator,
                                            ViaLocationFormatter viaLocationFormatter,
                                            ExchangeRateService exchangeRateService,
                                            ExchangeRateConversion exchangeRateConversion,
                                            FinancialChargeDetailService financialChargeDetailService,
                                            ChargeService chargeService,
                                            UnitService unitService,
                                            CurrencyService currencyService,
                                            TaxRegistrationService taxRegistrationService,
                                            HttpSession httpSession,
                                            StakeholderService stakeholderService,
                                            FinancialNumberGenerator financialNumberGenerator,
                                            BulkInvoiceService bulkInvoiceService,
                                            FinancialChargeService financialChargeService,
                                            ReportUploadService reportUploadService,
                                            TransportBookingFeedbackService transportBookingFeedbackService) {
        this.localInvoiceControllerManager = localInvoiceControllerManager;
        this.moduleService = moduleService;
        this.transportBookingStatusService = transportBookingStatusService;
        this.booleanBuilderDuplicateRemover = booleanBuilderDuplicateRemover;
        this.transportBookingService = transportBookingService;
        this.financeDocumentCalculator = financeDocumentCalculator;
        this.viaLocationFormatter = viaLocationFormatter;
        this.exchangeRateService = exchangeRateService;
        this.exchangeRateConversion = exchangeRateConversion;
        this.financialChargeDetailService = financialChargeDetailService;
        this.chargeService = chargeService;
        this.unitService = unitService;
        this.currencyService = currencyService;
        this.taxRegistrationService = taxRegistrationService;
        this.httpSession = httpSession;
        this.stakeholderService = stakeholderService;
        this.financialNumberGenerator = financialNumberGenerator;
        this.bulkInvoiceService = bulkInvoiceService;
        this.financialChargeService = financialChargeService;
        this.reportUploadService = reportUploadService;
        this.transportBookingFeedbackService = transportBookingFeedbackService;
    }

    @Override
    public ResponseObject calculate(BulkInvoiceSearchAux bulkInvoiceSearchAux, HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        List<SummedCharge> summedChargeList = this.searchBulkInvoiceFinancialCharge(bulkInvoiceSearchAux);
        bulkInvoiceSearchAux.setCompanyProfileSeq(companyProfileSeq);
        BulkInvoice bulkInvoice = this.initBulkInvoice(summedChargeList, bulkInvoiceSearchAux, request);
        bulkInvoice = this.financeDocumentCalculator.bulkInvoiceCalculationReCheck(bulkInvoice);
        responseObject.setStatus(true);
        responseObject.setObject(bulkInvoice);
        return responseObject;
    }

    @Override
    public ResponseObject createBulkInvoice(BulkInvoiceSearchAux bulkInvoiceSearchAux, HttpServletRequest request) {
        ResponseObject responseObject = this.calculate(bulkInvoiceSearchAux, request);
        try {
            BulkInvoice bulkInvoice = (BulkInvoice) responseObject.getObject();
            if (responseObject.isStatus()) {
                Stakeholder stakeholder = this.stakeholderService.findOne(bulkInvoiceSearchAux.getStakeholderSeq());
                if (stakeholder.getStakeholderSvatTypeSeq() != null
                        && stakeholder.getStakeholderSvatTypeSeq().equals(0)
                        && stakeholder.getSuspendedTaxRegNo() != null
                        && !bulkInvoice.getFinalVatAmount().equals(0.0)) {
                    bulkInvoice.setVatOrSVat(InvoiceType.SVAT.getInvoiceType());
                } else {
                    bulkInvoice.setVatOrSVat(InvoiceType.VAT.getInvoiceType());
                }
                if (bulkInvoice.getBulkInvoiceChargeDetailList().size() > 0) {
                    String invoiceNo = this.financialNumberGenerator.generateLocalInvoiceNo(bulkInvoice.getCompanyProfileSeq(), bulkInvoice.getDepartmentSeq(), "BI");
                    bulkInvoice.setBulkInvoiceNo(invoiceNo);
                    bulkInvoice.setAmountInWord(ConvertNumberToWord.convertToCurrency(String.valueOf(NumberFormatter.round(bulkInvoice.getFinalTotalAmount(), 2))));
                    bulkInvoice.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    List<BulkInvoiceDetail> bulkInvoiceDetailList = new ArrayList<>();
                    Set<Integer> chargeSeqList = bulkInvoiceSearchAux.getSelectedChargeSeq();
                    List<TaxRegistration> taxRegistrationList = this.taxRegistrationService.findByCompanyProfileSeqAndStatus(bulkInvoice.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                    for (Integer financialChargeSeq : bulkInvoiceSearchAux.getFinancialChargeSeq()) {
                        List<FinancialChargeDetail> financialChargeDetailList = this.financialChargeDetailService.findByFinancialChargeSeqAndChargeTypeAndLiStatusAndStatusNot(
                                financialChargeSeq, ChargeType.REVENUE.getChargeType(), YesOrNo.Yes.getYesOrNoSeq(), MasterDataStatus.DELETED.getStatusSeq());
                        if (financialChargeDetailList != null && financialChargeDetailList.size() > 0) {
                            LocalInvoice localInvoice = this.initLocalInvoice(bulkInvoiceSearchAux, financialChargeSeq);
                            List<LocalInvoiceChargeDetail> localInvoiceChargeDetailList = new ArrayList<>();
                            for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
                                if (chargeSeqList.contains(financialChargeDetail.getChargeSeq())) {
                                    LocalInvoiceChargeDetail localInvoiceChargeDetail = new LocalInvoiceChargeDetail();
                                    localInvoiceChargeDetail.setFinancialChargeDetailSeq(financialChargeDetail.getFinancialChargeDetailSeq());
                                    List<LocalInvoiceChargeDetailTax> localInvoiceChargeDetailTaxList = new ArrayList<>();
                                    for (TaxRegistration taxRegistration : taxRegistrationList) {
                                        String taxSeqString = request.getParameter("taxRegistrationSeq_" + financialChargeDetail.getChargeSeq() + "_" + taxRegistration.getTaxRegistrationSeq());
                                        if (taxSeqString != null) {
                                            LocalInvoiceChargeDetailTax localInvoiceChargeDetailTax = new LocalInvoiceChargeDetailTax();
                                            localInvoiceChargeDetailTax.setTaxPercentage(taxRegistration.getTaxRate());
                                            localInvoiceChargeDetailTax.setTaxRegistrationSeq(taxRegistration.getTaxRegistrationSeq());
                                            localInvoiceChargeDetailTax.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                                            localInvoiceChargeDetailTaxList.add(localInvoiceChargeDetailTax);
                                        }
                                    }
                                    if (localInvoiceChargeDetailTaxList.size() > 0) {
                                        localInvoiceChargeDetail.setLocalInvoiceChargeDetailTaxList(localInvoiceChargeDetailTaxList);
                                    }
                                    localInvoiceChargeDetailList.add(localInvoiceChargeDetail);
                                }
                            }
                            localInvoice.setLocalInvoiceChargeDetailList(localInvoiceChargeDetailList);
                            localInvoice = (LocalInvoice) this.localInvoiceControllerManager.createLocalInvoice(localInvoice, request).getObject();
                            BulkInvoiceDetail bulkInvoiceDetail = new BulkInvoiceDetail();
                            bulkInvoiceDetail.setLocalInvoiceSeq(localInvoice.getLocalInvoiceSeq());
                            bulkInvoiceDetail.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                            bulkInvoiceDetailList.add(bulkInvoiceDetail);
                        }
                    }
                    bulkInvoice.setBulkInvoiceDetailList(bulkInvoiceDetailList);
                    bulkInvoice = this.bulkInvoiceService.save(bulkInvoice);
                    this.financialNumberGenerator.increaseLocalInvoiceNo(bulkInvoice.getCompanyProfileSeq(), bulkInvoice.getDepartmentSeq(), "BI");
                    responseObject = new ResponseObject("Bulk Invoice Saved Successfully", true);
                    responseObject.setObject(bulkInvoice);
                } else {
                    responseObject = new ResponseObject("Please Choose at least One Charge Line", false);
                }
            }
        } catch (NullPointerException e) {
            responseObject = new ResponseObject("Some Required Data are Missing", false);
        }
        return responseObject;
    }

    private LocalInvoice initLocalInvoice(BulkInvoiceSearchAux bulkInvoiceSearchAux, Integer financialChargeSeq) {
        FinancialCharge financialCharge = this.financialChargeService.findOne(financialChargeSeq);
        LocalInvoice localInvoice = new LocalInvoice();
        localInvoice.setModuleSeq(bulkInvoiceSearchAux.getModuleSeq());
        localInvoice.setCompanyProfileSeq(bulkInvoiceSearchAux.getCompanyProfileSeq());
        localInvoice.setDepartmentSeq(bulkInvoiceSearchAux.getDepartmentSeq());
        localInvoice.setCurrencySeq(bulkInvoiceSearchAux.getCurrencySeq());
        localInvoice.setExchangeRateSeq(bulkInvoiceSearchAux.getExchangeRateSeq());
        localInvoice.setStakeholderSeq(bulkInvoiceSearchAux.getStakeholderSeq());
        localInvoice.setTargetType(bulkInvoiceSearchAux.getTargetType());
        localInvoice.setFinancialChargeSeq(financialChargeSeq);
        localInvoice.setReferenceSeq(financialCharge.getReferenceSeq());
        return localInvoice;
    }

    @Override
    public List<ExchangeRate> searchExchangeRateBank(Integer moduleSeq, Integer exchangeRateSourceType, Integer sourceBankSeq) {
        return this.localInvoiceControllerManager.searchExchangeRateBank(moduleSeq, exchangeRateSourceType, sourceBankSeq);
    }

    @Override
    public List<ExchangeRateDetail> searchExchangeRate(Integer exchangeRateSeq) {
        return this.localInvoiceControllerManager.searchExchangeRate(exchangeRateSeq);
    }

    @Override
    public List<FinancialCharge> searchTransportBookingChargesForBulkInvoice(BulkInvoiceSearchAux bulkInvoiceSearchAux) {
        List<FinancialCharge> financialChargeList = new ArrayList<>();
        try {
            Module transport = this.moduleService.findByModuleCode("TRANSPORT");
            if (bulkInvoiceSearchAux.getModuleSeq().equals(transport.getModuleSeq())) {
                QTransportBooking transportBooking = QTransportBooking.transportBooking;
                BooleanBuilder builder = new BooleanBuilder();
                builder.and(transportBooking.customerSeq.eq(bulkInvoiceSearchAux.getCustomerSeq()));
                if (bulkInvoiceSearchAux.getShipperSeq() != null) {
                    builder.and(transportBooking.shipperSeq.eq(bulkInvoiceSearchAux.getShipperSeq()));
                }
                List<TransportBookingStatus> transportBookingStatusList = this.transportBookingStatusService.findByInvoiceable(YesOrNo.Yes.getYesOrNoSeq());
                List<Integer> bookingStatusSeqList = transportBookingStatusList.stream().map(TransportBookingStatus::getCurrentStatus).collect(Collectors.toList());
                builder.and(transportBooking.currentStatus.in(bookingStatusSeqList));
                builder = this.booleanBuilderDuplicateRemover.dateFilterType(builder, transportBooking,
                        bulkInvoiceSearchAux.getDateFilterType(),
                        bulkInvoiceSearchAux.getStartDate(), DateFormatter.getEndOfTheDay(bulkInvoiceSearchAux.getEndDate()));
                if (bulkInvoiceSearchAux.getVehicleSeq() != null) {
                    builder.and(transportBooking.transportBookingVehicleList.any().vehicleSeq.eq(bulkInvoiceSearchAux.getVehicleSeq()));
                }
                if (bulkInvoiceSearchAux.getFinalDestinationSeq() != null && bulkInvoiceSearchAux.getFinalDestinationSeq().size() > 0) {
                    builder.and(transportBooking.pickupLocationSeq.in(bulkInvoiceSearchAux.getFinalDestinationSeq()));
                }
                List<TransportBooking> transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder);
                for (TransportBooking booking : transportBookingList) {
                    FinancialCharge financialCharge = this.financeDocumentCalculator.getTransportBookingCharges(bulkInvoiceSearchAux.getModuleSeq(), bulkInvoiceSearchAux.getTargetType(), booking.getTransportBookingSeq());
                    financialCharge.getTransportBooking().setViaLocationString(this.viaLocationFormatter.getViaLocationAsString(booking.getTransportBookingViaLocationList()));
                    financialChargeList.add(financialCharge);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return financialChargeList;
    }

    @Override
    public List<Currency> getCurrency(Integer exchangeRateSeq) {
        return this.localInvoiceControllerManager.getCurrency(exchangeRateSeq);
    }

    @Override
    public Integer getBulkInvoiceReport(Integer bulkInvoiceSeq, Integer reportSeq, Integer pdf,
                                        Integer rtf, Integer xls, HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, Principal principal) {
        Integer reportUploadSeq = null;
        String moduleName = "finance";
        try {
            DateFormatter dateFormatter = new DateFormatter();
            BulkInvoice bulkInvoice = this.bulkInvoiceService.findOne(bulkInvoiceSeq);

            List<BulkInvoiceLocation> bulkInvoiceLocationList = bulkInvoice.getBulkInvoiceLocationList();

            String pickLocation = "";
            if (bulkInvoiceLocationList != null && bulkInvoiceLocationList.size() > 0) {
                pickLocation = bulkInvoiceLocationList.stream().map(value -> value.getFinalDestination().getDestination())
                        .collect(Collectors.joining(", "));
            }

            Currency currency = this.currencyService.findOne(bulkInvoice.getCurrencySeq());
            Map<String, Object> param = new HashMap<>();
            param.put("BULK_INVOICE_SEQ", bulkInvoiceSeq);
            param.put("MODULE_SEQ", bulkInvoice.getModuleSeq());
            param.put("CURRENCY", currency.getCurrencyCode());
            param.put("VEHICLE_NO", bulkInvoice.getVehicle().getVehicleNo());
            param.put("VEHICLE_TYPE", bulkInvoice.getVehicle().getVehicleVehicleTypeList().get(0).getVehicleType().getVehicleTypeName());
            param.put("FILTER_TYPE", bulkInvoice.getDateFilterTypeDescription());
            param.put("LOADING_LOCATION", pickLocation);

            param.put("START_DATE", dateFormatter.returnSortFormattedDate(bulkInvoice.getStartDate()));
            param.put("END_DATE", dateFormatter.returnSortFormattedDate(bulkInvoice.getEndDate()));
            param.put("INVOICE_NO", bulkInvoice.getBulkInvoiceNo());
            param.put("INVOICED_DATE", dateFormatter.returnLongFormattedDateTime(bulkInvoice.getCreatedDate()));
            String invoiceType = "BULK INVOICE";
            if (bulkInvoice.getFinalVatAmount() > 0 && bulkInvoice.getVatOrSVat().equals(InvoiceType.SVAT.getInvoiceType())) {
                invoiceType = "SUSPENDED TAX INVOICE";
            } else if (bulkInvoice.getFinalVatAmount() > 0 && bulkInvoice.getVatOrSVat().equals(InvoiceType.VAT.getInvoiceType()) && bulkInvoice.getStakeholder().getTaxRegNo() != null) {
                invoiceType = "TAX INVOICE";
            }
            param.put("INVOICE_TYPE", invoiceType);
            param.put("STAKEHOLDER_ADDRESS", bulkInvoice.getStakeholder().getStakeholderName() + "\n" + AddressBookUtils.addressBookForInvoice(bulkInvoice.getStakeholder().getAddressBook()));
            param.put("SHIPPER", bulkInvoice.getShipper().getStakeholderName());
            String otherTaxText = this.getOtherTaxText(bulkInvoice.getBulkInvoiceChargeDetailList());
            param.put("OTHER_TAX_TEXT", otherTaxText);
            String vatTaxText = this.financeDocumentCalculator.getVatTaxText(bulkInvoice.getFinalVatAmount(), bulkInvoice.getVatOrSVat(), bulkInvoice.getStakeholder());
            param.put("VAT_TAX_TEXT", vatTaxText);
            if (pdf != null && pdf == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        bulkInvoice.getBulkInvoiceNo(),
                        ".pdf",
                        "application/pdf",
                        httpServletRequest);
            }
            if (rtf != null && rtf == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        bulkInvoice.getBulkInvoiceNo(),
                        ".rtf",
                        "application/rtf",
                        httpServletRequest);
            }
            if (xls != null && xls == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        bulkInvoice.getBulkInvoiceNo(),
                        ".xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        httpServletRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reportUploadSeq;
    }

    private String getOtherTaxText(List<BulkInvoiceChargeDetail> bulkInvoiceChargeDetailList) {
        List<String> distinctOtherTax = new ArrayList<>();
        for (BulkInvoiceChargeDetail bulkInvoiceChargeDetail : bulkInvoiceChargeDetailList) {
            if (bulkInvoiceChargeDetail.getBulkInvoiceChargeDetailTaxList() != null) {
                for (BulkInvoiceChargeDetailTax bulkInvoiceChargeDetailTax : bulkInvoiceChargeDetail.getBulkInvoiceChargeDetailTaxList()) {
                    if (bulkInvoiceChargeDetailTax.getTaxTypeSeq().equals(2)) { //other taxes
                        distinctOtherTax.add(bulkInvoiceChargeDetailTax.getTaxType().getTaxTypeName());
                    }
                }
            }
        }
        return String.join(",", distinctOtherTax);
    }

    @Override
    public ResponseObject deleteBulkInvoice(Integer bulkInvoiceSeq) {
        ResponseObject responseObject;
        BulkInvoice bulkInvoice = this.bulkInvoiceService.findOne(bulkInvoiceSeq);
        bulkInvoice.setStatus(MasterDataStatus.DELETED.getStatusSeq());
        List<BulkInvoiceDetail> bulkInvoiceDetailList = bulkInvoice.getBulkInvoiceDetailList();
        if (bulkInvoiceDetailList != null && bulkInvoiceDetailList.size() > 0) {
            for (BulkInvoiceDetail bulkInvoiceDetail : bulkInvoiceDetailList) {
                this.localInvoiceControllerManager.deleteLocalInvoice(bulkInvoiceDetail.getLocalInvoiceSeq());
            }
            this.bulkInvoiceService.save(bulkInvoice);
            responseObject = new ResponseObject("Bulk Invoice Deleted Successfully", true);
            responseObject.setObject(bulkInvoice);
        } else {
            responseObject = new ResponseObject("Error Deleting", false);
            responseObject.setObject(bulkInvoice);
        }
        return responseObject;
    }

    @Override
    public List<BulkInvoice> searchBulkInvoice(BulkInvoiceSearchFromAux bulkInvoiceSearchAux, Principal principal) {
        List<BulkInvoice> bulkInvoiceList = null;
        try {
            QBulkInvoice bulkInvoice = QBulkInvoice.bulkInvoice;
            BooleanBuilder builder = new BooleanBuilder();
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            builder.and(bulkInvoice.companyProfileSeq.eq(companyProfileSeq));
            if (bulkInvoiceSearchAux.getBulkInvoiceNo() != null) {
                builder.and(bulkInvoice.bulkInvoiceNo.contains(bulkInvoiceSearchAux.getBulkInvoiceNo()));
            }
            if (bulkInvoiceSearchAux.getStartDate() != null && bulkInvoiceSearchAux.getEndDate() != null) {
                builder.and(bulkInvoice.createdDate.between(bulkInvoiceSearchAux.getStartDate(), DateFormatter.getEndOfTheDay(bulkInvoiceSearchAux.getEndDate())));
            }
            if (bulkInvoiceSearchAux.getStakeholderSeq() != null) {
                builder.and(bulkInvoice.stakeholderSeq.eq(bulkInvoiceSearchAux.getStakeholderSeq()));
            }
            if (bulkInvoiceSearchAux.getStatus() != null) {
                builder.and(bulkInvoice.status.eq(bulkInvoiceSearchAux.getStatus()));
            } else {
                String[] statusList = {"ROLE_finance@bulkInvoice_APPROVE", "ROLE_finance@bulkInvoice_VIEW-DELETE"};
                List<MasterDataStatus> allowedStatus = MasterDataStatus.getStatusListForTransaction(Arrays.asList(statusList));
                List<Integer> statusListSeq = allowedStatus.stream().map(MasterDataStatus::getStatusSeq).collect(Collectors.toList());
                builder.and(bulkInvoice.status.in(statusListSeq));
            }
            builder = this.booleanBuilderDuplicateRemover.financialDocumentSearch(builder, bulkInvoiceSearchAux.getModuleSeq(),
                    bulkInvoiceSearchAux.getDepartmentSeq(), companyProfileSeq, principal, null, null, bulkInvoice);
            bulkInvoiceList = (List<BulkInvoice>) bulkInvoiceService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bulkInvoiceList;
    }

    @Override
    public List<SummedCharge> searchBulkInvoiceFinancialCharge(BulkInvoiceSearchAux bulkInvoiceSearchAux) {
        List<SummedCharge> summedChargeList = new ArrayList<>();
        try {
            List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
            ExchangeRate dbExchangeRate = this.exchangeRateService.findOne(bulkInvoiceSearchAux.getExchangeRateSeq());
            Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
            if (bulkInvoiceSearchAux.getFinancialChargeSeq() != null && bulkInvoiceSearchAux.getFinancialChargeSeq().size() > 0) {
                if (bulkInvoiceSearchAux.getModuleSeq() != null) {
                    for (Integer financialChargeSeq : bulkInvoiceSearchAux.getFinancialChargeSeq()) {
                        if (bulkInvoiceSearchAux.getModuleSeq().equals(transport)) {
                            List<FinancialChargeDetail> temp = this.financialChargeDetailService.findByFinancialChargeSeqAndChargeTypeAndLiStatusAndStatusNot(financialChargeSeq, ChargeType.REVENUE.getChargeType(), YesOrNo.Yes.getYesOrNoSeq(), MasterDataStatus.DELETED.getStatusSeq());
                            if (temp != null && temp.size() > 0) {
                                financialChargeDetailList.addAll(temp);
                            }
                        }
                    }
                }
                if (bulkInvoiceSearchAux.getCurrencySeq() != null) {
                    this.exchangeRateConversion.dynamicConversion(financialChargeDetailList, dbExchangeRate.getExchangeRateSeq(), bulkInvoiceSearchAux.getCurrencySeq());
                } else {
                    this.exchangeRateConversion.dynamicConversion(financialChargeDetailList, dbExchangeRate.getExchangeRateSeq(), dbExchangeRate.getBaseCurrencySeq());
                }

                Map<ChargeSumGroup, List<FinancialChargeDetail>> chargeSeqWiseSum = financialChargeDetailList.stream()
                        .collect(groupingBy(financialChargeDetail -> new ChargeSumGroup(financialChargeDetail.getChargeSeq(), financialChargeDetail.getUnitSeq())));

                for (Map.Entry<ChargeSumGroup, List<FinancialChargeDetail>> chargeSumGroupListEntry : chargeSeqWiseSum.entrySet()) {
                    SummedCharge summedCharge = new SummedCharge();
                    ChargeSumGroup key = chargeSumGroupListEntry.getKey();
                    summedCharge.setCurrency(this.currencyService.findOne(bulkInvoiceSearchAux.getCurrencySeq()));
                    summedCharge.setCharge(this.chargeService.findOne(key.getChargeSeq()));
                    summedCharge.setChargeSeq(key.getChargeSeq());
                    summedCharge.setUnit(this.unitService.findOne(key.getUnitSeq()));
                    summedCharge.setUnitSeq(key.getUnitSeq());
                    summedCharge.setQuantity(chargeSumGroupListEntry.getValue().stream().mapToDouble(FinancialChargeDetail::getQuantity).sum());
                    summedCharge.setChargeValue(chargeSumGroupListEntry.getValue().stream().mapToDouble(FinancialChargeDetail::getTempAmount).sum());
                    summedCharge.setFinancialChargeDetailList(chargeSumGroupListEntry.getValue());
                    summedCharge.setCheckedStatus("checked");
                    summedChargeList.add(summedCharge);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summedChargeList;
    }

    @Override
    public List<TaxRegistration> getVatTaxList() {
        return this.localInvoiceControllerManager.getVatTaxList();
    }

    @Override
    public List<TaxRegistration> getOtherTaxList() {
        return this.localInvoiceControllerManager.getOtherTaxList();
    }

    @Override
    public ResponseObject changeChargeableKm(Integer transportBookingSeq, Double chargeableKm) {
        ResponseObject responseObject = new ResponseObject(true, "");
        TransportBookingFeedback transportBookingFeedback = this.transportBookingFeedbackService.findOne(transportBookingSeq);
        transportBookingFeedback.setChargeableKm(chargeableKm);
        this.transportBookingFeedbackService.save(transportBookingFeedback);
        responseObject.setStatus(true);
        responseObject.setMessage(String.valueOf(chargeableKm));
        return responseObject;
    }

    private BulkInvoice initBulkInvoice(List<SummedCharge> summedChargeList, BulkInvoiceSearchAux bulkInvoiceSearchAux, HttpServletRequest request) {
        BulkInvoice bulkInvoice = new BulkInvoice();
        bulkInvoice.setModuleSeq(bulkInvoiceSearchAux.getModuleSeq());
        bulkInvoice.setCompanyProfileSeq(bulkInvoiceSearchAux.getCompanyProfileSeq());
        bulkInvoice.setDepartmentSeq(bulkInvoiceSearchAux.getDepartmentSeq());
        bulkInvoice.setCurrencySeq(bulkInvoiceSearchAux.getCurrencySeq());
        bulkInvoice.setExchangeRateSeq(bulkInvoiceSearchAux.getExchangeRateSeq());
        bulkInvoice.setShipperSeq(bulkInvoiceSearchAux.getShipperSeq());
        bulkInvoice.setDateFilterType(bulkInvoiceSearchAux.getDateFilterType());
        bulkInvoice.setStakeholderSeq(bulkInvoiceSearchAux.getStakeholderSeq());
        bulkInvoice.setVehicleSeq(bulkInvoiceSearchAux.getVehicleSeq());
        bulkInvoice.setStartDate(bulkInvoiceSearchAux.getStartDate());
        bulkInvoice.setEndDate(bulkInvoiceSearchAux.getEndDate());
        bulkInvoice.setRemark(bulkInvoiceSearchAux.getRemark());
        bulkInvoice.setTargetType(bulkInvoiceSearchAux.getTargetType());

        List<TaxRegistration> taxRegistrationList = this.taxRegistrationService.findByCompanyProfileSeqAndStatus(bulkInvoiceSearchAux.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
        List<BulkInvoiceChargeDetail> bulkInvoiceChargeDetailList = new ArrayList<>();
        if (summedChargeList != null && summedChargeList.size() > 0) {
            for (SummedCharge summedCharge : summedChargeList) {
                if (bulkInvoiceSearchAux.getSelectedChargeSeq().contains(summedCharge.getChargeSeq())) {
                    BulkInvoiceChargeDetail bulkInvoiceChargeDetail = new BulkInvoiceChargeDetail();
                    bulkInvoiceChargeDetail.setChargeSeq(summedCharge.getCharge().getChargeSeq());
                    bulkInvoiceChargeDetail.setAmount(summedCharge.getChargeValue());
                    bulkInvoiceChargeDetail.setQuantity(summedCharge.getQuantity());
                    bulkInvoiceChargeDetail.setCurrencySeq(bulkInvoice.getCurrencySeq());
                    bulkInvoiceChargeDetail.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    bulkInvoiceChargeDetail.setUnitSeq(summedCharge.getUnitSeq());
                    List<BulkInvoiceChargeDetailTax> bulkInvoiceChargeDetailTaxList = new ArrayList<>();
                    for (TaxRegistration taxRegistration : taxRegistrationList) {
                        String taxSeqString = request.getParameter("taxRegistrationSeq_" + summedCharge.getChargeSeq() + "_" + taxRegistration.getTaxRegistrationSeq());
                        if (taxSeqString != null) {
                            BulkInvoiceChargeDetailTax bulkInvoiceChargeDetailTax = new BulkInvoiceChargeDetailTax();
                            bulkInvoiceChargeDetailTax.setTaxPercentage(taxRegistration.getTaxRate());
                            bulkInvoiceChargeDetailTax.setTaxRegistrationSeq(taxRegistration.getTaxRegistrationSeq());
                            bulkInvoiceChargeDetailTax.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                            bulkInvoiceChargeDetailTaxList.add(bulkInvoiceChargeDetailTax);
                        }
                    }
                    if (bulkInvoiceChargeDetailTaxList.size() > 0) {
                        bulkInvoiceChargeDetail.setBulkInvoiceChargeDetailTaxList(bulkInvoiceChargeDetailTaxList);
                    }
                    bulkInvoiceChargeDetailList.add(bulkInvoiceChargeDetail);
                }
            }
        }

        if (bulkInvoiceSearchAux.getFinalDestinationSeq() != null) {
            List<BulkInvoiceLocation> bulkInvoiceLocationList = new ArrayList<>();
            for (Integer finalDestinationSeq : bulkInvoiceSearchAux.getFinalDestinationSeq()) {
                BulkInvoiceLocation bulkInvoiceLocation = new BulkInvoiceLocation();
                bulkInvoiceLocation.setFinalDestinationSeq(finalDestinationSeq);
                bulkInvoiceLocationList.add(bulkInvoiceLocation);
            }
            bulkInvoice.setBulkInvoiceLocationList(bulkInvoiceLocationList);
        }
        bulkInvoice.setBulkInvoiceChargeDetailList(bulkInvoiceChargeDetailList);
        return bulkInvoice;
    }
}
