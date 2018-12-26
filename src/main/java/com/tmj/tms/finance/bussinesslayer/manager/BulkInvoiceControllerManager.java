package com.tmj.tms.finance.bussinesslayer.manager;

import com.tmj.tms.finance.datalayer.modal.BulkInvoice;
import com.tmj.tms.finance.datalayer.modal.ExchangeRate;
import com.tmj.tms.finance.datalayer.modal.ExchangeRateDetail;
import com.tmj.tms.finance.datalayer.modal.FinancialCharge;
import com.tmj.tms.finance.datalayer.modal.auxiliary.BulkInvoiceSearchAux;
import com.tmj.tms.finance.datalayer.modal.auxiliary.SummedCharge;
import com.tmj.tms.finance.utility.BulkInvoiceSearchFromAux;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface BulkInvoiceControllerManager {
    ResponseObject calculate(BulkInvoiceSearchAux bulkInvoiceSearchAux, HttpServletRequest request);

    ResponseObject createBulkInvoice(BulkInvoiceSearchAux bulkInvoiceSearchAux, HttpServletRequest request);

    List<ExchangeRate> searchExchangeRateBank(Integer moduleSeq, Integer exchangeRateSourceType, Integer sourceBankSeq);

    List<ExchangeRateDetail> searchExchangeRate(Integer exchangeRateSeq);

    List<FinancialCharge> searchTransportBookingChargesForBulkInvoice(BulkInvoiceSearchAux bulkInvoiceSearchAux);

    List<Currency> getCurrency(Integer exchangeRateSeq);

    Integer getBulkInvoiceReport(Integer bulkInvoiceSeq, Integer reportSeq, Integer pdf, Integer rtf, Integer xls, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal);

    ResponseObject deleteBulkInvoice(Integer bulkInvoiceSeq);

    List<BulkInvoice> searchBulkInvoice(BulkInvoiceSearchFromAux bulkInvoiceSearchFromAux, Principal principal);

    List<SummedCharge> searchBulkInvoiceFinancialCharge(BulkInvoiceSearchAux bulkInvoiceSearchAux);

    List<TaxRegistration> getVatTaxList();

    List<TaxRegistration> getOtherTaxList();

    ResponseObject changeChargeableKm(Integer transportBookingSeq, Double chargeableKm);
}
