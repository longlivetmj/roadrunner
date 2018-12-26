package com.tmj.tms.finance.utility;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.*;
import com.tmj.tms.finance.datalayer.service.ExchangeRateDetailService;
import com.tmj.tms.finance.datalayer.service.ExchangeRateService;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.master.datalayer.service.TaxRegistrationService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.utility.NumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceDocumentCalculator {

    private final TaxRegistrationService taxRegistrationService;
    private final TransportBookingService transportBookingService;
    private final FinancialChargeService financialChargeService;
    private final ExchangeRateDetailService exchangeRateDetailService;
    private final ExchangeRateService exchangeRateService;
    private final CurrencyService currencyService;

    @Autowired
    public FinanceDocumentCalculator(TaxRegistrationService taxRegistrationService,
                                     TransportBookingService transportBookingService,
                                     FinancialChargeService financialChargeService,
                                     ExchangeRateDetailService exchangeRateDetailService,
                                     ExchangeRateService exchangeRateService,
                                     CurrencyService currencyService) {
        this.taxRegistrationService = taxRegistrationService;
        this.transportBookingService = transportBookingService;
        this.financialChargeService = financialChargeService;
        this.exchangeRateDetailService = exchangeRateDetailService;
        this.exchangeRateService = exchangeRateService;
        this.currencyService = currencyService;
    }

    public LocalInvoice localInvoiceCalculationReCheck(LocalInvoice localInvoice) {
        Double vatAmount = 0.0;
        Double otherTaxAmount = 0.0;
        Double withoutTaxAmount = 0.0;
        if (localInvoice.getLocalInvoiceChargeDetailList() != null) {
            for (LocalInvoiceChargeDetail localInvoiceChargeDetail : localInvoice.getLocalInvoiceChargeDetailList()) {
                withoutTaxAmount = withoutTaxAmount + localInvoiceChargeDetail.getAmount();
                Double thisOtherTax = 0.0;
                Double thisVatAmount = 0.0;
                if (localInvoiceChargeDetail.getLocalInvoiceChargeDetailTaxList() != null) {
                    for (LocalInvoiceChargeDetailTax taxRateMapping : localInvoiceChargeDetail.getLocalInvoiceChargeDetailTaxList()) {
                        TaxRegistration taxRegistration = this.taxRegistrationService.findOne(taxRateMapping.getTaxRegistrationSeq());
                        if (taxRegistration.getTaxTypeSeq().equals(2)) { //NBT
                            thisOtherTax = thisOtherTax + localInvoiceChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100;
                            taxRateMapping.setTaxAmount(localInvoiceChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100);
                            taxRateMapping.setTaxTypeSeq(taxRegistration.getTaxTypeSeq());
                        }
                    }
                    for (LocalInvoiceChargeDetailTax taxRateMapping : localInvoiceChargeDetail.getLocalInvoiceChargeDetailTaxList()) {
                        TaxRegistration taxRegistration = this.taxRegistrationService.findOne(taxRateMapping.getTaxRegistrationSeq());
                        if (taxRegistration.getTaxTypeSeq().equals(1)) { //VAT
                            if (thisOtherTax > 0) {
                                thisVatAmount = thisVatAmount + (thisOtherTax + localInvoiceChargeDetail.getAmount()) * taxRegistration.getTaxRate() / 100;
                                taxRateMapping.setTaxAmount((thisOtherTax + localInvoiceChargeDetail.getAmount()) * taxRegistration.getTaxRate() / 100);
                                taxRateMapping.setTaxTypeSeq(taxRegistration.getTaxTypeSeq());
                            } else {
                                thisVatAmount = thisVatAmount + localInvoiceChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100;
                                taxRateMapping.setTaxAmount(localInvoiceChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100);
                            }
                            taxRateMapping.setTaxTypeSeq(taxRegistration.getTaxTypeSeq());
                        }
                    }
                    vatAmount = vatAmount + thisVatAmount;
                    otherTaxAmount = otherTaxAmount + thisOtherTax;
                }
            }
        }
        Double totalAmount = withoutTaxAmount + vatAmount + otherTaxAmount;
        localInvoice.setFinalVatAmount(NumberFormatter.round(vatAmount, 2));
        localInvoice.setFinalOtherTaxAmount(NumberFormatter.round(otherTaxAmount, 2));
        localInvoice.setFinalWithoutTaxAmount(NumberFormatter.round(withoutTaxAmount, 2));
        localInvoice.setFinalTotalAmount(NumberFormatter.round(totalAmount, 2));
        return localInvoice;
    }

    public ExpenseVoucher expenseVoucherCalculationReCheck(ExpenseVoucher expenseVoucher) {
        Double vatAmount = 0.0;
        Double otherTaxAmount = 0.0;
        Double withoutTaxAmount = 0.0;
        if (expenseVoucher.getExpenseVoucherChargeDetailList() != null) {
            for (ExpenseVoucherChargeDetail expenseVoucherChargeDetail : expenseVoucher.getExpenseVoucherChargeDetailList()) {
                withoutTaxAmount = withoutTaxAmount + expenseVoucherChargeDetail.getAmount();
                Double thisOtherTax = 0.0;
                Double thisVatAmount = 0.0;
                if (expenseVoucherChargeDetail.getExpenseVoucherChargeDetailTaxList() != null) {
                    for (ExpenseVoucherChargeDetailTax taxRateMapping : expenseVoucherChargeDetail.getExpenseVoucherChargeDetailTaxList()) {
                        TaxRegistration taxRegistration = this.taxRegistrationService.findOne(taxRateMapping.getTaxRegistrationSeq());
                        if (taxRegistration.getTaxTypeSeq().equals(2)) { //NBT
                            thisOtherTax = thisOtherTax + expenseVoucherChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100;
                            taxRateMapping.setTaxAmount(expenseVoucherChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100);
                            taxRateMapping.setTaxTypeSeq(taxRegistration.getTaxTypeSeq());
                        }
                    }
                    for (ExpenseVoucherChargeDetailTax taxRateMapping : expenseVoucherChargeDetail.getExpenseVoucherChargeDetailTaxList()) {
                        TaxRegistration taxRegistration = this.taxRegistrationService.findOne(taxRateMapping.getTaxRegistrationSeq());
                        if (taxRegistration.getTaxTypeSeq().equals(1)) { //VAT
                            if (thisOtherTax > 0) {
                                thisVatAmount = thisVatAmount + (thisOtherTax + expenseVoucherChargeDetail.getAmount()) * taxRegistration.getTaxRate() / 100;
                                taxRateMapping.setTaxAmount((thisOtherTax + expenseVoucherChargeDetail.getAmount()) * taxRegistration.getTaxRate() / 100);
                            } else {
                                thisVatAmount = thisVatAmount + expenseVoucherChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100;
                                taxRateMapping.setTaxAmount(expenseVoucherChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100);
                            }
                            taxRateMapping.setTaxTypeSeq(taxRegistration.getTaxTypeSeq());
                        }
                    }
                    vatAmount = vatAmount + thisVatAmount;
                    otherTaxAmount = otherTaxAmount + thisOtherTax;
                }
            }
        }
        Double totalAmount = withoutTaxAmount + vatAmount + otherTaxAmount;
        expenseVoucher.setFinalVatAmount(NumberFormatter.round(vatAmount, 2));
        expenseVoucher.setFinalOtherTaxAmount(NumberFormatter.round(otherTaxAmount, 2));
        expenseVoucher.setFinalWithoutTaxAmount(NumberFormatter.round(withoutTaxAmount, 2));
        expenseVoucher.setFinalTotalAmount(NumberFormatter.round(totalAmount, 2));
        return expenseVoucher;
    }

    public FinancialCharge getTransportBookingCharges(Integer moduleSeq, Integer targetType, Integer transportBookingSeq) {
        FinancialCharge financialCharge = null;
        try {
            if (targetType != null && targetType.equals(TargetType.TRANSPORT_JOB.getTargetType())) {
                financialCharge = this.financialChargeService.findByTargetTypeAndReferenceTypeAndReferenceSeqAndModuleSeqAndStatusNot(targetType, ReferenceType.TRANSPORT_BOOKING.getReferenceType(), transportBookingSeq, moduleSeq, MasterDataStatus.DELETED.getStatusSeq());
                financialCharge.setTransportBooking(this.transportBookingService.findOne(transportBookingSeq));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return financialCharge;
    }

    public List<Currency> getCurrencyList(Integer exchangeRateSeq) {
        List<Integer> currencyList = new ArrayList<>();
        List<ExchangeRateDetail> dbExchangeRateDetail = this.exchangeRateDetailService.findByExchangeRateSeq(exchangeRateSeq);
        ExchangeRate dbExchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
        List<Integer> currencySeqList = dbExchangeRateDetail.stream().map(ExchangeRateDetail::getCurrencySeq).collect(Collectors.toList());
        currencyList.addAll(currencySeqList);
        currencyList.add(dbExchangeRate.getBaseCurrencySeq());
        return this.currencyService.findByCurrencySeqIn(currencyList);
    }

    public String getVatTaxText(Double finalVatAmount, Integer vatOrSVat, Stakeholder stakeholder) {
        String vatTaxText;
        if (finalVatAmount > 0 && vatOrSVat.equals(InvoiceType.SVAT.getInvoiceType())) {
            vatTaxText = "SUSPENDED VAT";
        } else if (finalVatAmount > 0 && vatOrSVat.equals(InvoiceType.VAT.getInvoiceType()) && stakeholder.getTaxRegNo() != null) {
            vatTaxText = "VAT";
        } else {
            vatTaxText = "GOVERNMENT LEVY AND STATUTORY TAXES";
        }
        return vatTaxText;
    }

    public BulkInvoice bulkInvoiceCalculationReCheck(BulkInvoice bulkInvoice) {
        Double vatAmount = 0.0;
        Double otherTaxAmount = 0.0;
        Double withoutTaxAmount = 0.0;
        if (bulkInvoice.getBulkInvoiceChargeDetailList() != null) {
            for (BulkInvoiceChargeDetail bulkInvoiceChargeDetail : bulkInvoice.getBulkInvoiceChargeDetailList()) {
                withoutTaxAmount = withoutTaxAmount + bulkInvoiceChargeDetail.getAmount();
                Double thisOtherTax = 0.0;
                Double thisVatAmount = 0.0;
                if (bulkInvoiceChargeDetail.getBulkInvoiceChargeDetailTaxList() != null) {
                    for (BulkInvoiceChargeDetailTax taxRateMapping : bulkInvoiceChargeDetail.getBulkInvoiceChargeDetailTaxList()) {
                        TaxRegistration taxRegistration = this.taxRegistrationService.findOne(taxRateMapping.getTaxRegistrationSeq());
                        if (taxRegistration.getTaxTypeSeq().equals(2)) { //NBT
                            thisOtherTax = thisOtherTax + bulkInvoiceChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100;
                            taxRateMapping.setTaxAmount(bulkInvoiceChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100);
                            taxRateMapping.setTaxTypeSeq(taxRegistration.getTaxTypeSeq());
                        }
                    }
                    for (BulkInvoiceChargeDetailTax taxRateMapping : bulkInvoiceChargeDetail.getBulkInvoiceChargeDetailTaxList()) {
                        TaxRegistration taxRegistration = this.taxRegistrationService.findOne(taxRateMapping.getTaxRegistrationSeq());
                        if (taxRegistration.getTaxTypeSeq().equals(1)) { //VAT
                            if (thisOtherTax > 0) {
                                thisVatAmount = thisVatAmount + (thisOtherTax + bulkInvoiceChargeDetail.getAmount()) * taxRegistration.getTaxRate() / 100;
                                taxRateMapping.setTaxAmount((thisOtherTax + bulkInvoiceChargeDetail.getAmount()) * taxRegistration.getTaxRate() / 100);
                                taxRateMapping.setTaxTypeSeq(taxRegistration.getTaxTypeSeq());
                            } else {
                                thisVatAmount = thisVatAmount + bulkInvoiceChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100;
                                taxRateMapping.setTaxAmount(bulkInvoiceChargeDetail.getAmount() * taxRegistration.getTaxRate() / 100);
                            }
                            taxRateMapping.setTaxTypeSeq(taxRegistration.getTaxTypeSeq());
                        }
                    }
                    vatAmount = vatAmount + thisVatAmount;
                    otherTaxAmount = otherTaxAmount + thisOtherTax;
                }
            }
        }
        Double totalAmount = withoutTaxAmount + vatAmount + otherTaxAmount;
        bulkInvoice.setFinalVatAmount(NumberFormatter.round(vatAmount, 2));
        bulkInvoice.setFinalOtherTaxAmount(NumberFormatter.round(otherTaxAmount, 2));
        bulkInvoice.setFinalWithoutTaxAmount(NumberFormatter.round(withoutTaxAmount, 2));
        bulkInvoice.setFinalTotalAmount(NumberFormatter.round(totalAmount, 2));
        return bulkInvoice;
    }
}
