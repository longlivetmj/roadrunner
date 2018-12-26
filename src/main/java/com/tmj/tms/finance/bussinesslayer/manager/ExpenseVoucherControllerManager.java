package com.tmj.tms.finance.bussinesslayer.manager;

import com.tmj.tms.finance.datalayer.modal.*;
import com.tmj.tms.finance.datalayer.modal.auxiliary.ExpenseVoucherSearchAux;
import com.tmj.tms.finance.utility.ExpenseVoucherSearchFromAux;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

public interface ExpenseVoucherControllerManager {

    ResponseObject calculate(ExpenseVoucher expenseVoucher, HttpServletRequest request);

    ResponseObject createExpenseVoucher(ExpenseVoucher expenseVoucher, HttpServletRequest request);

    List<ExchangeRate> searchExchangeRateBank(Integer moduleSeq, Integer exchangeRateSourceType, Integer sourceBankSeq);

    List<ExchangeRateDetail> searchExchangeRate(Integer exchangeRateSeq);

    FinancialCharge searchTransportBookingChargesForExpenseVoucher(Integer moduleSeq, Integer targetType, Integer transportBookingSeq);

    List<Currency> getCurrency(Integer exchangeRateSeq);

    ResponseObject deleteExpenseVoucher(Integer expenseVoucherSeq);

    List<ExpenseVoucher> searchExpenseVoucher(ExpenseVoucherSearchFromAux expenseVoucherSearchAux, Principal principal);

    Integer getExpenseVoucherReport(Integer expenseVoucherSeq, Integer reportSeq, Integer pdf, Integer rtf, Integer xls,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Principal principal);

    List<FinancialChargeDetail> searchExpenseVoucherFinancialCharge(Integer moduleSeq, Integer financialChargeSeq, Integer exchangeRateSeq, Integer baseCurrencySeq);

    List<TaxRegistration> getVatTaxList();

    List<TaxRegistration> getOtherTaxList();

}
