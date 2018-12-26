package com.tmj.tms.finance.utility;

import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.finance.datalayer.modal.ExchangeRate;
import com.tmj.tms.finance.datalayer.modal.ExchangeRateDetail;
import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import com.tmj.tms.finance.datalayer.service.*;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateConversion {
    private final ExchangeRateDetailService exchangeRateDetailService;
    private final ExchangeRateService exchangeRateService;
    private final CurrencyService currencyService;
    private final LocalInvoiceChargeDetailService localInvoiceChargeDetailService;
    private final LocalInvoiceExcRateMappingService localInvoiceExcRateMappingService;
    private final LocalInvoiceService localInvoiceService;
    private final FinancialChargeService financialChargeService;
    private final ModuleService moduleService;
    private final FinancialChargeDetailService financialChargeDetailService;

    @Autowired
    public ExchangeRateConversion(ExchangeRateDetailService exchangeRateDetailService,
                                  ExchangeRateService exchangeRateService,
                                  CurrencyService currencyService,
                                  LocalInvoiceChargeDetailService localInvoiceChargeDetailService,
                                  LocalInvoiceExcRateMappingService localInvoiceExcRateMappingService,
                                  LocalInvoiceService localInvoiceService,
                                  FinancialChargeService financialChargeService,
                                  ModuleService moduleService,
                                  FinancialChargeDetailService financialChargeDetailService) {
        this.exchangeRateDetailService = exchangeRateDetailService;
        this.exchangeRateService = exchangeRateService;
        this.currencyService = currencyService;
        this.localInvoiceChargeDetailService = localInvoiceChargeDetailService;
        this.localInvoiceExcRateMappingService = localInvoiceExcRateMappingService;
        this.localInvoiceService = localInvoiceService;
        this.financialChargeService = financialChargeService;
        this.moduleService = moduleService;
        this.financialChargeDetailService = financialChargeDetailService;
    }

    public List<FinancialChargeDetail> dynamicConversion(List<FinancialChargeDetail> financialChargeList,
                                                         Integer exchangeRateSeq,
                                                         Integer dynamicCurrency) {
        ExchangeRateDetail invoicingCurrency = this.exchangeRateDetailService.findByExchangeRateSeqAndCurrencySeq(exchangeRateSeq, dynamicCurrency);
        Double rateCharge;
        if (invoicingCurrency == null) {
            rateCharge = 1.00; // only if invoicing in base currency
        } else {
            rateCharge = invoicingCurrency.getRate();
        }
        for (FinancialChargeDetail chargeDetail : financialChargeList) {
            Currency dbCurrency = this.currencyService.findOne(dynamicCurrency);
            chargeDetail.setDynamicRate(1.00);
            chargeDetail.setCheckedStatus("");
            chargeDetail.setTempCurrencyCode(dbCurrency.getCurrencyCode());
            ExchangeRateDetail multiply = this.exchangeRateDetailService.findByExchangeRateSeqAndCurrencySeq(exchangeRateSeq, chargeDetail.getCurrencySeq());
            if (multiply != null) {
                chargeDetail.setTempAmount(chargeDetail.getAmount() * multiply.getRate() / rateCharge);
                chargeDetail.setTempCurrencyCode(dbCurrency.getCurrencyCode());
            } else { // Multiply is null when chargeDetail.getCurrencySeq() does not exists in exchange rate details or its base currency
                ExchangeRate exchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
                if (exchangeRate.getBaseCurrencySeq().equals(chargeDetail.getCurrencySeq())) {
                    chargeDetail.setTempAmount(chargeDetail.getAmount() / rateCharge);
                    chargeDetail.setTempCurrencyCode(dbCurrency.getCurrencyCode());
                } else {
                    chargeDetail.setCheckedStatus("disabled");
                    chargeDetail.setDynamicRate(1.00);
                    chargeDetail.setTempAmount(chargeDetail.getAmount());
                }
            }
        }
        return financialChargeList;
    }

    public FinancialChargeDetail dynamicConversion(FinancialChargeDetail financialChargeDetail,
                                                   Integer exchangeRateSeq,
                                                   Integer dynamicCurrency) {
        ExchangeRateDetail invoicingCurrency = this.exchangeRateDetailService.findByExchangeRateSeqAndCurrencySeq(exchangeRateSeq, dynamicCurrency);
        Double rateCharge;
        if (invoicingCurrency == null) {
            rateCharge = 1.00; // only if invoicing in base currency
        } else {
            rateCharge = invoicingCurrency.getRate();
        }
        Currency dbCurrency = this.currencyService.findOne(dynamicCurrency);
        financialChargeDetail.setDynamicRate(1.00);
        financialChargeDetail.setCheckedStatus("");
        financialChargeDetail.setTempCurrencyCode(dbCurrency.getCurrencyCode());
        ExchangeRateDetail multiply = this.exchangeRateDetailService.findByExchangeRateSeqAndCurrencySeq(exchangeRateSeq, financialChargeDetail.getCurrencySeq());
        if (multiply != null) {
            financialChargeDetail.setTempAmount(financialChargeDetail.getAmount() * multiply.getRate() / rateCharge);
            financialChargeDetail.setTempCurrencyCode(dbCurrency.getCurrencyCode());
        } else { // Multiply is null when chargeDetail.getCurrencySeq() does not exists in exchange rate details or its base currency
            ExchangeRate exchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
            if (exchangeRate.getBaseCurrencySeq().equals(financialChargeDetail.getCurrencySeq())) {
                financialChargeDetail.setTempAmount(financialChargeDetail.getAmount() / rateCharge);
                financialChargeDetail.setTempCurrencyCode(dbCurrency.getCurrencyCode());
            } else {
                financialChargeDetail.setCheckedStatus("disabled");
                financialChargeDetail.setDynamicRate(1.00);
                financialChargeDetail.setTempAmount(financialChargeDetail.getAmount());
            }
        }
        return financialChargeDetail;
    }

    /*public List<LocalInvFinChargeMapping> currencyConversionForLocalInvoice(List<LocalInvFinChargeMapping> localInvFinChargeMappingList, Integer exchangeRateSeq) {
        Double rateCharge;
        Integer currencySeq = localInvFinChargeMappingList.get(0).getLocalInvoice().getBaseCurrencySeq();
        Integer localInvoiceSeq = localInvFinChargeMappingList.get(0).getLocalInvoiceSeq();
        LocalInvoiceExcRateMapping localInvoiceExcRateMapping = this.localInvoiceExcRateMappingService.findByLocalInvoiceSeqAndCurrencySeq(localInvoiceSeq, currencySeq);
        if (localInvoiceExcRateMapping == null) {
            rateCharge = 1.00;
        } else {
            rateCharge = localInvoiceExcRateMapping.getRate();
        }

        LocalInvoice localInvoice = this.localInvoiceService.findOne(localInvoiceSeq);

        for (LocalInvFinChargeMapping localInvFinChargeMapping : localInvFinChargeMappingList) {
            LocalInvoiceExcRateMapping multiply = this.localInvoiceExcRateMappingService.findByLocalInvoiceSeqAndCurrencySeq(localInvoiceSeq, localInvFinChargeMapping.getFinancialChargeDetail().getCurrencySeq());
            if (multiply != null) {
                ExchangeRateDetail exchangeRateDetail = this.exchangeRateDetailService.findOne(multiply.getExchangeRateDetailSeq());
                if (!localInvoice.getExchangeRateSeq().equals(exchangeRateDetail.getExchangeRateSeq())) {
                    multiply = new LocalInvoiceExcRateMapping();
                    ExchangeRateDetail filteredExchangeRateDetail = localInvoice.getExchangeRate().getExchangeRateDetails().stream().filter(i -> i.getCurrencySeq().equals(localInvFinChargeMapping.getFinancialChargeDetail().getCurrencySeq())).findFirst().get();
                    multiply.setRate(filteredExchangeRateDetail.getRate());
                    multiply.setCurrencySeq(filteredExchangeRateDetail.getCurrencySeq());
                }
                localInvFinChargeMapping.getFinancialChargeDetail().setConvertedAmount(localInvFinChargeMapping.getFinancialChargeDetail().getAmount() * multiply.getRate() / rateCharge);
            } else { // Multiply is null when chargeDetail.getCurrencySeq() does not exists in exchange rate details or its base currency
                if (currencySeq.equals(localInvFinChargeMapping.getFinancialChargeDetail().getCurrencySeq())) {
                    localInvFinChargeMapping.getFinancialChargeDetail().setConvertedAmount(localInvFinChargeMapping.getFinancialChargeDetail().getAmount());
                } else {
                    ExchangeRate exchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
                    if (exchangeRate.getBaseCurrencySeq().equals(localInvFinChargeMapping.getFinancialChargeDetail().getCurrencySeq())) {
                        localInvFinChargeMapping.getFinancialChargeDetail().setConvertedAmount(localInvFinChargeMapping.getFinancialChargeDetail().getAmount() / rateCharge);
                    } else {
                        localInvFinChargeMapping.getFinancialChargeDetail().setCheckedStatus("disabled");
                        localInvFinChargeMapping.getFinancialChargeDetail().setConvertedAmount(localInvFinChargeMapping.getFinancialChargeDetail().getAmount());
                    }
                }
            }
        }
        return localInvFinChargeMappingList;

    }

    public List<FinancialChargeDetail> currencyConversionForLocalInvoiceFinChaDetail(List<FinancialChargeDetail> financialChargeDetailList, Integer localInvoiceSeq, Integer currencySeq, Integer exchangeRateSeq) {
        Double rateCharge;
        LocalInvoiceExcRateMapping localInvoiceExcRateMapping = this.localInvoiceExcRateMappingService.findByLocalInvoiceSeqAndCurrencySeq(localInvoiceSeq, currencySeq);
        if (localInvoiceExcRateMapping == null) {
            rateCharge = 1.00;
        } else {
            rateCharge = localInvoiceExcRateMapping.getRate();
        }

        LocalInvoice localInvoice = this.localInvoiceService.findOne(localInvoiceSeq);

        for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
            LocalInvoiceExcRateMapping multiply = this.localInvoiceExcRateMappingService.findByLocalInvoiceSeqAndCurrencySeq(localInvoiceSeq, financialChargeDetail.getCurrencySeq());
            if (multiply != null) {
                ExchangeRateDetail exchangeRateDetail = this.exchangeRateDetailService.findOne(multiply.getExchangeRateDetailSeq());
                if (!localInvoice.getExchangeRateSeq().equals(exchangeRateDetail.getExchangeRateSeq())) {
                    multiply = new LocalInvoiceExcRateMapping();
                    ExchangeRateDetail filteredExchangeRateDetail = localInvoice.getExchangeRate().getExchangeRateDetails().stream().filter(i -> i.getCurrencySeq().equals(financialChargeDetail.getCurrencySeq())).findFirst().get();
                    multiply.setRate(filteredExchangeRateDetail.getRate());
                    multiply.setCurrencySeq(filteredExchangeRateDetail.getCurrencySeq());
                }
                financialChargeDetail.setConvertedAmount(financialChargeDetail.getAmount() * multiply.getRate() / rateCharge);
            } else { // Multiply is null when chargeDetail.getCurrencySeq() does not exists in exchange rate details or its base currency
                ExchangeRate exchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
                if (exchangeRate.getBaseCurrencySeq().equals(financialChargeDetail.getCurrencySeq())) {
                    financialChargeDetail.setConvertedAmount(financialChargeDetail.getAmount() / rateCharge);
                } else {
                    financialChargeDetail.setCheckedStatus("disabled");
                    financialChargeDetail.setConvertedAmount(financialChargeDetail.getAmount());
                }
            }
        }
        return financialChargeDetailList;
    }

    public List<ExpenseVouFinChargeMapping> currencyConversionForExpenseVoucher(List<ExpenseVouFinChargeMapping> expenseVouFinChargeMappingList, Integer exchangeRateSeq) {
        Double rateCharge;
        Integer currencySeq = expenseVouFinChargeMappingList.get(0).getExpenseVoucher().getBaseCurrencySeq();
        Integer expenseVoucherSeq = expenseVouFinChargeMappingList.get(0).getExpenseVoucherSeq();
        ExpenseVouExcRateMapping expenseVouExcRateMapping = this.expenseVouExcRateMappingService.findByExpenseVoucherSeqAndCurrencySeq(expenseVoucherSeq, currencySeq);
        if (expenseVouExcRateMapping == null) {
            rateCharge = 1.00;
        } else {
            rateCharge = expenseVouExcRateMapping.getRate();
        }

        ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(expenseVoucherSeq);

        for (ExpenseVouFinChargeMapping expenseVouFinChargeMapping : expenseVouFinChargeMappingList) {
            ExpenseVouExcRateMapping multiply = this.expenseVouExcRateMappingService.findByExpenseVoucherSeqAndCurrencySeq(expenseVoucherSeq, expenseVouFinChargeMapping.getFinancialChargeDetail().getCurrencySeq());
            if (multiply != null) {
                ExchangeRateDetail exchangeRateDetail = this.exchangeRateDetailService.findOne(multiply.getExchangeRateDetailSeq());
                if (!expenseVoucher.getExchangeRateSeq().equals(exchangeRateDetail.getExchangeRateSeq())) {
                    multiply = new ExpenseVouExcRateMapping();
                    ExchangeRateDetail filteredExchangeRateDetail = expenseVoucher.getExchangeRate().getExchangeRateDetails().stream().filter(i -> i.getCurrencySeq().equals(expenseVouFinChargeMapping.getFinancialChargeDetail().getCurrencySeq())).findFirst().get();
                    multiply.setRate(filteredExchangeRateDetail.getRate());
                    multiply.setCurrencySeq(filteredExchangeRateDetail.getCurrencySeq());
                }
                expenseVouFinChargeMapping.getFinancialChargeDetail().setConvertedAmount(expenseVouFinChargeMapping.getFinancialChargeDetail().getAmount() * multiply.getRate() / rateCharge);
            } else {
                ExchangeRate exchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
                if (exchangeRate.getBaseCurrencySeq().equals(expenseVouFinChargeMapping.getFinancialChargeDetail().getCurrencySeq())) {
                    expenseVouFinChargeMapping.getFinancialChargeDetail().setConvertedAmount(expenseVouFinChargeMapping.getFinancialChargeDetail().getAmount() / rateCharge);
                } else {
                    expenseVouFinChargeMapping.getFinancialChargeDetail().setCheckedStatus("disabled");
                    expenseVouFinChargeMapping.getFinancialChargeDetail().setConvertedAmount(expenseVouFinChargeMapping.getFinancialChargeDetail().getAmount());
                }
            }
        }
        return expenseVouFinChargeMappingList;
    }

    public List<FinancialChargeDetail> currencyConversionForExpenseVoucherFinChaDetail(List<FinancialChargeDetail> financialChargeDetailList, Integer expenseVoucherSeq, Integer currencySeq, Integer exchangeRateSeq) {
        Double rateCharge;
        ExpenseVouExcRateMapping expenseVouExcRateMapping = this.expenseVouExcRateMappingService.findByExpenseVoucherSeqAndCurrencySeq(expenseVoucherSeq, currencySeq);
        if (expenseVouExcRateMapping == null) {
            rateCharge = 1.00;
        } else {
            rateCharge = expenseVouExcRateMapping.getRate();
        }

        ExpenseVoucher expenseVoucher = this.expenseVoucherService.findOne(expenseVoucherSeq);

        for (FinancialChargeDetail financialChargeDetail : financialChargeDetailList) {
            ExpenseVouExcRateMapping multiply = this.expenseVouExcRateMappingService.findByExpenseVoucherSeqAndCurrencySeq(expenseVoucherSeq, financialChargeDetail.getCurrencySeq());
            if (multiply != null) {
                ExchangeRateDetail exchangeRateDetail = this.exchangeRateDetailService.findOne(multiply.getExchangeRateDetailSeq());
                if (!expenseVoucher.getExchangeRateSeq().equals(exchangeRateDetail.getExchangeRateSeq())) {
                    multiply = new ExpenseVouExcRateMapping();
                    ExchangeRateDetail filteredExchangeRateDetail = expenseVoucher.getExchangeRate().getExchangeRateDetails().stream().filter(i -> i.getCurrencySeq().equals(financialChargeDetail.getCurrencySeq())).findFirst().get();
                    multiply.setRate(filteredExchangeRateDetail.getRate());
                    multiply.setCurrencySeq(filteredExchangeRateDetail.getCurrencySeq());
                }
                financialChargeDetail.setConvertedAmount(financialChargeDetail.getAmount() * multiply.getRate() / rateCharge);
            } else {
                ExchangeRate exchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
                if (exchangeRate.getBaseCurrencySeq().equals(financialChargeDetail.getCurrencySeq())) {
                    financialChargeDetail.setConvertedAmount(financialChargeDetail.getAmount() / rateCharge);
                } else {
                    financialChargeDetail.setCheckedStatus("disabled");
                    financialChargeDetail.setConvertedAmount(financialChargeDetail.getAmount());
                }
            }
        }
        return financialChargeDetailList;
    }
    */
}




