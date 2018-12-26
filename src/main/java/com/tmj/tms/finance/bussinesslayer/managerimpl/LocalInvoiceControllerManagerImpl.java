package com.tmj.tms.finance.bussinesslayer.managerimpl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.datalayer.service.ReportUploadService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.LocalInvoiceControllerManager;
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
import org.springframework.beans.factory.annotation.Autowired;
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
public class LocalInvoiceControllerManagerImpl implements LocalInvoiceControllerManager {

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
    private final LocalInvoiceService localInvoiceService;
    private final ReportUploadService reportUploadService;
    private final ViaLocationFormatter viaLocationFormatter;
    private final BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover;
    private final JobService jobService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public LocalInvoiceControllerManagerImpl(HttpSession httpSession,
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
                                             LocalInvoiceService localInvoiceService,
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
        this.localInvoiceService = localInvoiceService;
        this.reportUploadService = reportUploadService;
        this.viaLocationFormatter = viaLocationFormatter;
        this.booleanBuilderDuplicateRemover = booleanBuilderDuplicateRemover;
        this.jobService = jobService;
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
    public FinancialCharge searchTransportBookingChargesForLocalInvoice(Integer moduleSeq, Integer targetType, Integer transportBookingSeq) {
        return this.financeDocumentCalculator.getTransportBookingCharges(moduleSeq, targetType, transportBookingSeq);
    }

    @Override
    public List<ExchangeRateDetail> searchExchangeRate(Integer exchangeRateSeq) {
        return this.exchangeRateDetailService.findByExchangeRateSeq(exchangeRateSeq);
    }

    @Override
    public List<Currency> getCurrency(Integer exchangeRateSeq) {
        return this.financeDocumentCalculator.getCurrencyList(exchangeRateSeq);
    }

    @Override
    public List<FinancialChargeDetail> searchLocalInvoiceFinancialCharge(Integer moduleSeq, Integer financialChargeSeq, Integer exchangeRateSeq, Integer baseCurrencySeq) {
        List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
        ExchangeRate dbExchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
        Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        if (moduleSeq != null && financialChargeSeq != null) {
            if (moduleSeq.equals(transport)) {
                financialChargeDetailList = this.financialChargeDetailService.findByFinancialChargeSeqAndChargeTypeAndLiStatusAndStatusNot(financialChargeSeq, ChargeType.REVENUE.getChargeType(), YesOrNo.Yes.getYesOrNoSeq(), MasterDataStatus.DELETED.getStatusSeq());
            }
        }
        if (baseCurrencySeq != null) {
            this.exchangeRateConversion.dynamicConversion(financialChargeDetailList, dbExchangeRate.getExchangeRateSeq(), baseCurrencySeq);
        } else {
            this.exchangeRateConversion.dynamicConversion(financialChargeDetailList, dbExchangeRate.getExchangeRateSeq(), dbExchangeRate.getBaseCurrencySeq());
        }
        return financialChargeDetailList;
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

    @Override
    public ResponseObject calculate(LocalInvoice localInvoice, HttpServletRequest request) {
        ResponseObject responseObject = new ResponseObject();
        Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        localInvoice.setCompanyProfileSeq(companyProfileSeq);
        if (localInvoice.getFinancialChargeSeq() != null) {
            FinancialCharge financialCharge = this.financialChargeService.findOne(localInvoice.getFinancialChargeSeq());
            if (financialCharge.getModuleSeq().equals(transport) && financialCharge.getReferenceSeq() != null && financialCharge.getTargetType().equals(TargetType.TRANSPORT_JOB.getTargetType()) && financialCharge.getReferenceType().equals(ReferenceType.TRANSPORT_BOOKING.getReferenceType())) {
                localInvoice = this.initLocalInvoice(localInvoice, request);
                localInvoice = this.financeDocumentCalculator.localInvoiceCalculationReCheck(localInvoice);
                responseObject.setStatus(true);
                responseObject.setObject(localInvoice);
            }
        }
        return responseObject;
    }

    @Override
    public ResponseObject createLocalInvoice(LocalInvoice localInvoice, HttpServletRequest request) {
        ResponseObject responseObject = this.calculate(localInvoice, request);
        try {
            if (responseObject.isStatus()) {
                TransportBooking transportBooking = this.transportBookingService.findOne(localInvoice.getReferenceSeq());
                localInvoice = (LocalInvoice) responseObject.getObject();
                localInvoice.setDepartmentSeq(transportBooking.getDepartmentSeq());
                List<LocalInvoiceExcRateMapping> excRateMappingList = new ArrayList<>();
                ExchangeRate exchangeRate = this.exchangeRateService.findOne(localInvoice.getExchangeRateSeq());
                for (ExchangeRateDetail exchangeRateDetail : exchangeRate.getExchangeRateDetails()) {
                    LocalInvoiceExcRateMapping localInvoiceExcRateMapping = new LocalInvoiceExcRateMapping();
                    localInvoiceExcRateMapping.setExchangeRateDetailSeq(exchangeRateDetail.getExchangeRateDetailSeq());
                    localInvoiceExcRateMapping.setRate(exchangeRateDetail.getRate());
                    localInvoiceExcRateMapping.setCurrencySeq(exchangeRateDetail.getCurrencySeq());
                    localInvoiceExcRateMapping.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    excRateMappingList.add(localInvoiceExcRateMapping);
                }
                localInvoice.setLocalInvoiceExcRateMappings(excRateMappingList);
                Stakeholder stakeholder = this.stakeholderService.findOne(localInvoice.getStakeholderSeq());
                if (stakeholder.getStakeholderSvatTypeSeq() != null
                        && stakeholder.getStakeholderSvatTypeSeq().equals(0)
                        && stakeholder.getSuspendedTaxRegNo() != null
                        && !localInvoice.getFinalVatAmount().equals(0.0)) {
                    localInvoice.setVatOrSVat(InvoiceType.SVAT.getInvoiceType());
                } else {
                    localInvoice.setVatOrSVat(InvoiceType.VAT.getInvoiceType());
                }
                if (localInvoice.getLocalInvoiceChargeDetailList().size() > 0) {
                    String invoiceNo = this.financialNumberGenerator.generateLocalInvoiceNo(localInvoice.getCompanyProfileSeq(), localInvoice.getDepartmentSeq(), "I");
                    localInvoice.setLocalInvoiceNo(invoiceNo);
                    localInvoice.setAmountInWord(ConvertNumberToWord.convertToCurrency(String.valueOf(NumberFormatter.round(localInvoice.getFinalTotalAmount(), 2))));
                    localInvoice.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    List<Integer> financialChargeDetailSeqList = localInvoice.getLocalInvoiceChargeDetailList().stream().map(LocalInvoiceChargeDetail::getFinancialChargeDetailSeq).collect(Collectors.toList());
                    List<FinancialChargeDetail> financialChargeDetailList = this.financialChargeDetailService.findByFinancialChargeDetailSeqIn(financialChargeDetailSeqList);
                    for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
                        financialChargeDetail.setLiStatus(MasterDataStatus.CLOSED.getStatusSeq());
                    }
                    localInvoice = this.localInvoiceService.save(localInvoice);
                    this.financialNumberGenerator.increaseLocalInvoiceNo(localInvoice.getCompanyProfileSeq(), localInvoice.getDepartmentSeq(), "I");
                    responseObject = new ResponseObject("Local Invoice Saved Successfully", true);
                    responseObject.setObject(localInvoice);

                    Job job = this.jobService.findByModuleSeqAndCompanyProfileSeqAndReferenceSeq(localInvoice.getModuleSeq(), localInvoice.getCompanyProfileSeq(), localInvoice.getReferenceSeq());
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
    public Integer getLocalInvoiceReport(Integer localInvoiceSeq, Integer reportSeq, Integer pdf, Integer rtf, Integer xls,
                                         HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal) {
        Integer reportUploadSeq = null;
        String moduleName = "finance";
        try {
            DateFormatter dateFormatter = new DateFormatter();
            LocalInvoice localInvoice = this.localInvoiceService.findOne(localInvoiceSeq);
            TransportBooking transportBooking = this.transportBookingService.findOne(localInvoice.getFinancialCharge().getReferenceSeq());
            transportBooking.setViaLocationString(this.viaLocationFormatter.getViaLocationAsString(transportBooking.getTransportBookingViaLocationList()));
            Currency currency = this.currencyService.findOne(localInvoice.getCompanyProfileSeq());
            Map<String, Object> param = new HashMap<>();
            param.put("LOCAL_INVOICE_SEQ", localInvoiceSeq);
            param.put("MODULE_SEQ", localInvoice.getModuleSeq());
            param.put("CURRENCY", currency.getCurrencyCode());
            param.put("VEHICLE_NO", transportBooking.getTransportBookingVehicleList().get(0).getVehicle().getVehicleNo());
            param.put("DRIVER", transportBooking.getTransportBookingVehicleList().get(0).getDriver().getEmployeeName());
            param.put("VEHICLE_TYPE", transportBooking.getVehicleType().getVehicleTypeName());
            param.put("CHARGEABLE_KM", transportBooking.getTransportBookingFeedback().getChargeableKm());
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
            param.put("INVOICE_NO", localInvoice.getLocalInvoiceNo());
            param.put("INVOICED_DATE", dateFormatter.returnLongFormattedDateTime(localInvoice.getCreatedDate()));
            String invoiceType = "INVOICE";
            if (localInvoice.getFinalVatAmount() > 0 && localInvoice.getVatOrSVat().equals(InvoiceType.SVAT.getInvoiceType())) {
                invoiceType = "SUSPENDED TAX INVOICE";
            } else if (localInvoice.getFinalVatAmount() > 0 && localInvoice.getVatOrSVat().equals(InvoiceType.VAT.getInvoiceType()) && localInvoice.getStakeholder().getTaxRegNo() != null) {
                invoiceType = "TAX INVOICE";
            }
            param.put("INVOICE_TYPE", invoiceType);
            param.put("STAKEHOLDER_ADDRESS", localInvoice.getStakeholder().getStakeholderName() + "\n" + AddressBookUtils.addressBookForInvoice(localInvoice.getStakeholder().getAddressBook()));
            String otherTaxText = this.getOtherTaxText(localInvoice.getLocalInvoiceChargeDetailList());
            param.put("OTHER_TAX_TEXT", otherTaxText);
            String vatTaxText = this.financeDocumentCalculator.getVatTaxText(localInvoice.getFinalVatAmount(), localInvoice.getVatOrSVat(), localInvoice.getStakeholder());
            param.put("VAT_TAX_TEXT", vatTaxText);
            if (pdf != null && pdf == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        localInvoice.getLocalInvoiceNo(),
                        ".pdf",
                        "application/pdf",
                        httpServletRequest);
            }
            if (rtf != null && rtf == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        localInvoice.getLocalInvoiceNo(),
                        ".rtf",
                        "application/rtf",
                        httpServletRequest);
            }
            if (xls != null && xls == 1) {
                reportUploadSeq = this.reportUploadService.saveReport(param,
                        moduleName,
                        reportSeq,
                        localInvoice.getLocalInvoiceNo(),
                        ".xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        httpServletRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reportUploadSeq;
    }

    @Override
    public ResponseObject deleteLocalInvoice(Integer localInvoiceSeq) {
        ResponseObject responseObject;
        LocalInvoice dbLocalInvoice = this.localInvoiceService.findOne(localInvoiceSeq);
        dbLocalInvoice.setStatus(MasterDataStatus.DELETED.getStatusSeq());
        List<LocalInvoiceChargeDetail> localInvoiceChargeDetailList = dbLocalInvoice.getLocalInvoiceChargeDetailList();
        List<FinancialChargeDetail> financialChargeDetailList = this.financialChargeDetailService.findByFinancialChargeDetailSeqIn(localInvoiceChargeDetailList.stream().map(LocalInvoiceChargeDetail::getFinancialChargeDetailSeq).collect(Collectors.toList()));
        for (FinancialChargeDetail chargeDetail : financialChargeDetailList) {
            chargeDetail.setStatus(MasterDataStatus.OPEN.getStatusSeq());
            chargeDetail.setLiStatus(MasterDataStatus.OPEN.getStatusSeq());
        }

        dbLocalInvoice = this.localInvoiceService.save(dbLocalInvoice);
        responseObject = new ResponseObject("Local Invoice Deleted Successfully", true);
        responseObject.setObject(dbLocalInvoice);

        Job job = this.jobService.findByModuleSeqAndCompanyProfileSeqAndReferenceSeq(dbLocalInvoice.getModuleSeq(), dbLocalInvoice.getCompanyProfileSeq(), dbLocalInvoice.getReferenceSeq());
        job.setUpdateFlag(YesOrNo.NO.getYesOrNoSeq());
        this.jobService.save(job);

        return responseObject;
    }

    @Override
    public List<LocalInvoice> searchLocalInvoice(LocalInvoiceSearchFromAux localInvoiceSearchAux, Principal principal) {
        List<LocalInvoice> localInvoiceList = null;
        try {
            QLocalInvoice localInvoice = QLocalInvoice.localInvoice;
            BooleanBuilder builder = new BooleanBuilder();
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            builder.and(localInvoice.companyProfileSeq.eq(companyProfileSeq));
            if (localInvoiceSearchAux.getLocalInvoiceNo() != null) {
                builder.and(localInvoice.localInvoiceNo.contains(localInvoiceSearchAux.getLocalInvoiceNo()));
            }
            if (localInvoiceSearchAux.getTargetTypeSeq() != null) {
                builder.and(localInvoice.financialCharge.targetType.eq(localInvoiceSearchAux.getTargetTypeSeq()));
            }
            if (localInvoiceSearchAux.getStartDate() != null && localInvoiceSearchAux.getEndDate() != null) {
                builder.and(localInvoice.createdDate.between(localInvoiceSearchAux.getStartDate(), DateFormatter.getEndOfTheDay(localInvoiceSearchAux.getEndDate())));
            }
            if (localInvoiceSearchAux.getStakeholderSeq() != null) {
                builder.and(localInvoice.stakeholderSeq.eq(localInvoiceSearchAux.getStakeholderSeq()));
            }
            if (localInvoiceSearchAux.getStatus() != null) {
                builder.and(localInvoice.status.eq(localInvoiceSearchAux.getStatus()));
            } else {
                String[] statusList = {"ROLE_finance@localInvoice_APPROVE", "ROLE_finance@localInvoice_VIEW-DELETE"};
                List<MasterDataStatus> allowedStatus = MasterDataStatus.getStatusListForTransaction(Arrays.asList(statusList));
                List<Integer> statusListSeq = allowedStatus.stream().map(MasterDataStatus::getStatusSeq).collect(Collectors.toList());
                builder.and(localInvoice.status.in(statusListSeq));
            }
            if (localInvoiceSearchAux.getRequestId() != null) {
                builder.and(localInvoice.financialCharge.referenceNo.contains(localInvoiceSearchAux.getRequestId()));
            }

            builder = this.booleanBuilderDuplicateRemover.financialDocumentSearch(builder, localInvoiceSearchAux.getModuleSeq(),
                    localInvoiceSearchAux.getDepartmentSeq(), companyProfileSeq, principal, localInvoice, null, null);
            localInvoiceList = (List<LocalInvoice>) localInvoiceService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localInvoiceList;
    }

    private String getOtherTaxText(List<LocalInvoiceChargeDetail> localInvoiceChargeDetailList) {
        List<String> distinctOtherTax = new ArrayList<>();
        for (LocalInvoiceChargeDetail localInvoiceChargeDetail : localInvoiceChargeDetailList) {
            if (localInvoiceChargeDetail.getLocalInvoiceChargeDetailTaxList() != null) {
                for (LocalInvoiceChargeDetailTax localInvoiceChargeDetailTax : localInvoiceChargeDetail.getLocalInvoiceChargeDetailTaxList()) {
                    if (localInvoiceChargeDetailTax.getTaxTypeSeq().equals(2)) { //other taxes
                        distinctOtherTax.add(localInvoiceChargeDetailTax.getTaxType().getTaxTypeName());
                    }
                }
            }
        }
        return String.join(",", distinctOtherTax);
    }

    private LocalInvoice initLocalInvoice(LocalInvoice localInvoice, HttpServletRequest request) {
        List<TaxRegistration> taxRegistrationList = this.taxRegistrationService.findByCompanyProfileSeqAndStatus(localInvoice.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
        if (localInvoice.getLocalInvoiceChargeDetailList() != null) {
            for (LocalInvoiceChargeDetail localInvoiceChargeDetail : localInvoice.getLocalInvoiceChargeDetailList()) {
                FinancialChargeDetail financialChargeDetail = this.financialChargeDetailService.findOne(localInvoiceChargeDetail.getFinancialChargeDetailSeq());
                financialChargeDetail = this.exchangeRateConversion.dynamicConversion(financialChargeDetail, localInvoice.getExchangeRateSeq(), localInvoice.getCurrencySeq());
                localInvoiceChargeDetail.setAmount(financialChargeDetail.getTempAmount());
                localInvoiceChargeDetail.setChargeSeq(financialChargeDetail.getChargeSeq());
                localInvoiceChargeDetail.setChargeValue(financialChargeDetail.getChargeValue());
                localInvoiceChargeDetail.setQuantity(financialChargeDetail.getQuantity());
                localInvoiceChargeDetail.setUnitSeq(financialChargeDetail.getUnitSeq());
                localInvoiceChargeDetail.setCurrencySeq(localInvoice.getCurrencySeq());
                localInvoiceChargeDetail.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                if (localInvoiceChargeDetail.getLocalInvoiceChargeDetailTaxList() == null || localInvoiceChargeDetail.getLocalInvoiceChargeDetailTaxList().size() == 0) {
                    List<LocalInvoiceChargeDetailTax> localInvoiceChargeDetailTaxList = new ArrayList<>();
                    for (TaxRegistration taxRegistration : taxRegistrationList) {
                        String taxSeqString = request.getParameter("taxRegistrationSeq_" + financialChargeDetail.getFinancialChargeDetailSeq() + "_" + taxRegistration.getTaxRegistrationSeq());
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
                }
            }
        }
        return localInvoice;
    }

}
