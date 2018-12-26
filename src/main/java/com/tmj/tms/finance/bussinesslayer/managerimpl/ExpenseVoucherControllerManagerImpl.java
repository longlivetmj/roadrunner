package com.tmj.tms.finance.bussinesslayer.managerimpl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.datalayer.service.ReportUploadService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.ExpenseVoucherControllerManager;
import com.tmj.tms.finance.datalayer.modal.*;
import com.tmj.tms.finance.datalayer.service.*;
import com.tmj.tms.finance.utility.*;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.TaxRegistrationService;
import com.tmj.tms.master.utility.AddressBookUtils;
import com.tmj.tms.transport.datalayer.modal.Job;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.service.JobService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.utility.BooleanBuilderDuplicateRemover;
import com.tmj.tms.transport.utility.ViaLocationFormatter;
import com.tmj.tms.utility.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;

@Service
public class ExpenseVoucherControllerManagerImpl implements ExpenseVoucherControllerManager {

    private final HttpSession httpSession;
    private final FinancialChargeService financialChargeService;
    private final ExchangeRateDetailService exchangeRateDetailService;
    private final ExchangeRateService exchangeRateService;
    private final CurrencyService currencyService;
    private final TransportBookingService transportBookingService;
    private final TaxRegistrationService taxRegistrationService;
    private final ModuleService moduleService;
    private final FinancialChargeDetailService financialChargeDetailService;
    private final ExchangeRateConversion exchangeRateConversion;
    private final FinanceDocumentCalculator financeDocumentCalculator;
    private final StakeholderService stakeholderService;
    private final FinancialNumberGenerator financialNumberGenerator;
    private final ExpenseVoucherService expenseVoucherService;
    private final ReportUploadService reportUploadService;
    private final ViaLocationFormatter viaLocationFormatter;
    private final BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover;
    private final JobService jobService;

    @PersistenceContext
    private EntityManager entityManager;

    public ExpenseVoucherControllerManagerImpl(HttpSession httpSession,
                                               FinancialChargeService financialChargeService,
                                               ExchangeRateDetailService exchangeRateDetailService,
                                               ExchangeRateService exchangeRateService,
                                               CurrencyService currencyService,
                                               TransportBookingService transportBookingService,
                                               TaxRegistrationService taxRegistrationService,
                                               ModuleService moduleService,
                                               FinancialChargeDetailService financialChargeDetailService,
                                               ExchangeRateConversion exchangeRateConversion,
                                               FinanceDocumentCalculator financeDocumentCalculator,
                                               StakeholderService stakeholderService,
                                               FinancialNumberGenerator financialNumberGenerator,
                                               ExpenseVoucherService expenseVoucherService,
                                               ReportUploadService reportUploadService,
                                               ViaLocationFormatter viaLocationFormatter,
                                               BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover,
                                               JobService jobService) {
        this.httpSession = httpSession;
        this.financialChargeService = financialChargeService;
        this.exchangeRateDetailService = exchangeRateDetailService;
        this.exchangeRateService = exchangeRateService;
        this.currencyService = currencyService;
        this.transportBookingService = transportBookingService;
        this.taxRegistrationService = taxRegistrationService;
        this.moduleService = moduleService;
        this.financialChargeDetailService = financialChargeDetailService;
        this.exchangeRateConversion = exchangeRateConversion;
        this.financeDocumentCalculator = financeDocumentCalculator;
        this.stakeholderService = stakeholderService;
        this.financialNumberGenerator = financialNumberGenerator;
        this.expenseVoucherService = expenseVoucherService;
        this.reportUploadService = reportUploadService;
        this.viaLocationFormatter = viaLocationFormatter;
        this.booleanBuilderDuplicateRemover = booleanBuilderDuplicateRemover;
        this.jobService = jobService;
    }

    @Override
    public ResponseObject calculate(ExpenseVoucher expenseVoucher, HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject();
        Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        expenseVoucher.setCompanyProfileSeq(companyProfileSeq);
        if (expenseVoucher.getFinancialChargeSeq() != null) {
            FinancialCharge financialCharge = this.financialChargeService.findOne(expenseVoucher.getFinancialChargeSeq());
            if (financialCharge.getModuleSeq().equals(transport) && financialCharge.getReferenceSeq() != null && financialCharge.getTargetType().equals(TargetType.TRANSPORT_JOB.getTargetType())
                    && financialCharge.getReferenceType().equals(ReferenceType.TRANSPORT_BOOKING.getReferenceType())) {
                expenseVoucher = this.initExpenseVoucher(expenseVoucher, request);
                expenseVoucher = this.financeDocumentCalculator.expenseVoucherCalculationReCheck(expenseVoucher);
                responseObject.setStatus(true);
                responseObject.setObject(expenseVoucher);
            }
        }
        return responseObject;
    }

    private ExpenseVoucher initExpenseVoucher(ExpenseVoucher expenseVoucher, HttpServletRequest request) {
        List<TaxRegistration> taxRegistrationList = this.taxRegistrationService.findByCompanyProfileSeqAndStatus(expenseVoucher.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
        if (expenseVoucher.getExpenseVoucherChargeDetailList() != null) {
            for (ExpenseVoucherChargeDetail expenseVoucherChargeDetail : expenseVoucher.getExpenseVoucherChargeDetailList()) {
                FinancialChargeDetail financialChargeDetail = this.financialChargeDetailService.findOne(expenseVoucherChargeDetail.getFinancialChargeDetailSeq());
                financialChargeDetail = this.exchangeRateConversion.dynamicConversion(financialChargeDetail, expenseVoucher.getExchangeRateSeq(), expenseVoucher.getCurrencySeq());
                expenseVoucherChargeDetail.setAmount(financialChargeDetail.getTempAmount());
                expenseVoucherChargeDetail.setChargeSeq(financialChargeDetail.getChargeSeq());
                expenseVoucherChargeDetail.setChargeValue(financialChargeDetail.getChargeValue());
                expenseVoucherChargeDetail.setQuantity(financialChargeDetail.getQuantity());
                expenseVoucherChargeDetail.setUnitSeq(financialChargeDetail.getUnitSeq());
                expenseVoucherChargeDetail.setCurrencySeq(expenseVoucher.getCurrencySeq());
                expenseVoucherChargeDetail.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                List<ExpenseVoucherChargeDetailTax> expenseVoucherChargeDetailTaxList = new ArrayList<>();
                for (TaxRegistration taxRegistration : taxRegistrationList) {
                    String taxSeqString = request.getParameter("taxRegistrationSeq_" + financialChargeDetail.getFinancialChargeDetailSeq() + "_" + taxRegistration.getTaxRegistrationSeq());
                    if (taxSeqString != null) {
                        ExpenseVoucherChargeDetailTax expenseVoucherChargeDetailTax = new ExpenseVoucherChargeDetailTax();
                        expenseVoucherChargeDetailTax.setTaxPercentage(taxRegistration.getTaxRate());
                        expenseVoucherChargeDetailTax.setTaxRegistrationSeq(taxRegistration.getTaxRegistrationSeq());
                        expenseVoucherChargeDetailTax.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                        expenseVoucherChargeDetailTaxList.add(expenseVoucherChargeDetailTax);
                    }
                }
                if (expenseVoucherChargeDetailTaxList.size() > 0) {
                    expenseVoucherChargeDetail.setExpenseVoucherChargeDetailTaxList(expenseVoucherChargeDetailTaxList);
                }
            }
        }
        return expenseVoucher;
    }

    @Override
    public ResponseObject createExpenseVoucher(ExpenseVoucher expenseVoucher, HttpServletRequest request) {
        ResponseObject responseObject = this.calculate(expenseVoucher, request);
        try {
            if (responseObject.isStatus()) {
                TransportBooking transportBooking = this.transportBookingService.findOne(expenseVoucher.getReferenceSeq());
                expenseVoucher = (ExpenseVoucher) responseObject.getObject();
                expenseVoucher.setDepartmentSeq(transportBooking.getDepartmentSeq());
                List<ExpenseVoucherExcRateMapping> excRateMappingList = new ArrayList<>();
                ExchangeRate exchangeRate = this.exchangeRateService.findOne(expenseVoucher.getExchangeRateSeq());
                for (ExchangeRateDetail exchangeRateDetail : exchangeRate.getExchangeRateDetails()) {
                    ExpenseVoucherExcRateMapping expenseVoucherExcRateMapping = new ExpenseVoucherExcRateMapping();
                    expenseVoucherExcRateMapping.setExchangeRateDetailSeq(exchangeRateDetail.getExchangeRateDetailSeq());
                    expenseVoucherExcRateMapping.setRate(exchangeRateDetail.getRate());
                    expenseVoucherExcRateMapping.setCurrencySeq(exchangeRateDetail.getCurrencySeq());
                    expenseVoucherExcRateMapping.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    excRateMappingList.add(expenseVoucherExcRateMapping);
                }
                expenseVoucher.setExpenseVoucherExcRateMappings(excRateMappingList);
                Stakeholder stakeholder = this.stakeholderService.findOne(expenseVoucher.getStakeholderSeq());
                if (stakeholder.getStakeholderSvatTypeSeq() != null
                        && stakeholder.getStakeholderSvatTypeSeq().equals(0)
                        && stakeholder.getSuspendedTaxRegNo() != null
                        && !expenseVoucher.getFinalVatAmount().equals(0.0)) {
                    expenseVoucher.setVatOrSVat(InvoiceType.SVAT.getInvoiceType());
                } else {
                    expenseVoucher.setVatOrSVat(InvoiceType.VAT.getInvoiceType());
                }
                if (expenseVoucher.getExpenseVoucherChargeDetailList().size() > 0) {
                    String invoiceNo = this.financialNumberGenerator.generateExpenseVoucherNo(expenseVoucher.getCompanyProfileSeq(), expenseVoucher.getDepartmentSeq(), "I");
                    expenseVoucher.setExpenseVoucherNo(invoiceNo);
                    expenseVoucher.setAmountInWord(ConvertNumberToWord.convertToCurrency(String.valueOf(NumberFormatter.round(expenseVoucher.getFinalTotalAmount(), 2))));
                    expenseVoucher.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    List<Integer> financialChargeDetailSeqList = expenseVoucher.getExpenseVoucherChargeDetailList().stream().map(ExpenseVoucherChargeDetail::getFinancialChargeDetailSeq).collect(Collectors.toList());
                    List<FinancialChargeDetail> financialChargeDetailList = this.financialChargeDetailService.findByFinancialChargeDetailSeqIn(financialChargeDetailSeqList);
                    for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
                        financialChargeDetail.setLiStatus(MasterDataStatus.CLOSED.getStatusSeq());
                    }
                    expenseVoucher = this.expenseVoucherService.save(expenseVoucher);
                    this.financialNumberGenerator.increaseExpenseVoucherNo(expenseVoucher.getCompanyProfileSeq(), expenseVoucher.getDepartmentSeq(), "E");
                    responseObject = new ResponseObject("Expense Voucher Saved Successfully", true);
                    responseObject.setObject(expenseVoucher);

                    Job job = this.jobService.findByModuleSeqAndCompanyProfileSeqAndReferenceSeq(expenseVoucher.getModuleSeq(), expenseVoucher.getCompanyProfileSeq(), expenseVoucher.getReferenceSeq());
                    job.setUpdateFlag(YesOrNo.NO.getYesOrNoSeq());
                    this.jobService.save(job);
                } else {
                    responseObject = new ResponseObject("Please Choose at least One Charge Line", false);
                }
            }
        } catch (NullPointerException e) {
            responseObject = new ResponseObject("Some Required Data are Missing", false);
        }
        return responseObject;
    }

    @Override
    public List<ExchangeRate> searchExchangeRateBank(Integer moduleSeq, Integer exchangeRateSourceType, Integer sourceBankSeq) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        QExchangeRate qExchangeRate = QExchangeRate.exchangeRate;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.from(qExchangeRate).distinct().
                where(qExchangeRate.moduleSeq.eq(moduleSeq)
                        .and(qExchangeRate.exchangeRateSourceType.eq(exchangeRateSourceType))
                        .and(qExchangeRate.bankSeq.eq(sourceBankSeq))
                        .and(qExchangeRate.companyProfileSeq.eq(companyProfileSeq))
                        .and(qExchangeRate.effectiveTo.between(new NDaysBefore().getDateNDaysAfterCurrentDate(-1), new NDaysBefore().getDateNDaysAfterCurrentDate(60)))
                        .and(qExchangeRate.status.eq(MasterDataStatus.APPROVED.getStatusSeq())))
                .transform(groupBy(qExchangeRate.exchangeRateSeq).list(qExchangeRate));
    }

    @Override
    public List<ExchangeRateDetail> searchExchangeRate(Integer exchangeRateSeq) {
        return this.exchangeRateDetailService.findByExchangeRateSeq(exchangeRateSeq);
    }

    @Override
    public FinancialCharge searchTransportBookingChargesForExpenseVoucher(Integer moduleSeq, Integer targetType, Integer transportBookingSeq) {
        return this.financeDocumentCalculator.getTransportBookingCharges(moduleSeq, targetType, transportBookingSeq);
    }

    @Override
    public List<Currency> getCurrency(Integer exchangeRateSeq) {
        return this.financeDocumentCalculator.getCurrencyList(exchangeRateSeq);
    }

    @Override
    public ResponseObject deleteExpenseVoucher(Integer expenseVoucherSeq) {
        ResponseObject responseObject;
        ExpenseVoucher dbExpenseVoucher = this.expenseVoucherService.findOne(expenseVoucherSeq);
        dbExpenseVoucher.setStatus(MasterDataStatus.DELETED.getStatusSeq());
        List<ExpenseVoucherChargeDetail> expenseVoucherChargeDetailList = dbExpenseVoucher.getExpenseVoucherChargeDetailList();
        List<FinancialChargeDetail> financialChargeDetailList = this.financialChargeDetailService.findByFinancialChargeDetailSeqIn(expenseVoucherChargeDetailList.stream().map(ExpenseVoucherChargeDetail::getFinancialChargeDetailSeq).collect(Collectors.toList()));
        for (FinancialChargeDetail chargeDetail : financialChargeDetailList) {
            chargeDetail.setStatus(MasterDataStatus.OPEN.getStatusSeq());
            chargeDetail.setLiStatus(MasterDataStatus.OPEN.getStatusSeq());
        }

        dbExpenseVoucher = this.expenseVoucherService.save(dbExpenseVoucher);
        responseObject = new ResponseObject("Expense Voucher Deleted Successfully", true);
        responseObject.setObject(dbExpenseVoucher);

        Job job = this.jobService.findByModuleSeqAndCompanyProfileSeqAndReferenceSeq(dbExpenseVoucher.getModuleSeq(), dbExpenseVoucher.getCompanyProfileSeq(), dbExpenseVoucher.getReferenceSeq());
        job.setUpdateFlag(YesOrNo.NO.getYesOrNoSeq());
        this.jobService.save(job);

        return responseObject;
    }

    @Override
    public List<ExpenseVoucher> searchExpenseVoucher(ExpenseVoucherSearchFromAux expenseVoucherSearchAux, Principal principal) {
        List<ExpenseVoucher> expenseVoucherList = null;
        try {
            QExpenseVoucher expenseVoucher = QExpenseVoucher.expenseVoucher;
            BooleanBuilder builder = new BooleanBuilder();
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            builder.and(expenseVoucher.companyProfileSeq.eq(companyProfileSeq));
            if (expenseVoucherSearchAux.getExpenseVoucherNo() != null) {
                builder.and(expenseVoucher.expenseVoucherNo.contains(expenseVoucherSearchAux.getExpenseVoucherNo()));
            }
            if (expenseVoucherSearchAux.getTargetTypeSeq() != null) {
                builder.and(expenseVoucher.financialCharge.targetType.eq(expenseVoucherSearchAux.getTargetTypeSeq()));
            }
            if (expenseVoucherSearchAux.getStartDate() != null && expenseVoucherSearchAux.getEndDate() != null) {
                builder.and(expenseVoucher.createdDate.between(expenseVoucherSearchAux.getStartDate(), DateFormatter.getEndOfTheDay(expenseVoucherSearchAux.getEndDate())));
            }
            if (expenseVoucherSearchAux.getStakeholderSeq() != null) {
                builder.and(expenseVoucher.stakeholderSeq.eq(expenseVoucherSearchAux.getStakeholderSeq()));
            }
            if (expenseVoucherSearchAux.getStatus() != null) {
                builder.and(expenseVoucher.status.eq(expenseVoucherSearchAux.getStatus()));
            } else {
                String[] statusList = {"ROLE_finance@expenseVoucher_APPROVE", "ROLE_finance@expenseVoucher_VIEW-DELETE"};
                List<MasterDataStatus> allowedStatus = MasterDataStatus.getStatusListForTransaction(Arrays.asList(statusList));
                List<Integer> statusListSeq = allowedStatus.stream().map(MasterDataStatus::getStatusSeq).collect(Collectors.toList());
                builder.and(expenseVoucher.status.in(statusListSeq));
            }
            if (expenseVoucherSearchAux.getRequestId() != null) {
                builder.and(expenseVoucher.financialCharge.referenceNo.contains(expenseVoucherSearchAux.getRequestId()));
            }
            builder = this.booleanBuilderDuplicateRemover.financialDocumentSearch(builder, expenseVoucherSearchAux.getModuleSeq(),
                    expenseVoucherSearchAux.getDepartmentSeq(), companyProfileSeq, principal, null, expenseVoucher, null);
            expenseVoucherList = (List<ExpenseVoucher>) expenseVoucherService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenseVoucherList;
    }

    @Override
    public Integer getExpenseVoucherReport(Integer expenseVoucherSeq, Integer reportSeq, Integer pdf, Integer rtf, Integer xls, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal) {
        Integer reportUploadSeq = null;
        String moduleName = "finance";
        try {
            DateFormatter dateFormatter = new DateFormatter();
            ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(expenseVoucherSeq);
            TransportBooking transportBooking = this.transportBookingService.findOne(expenseVoucher.getFinancialCharge().getReferenceSeq());
            transportBooking.setViaLocationString(this.viaLocationFormatter.getViaLocationAsString(transportBooking.getTransportBookingViaLocationList()));
            Currency currency = this.currencyService.findOne(expenseVoucher.getCompanyProfileSeq());
            Map<String, Object> param = new HashMap<>();
            param.put("EXPENSE_VOUCHER_SEQ", expenseVoucherSeq);
            param.put("MODULE_SEQ", expenseVoucher.getModuleSeq());
            param.put("CURRENCY", currency.getCurrencyCode());
            param.put("VEHICLE_NO", transportBooking.getTransportBookingVehicleList().get(0).getVehicle().getVehicleNo());
            param.put("DRIVER", transportBooking.getTransportBookingVehicleList().get(0).getDriver().getEmployeeName());
            param.put("VEHICLE_TYPE", transportBooking.getVehicleType().getVehicleTypeName());
            param.put("CHARGEABLE_KM", transportBooking.getTransportBookingFeedback().getCargoInHandKm());
            param.put("PAYMENT_MODE", transportBooking.getPaymentModeDescription());
            param.put("CUSTOMER_REF", transportBooking.getCustomerReferenceNo());
            param.put("PICKUP", transportBooking.getPickupLocation().getDestination());
            param.put("DELIVERY", transportBooking.getDeliveryLocation().getDestination());
            param.put("AAP", dateFormatter.returnLongFormattedDateTime(transportBooking.getTransportBookingFeedback().getArrivedAtPickup()));
            param.put("DFD", dateFormatter.returnLongFormattedDateTime(transportBooking.getTransportBookingFeedback().getDepartedFromDelivery()));
            param.put("VIA_LOCATIONS", transportBooking.getViaLocationString());
            param.put("TRANSPORT_BOOKING_SEQ", transportBooking.getTransportBookingSeq());
            param.put("REQUEST_ID", transportBooking.getBookingNo());
            param.put("JOB_NO", transportBooking.getJob().getJobNo());
            param.put("EXPENSE_VOUCHER_NO", expenseVoucher.getExpenseVoucherNo());
            param.put("EXPENSE_VOUCHER_DATE", dateFormatter.returnLongFormattedDateTime(expenseVoucher.getCreatedDate()));
            String expenseVoucherType = "EXPENSE VOUCHER";
            param.put("EXPENSE_VOUCHER_TYPE", expenseVoucherType);
            param.put("STAKEHOLDER_ADDRESS", expenseVoucher.getStakeholder().getStakeholderName() + "\n" + AddressBookUtils.addressBookForInvoice(expenseVoucher.getStakeholder().getAddressBook()));
            String otherTaxText = this.getOtherTaxText(expenseVoucher.getExpenseVoucherChargeDetailList());
            param.put("OTHER_TAX_TEXT", otherTaxText);
            String vatTaxText = this.financeDocumentCalculator.getVatTaxText(expenseVoucher.getFinalVatAmount(), expenseVoucher.getVatOrSVat(), expenseVoucher.getStakeholder());
            param.put("VAT_TAX_TEXT", vatTaxText);
            if (pdf != null && pdf == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        expenseVoucher.getExpenseVoucherNo(),
                        ".pdf",
                        "application/pdf",
                        httpServletRequest);
            }
            if (rtf != null && rtf == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        expenseVoucher.getExpenseVoucherNo(),
                        ".rtf",
                        "application/rtf",
                        httpServletRequest);
            }
            if (xls != null && xls == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        expenseVoucher.getExpenseVoucherNo(),
                        ".xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        httpServletRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reportUploadSeq;
    }

    private String getOtherTaxText(List<ExpenseVoucherChargeDetail> expenseVoucherChargeDetailList) {
        List<String> distinctOtherTax = new ArrayList<>();
        for (ExpenseVoucherChargeDetail expenseVoucherChargeDetail : expenseVoucherChargeDetailList) {
            if (expenseVoucherChargeDetail.getExpenseVoucherChargeDetailTaxList() != null) {
                for (ExpenseVoucherChargeDetailTax expenseVoucherChargeDetailTax : expenseVoucherChargeDetail.getExpenseVoucherChargeDetailTaxList()) {
                    if (expenseVoucherChargeDetailTax.getTaxTypeSeq().equals(2)) { //other taxes
                        distinctOtherTax.add(expenseVoucherChargeDetailTax.getTaxRegistration().getTaxName());
                    }
                }
            }
        }
        return String.join(",", distinctOtherTax);
    }

    @Override
    public List<FinancialChargeDetail> searchExpenseVoucherFinancialCharge(Integer moduleSeq, Integer financialChargeSeq, Integer exchangeRateSeq, Integer baseCurrencySeq) {
        List<FinancialChargeDetail> financialChargeDetailHouseAwbList = new ArrayList<>();
        ExchangeRate dbExchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
        Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        if (moduleSeq != null && financialChargeSeq != null) {
            if (moduleSeq.equals(transport)) {
                financialChargeDetailHouseAwbList = this.financialChargeDetailService.findByFinancialChargeSeqAndChargeTypeAndLiStatusAndStatusNot(financialChargeSeq,
                        ChargeType.COST.getChargeType(), YesOrNo.Yes.getYesOrNoSeq(), MasterDataStatus.DELETED.getStatusSeq());
            }
        }
        if (baseCurrencySeq != null) {
            this.exchangeRateConversion.dynamicConversion(financialChargeDetailHouseAwbList, dbExchangeRate.getExchangeRateSeq(), baseCurrencySeq);
        } else {
            this.exchangeRateConversion.dynamicConversion(financialChargeDetailHouseAwbList, dbExchangeRate.getExchangeRateSeq(), dbExchangeRate.getBaseCurrencySeq());
        }
        return financialChargeDetailHouseAwbList;
    }

    @Override
    public List<TaxRegistration> getVatTaxList() {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        return this.taxRegistrationService.findByCompanyProfileSeqAndTaxTypeSeqAndStatus(companyProfileSeq, 1, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @Override
    public List<TaxRegistration> getOtherTaxList() {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        return this.taxRegistrationService.findByCompanyProfileSeqAndTaxTypeSeqAndStatus(companyProfileSeq, 2, MasterDataStatus.APPROVED.getStatusSeq());
    }
}
