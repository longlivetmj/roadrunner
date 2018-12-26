package com.tmj.tms.finance.bussinesslayer.manager;

import com.tmj.tms.finance.datalayer.modal.*;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalInvoiceSearchAux;
import com.tmj.tms.finance.utility.LocalInvoiceSearchFromAux;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface LocalInvoiceControllerManager {

    List<ExchangeRate> searchExchangeRateBank(Integer moduleSeq, Integer exchangeRateSourceType, Integer sourceBankSeq);

    FinancialCharge searchTransportBookingChargesForLocalInvoice(Integer moduleSeq, Integer targetType, Integer transportBookingSeq);

    List<ExchangeRateDetail> searchExchangeRate(Integer exchangeRateSeq);

    List<Currency> getCurrency(Integer exchangeRateSeq);

    List<FinancialChargeDetail> searchLocalInvoiceFinancialCharge(Integer moduleSeq, Integer financialChargeSeq, Integer exchangeRateSeq, Integer baseCurrencySeq);

    List<TaxRegistration> getVatTaxList();

    List<TaxRegistration> getOtherTaxList();

    ResponseObject calculate(LocalInvoice localInvoice, HttpServletRequest request);

    ResponseObject createLocalInvoice(LocalInvoice localInvoice, HttpServletRequest request);

    Integer getLocalInvoiceReport(Integer localInvoiceSeq, Integer reportSeq, Integer pdf, Integer rtf, Integer xls,
                                  HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal);

    ResponseObject deleteLocalInvoice(Integer localInvoiceSeq);

    List<LocalInvoice> searchLocalInvoice(LocalInvoiceSearchFromAux localInvoiceSearchFromAux, Principal principal);
}
