package com.tmj.tms.finance.datalayer.modal.auxiliary;


import com.tmj.tms.finance.datalayer.modal.ExpenseVoucherChargeDetailTax;

import java.util.List;


public class LocalCreditNoteFinanceChargeDetailForExpVoucher {
    private Integer expenseVouFinChrMapSeq;
    private Integer lcnChargeDetailSeq;
    private Integer expenseVoucherSeq;
    private Integer financialChargeSeq;
    private Integer financialChargeDetailSeq;
    private Integer chargeSeq;
    private String chargeName;
    private Integer transportBookingSeq;
    private Integer currencySeq;
    private String currencyCode;
    private Double invoicedAmount;
    private Double currentAmount;
    private String checkedStatus;
    private List<ExpenseVoucherChargeDetailTax> expenseVoucherChargeDetailTaxList;

    public Integer getExpenseVouFinChrMapSeq() {
        return expenseVouFinChrMapSeq;
    }

    public void setExpenseVouFinChrMapSeq(Integer expenseVouFinChrMapSeq) {
        this.expenseVouFinChrMapSeq = expenseVouFinChrMapSeq;
    }

    public Integer getExpenseVoucherSeq() {
        return expenseVoucherSeq;
    }

    public void setExpenseVoucherSeq(Integer expenseVoucherSeq) {
        this.expenseVoucherSeq = expenseVoucherSeq;
    }

    public Integer getFinancialChargeSeq() {
        return financialChargeSeq;
    }

    public void setFinancialChargeSeq(Integer financialChargeSeq) {
        this.financialChargeSeq = financialChargeSeq;
    }

    public Integer getFinancialChargeDetailSeq() {
        return financialChargeDetailSeq;
    }

    public void setFinancialChargeDetailSeq(Integer financialChargeDetailSeq) {
        this.financialChargeDetailSeq = financialChargeDetailSeq;
    }

    public Integer getChargeSeq() {
        return chargeSeq;
    }

    public void setChargeSeq(Integer chargeSeq) {
        this.chargeSeq = chargeSeq;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getInvoicedAmount() {
        return invoicedAmount;
    }

    public void setInvoicedAmount(Double invoicedAmount) {
        this.invoicedAmount = invoicedAmount;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

    public List<ExpenseVoucherChargeDetailTax> getExpenseVoucherChargeDetailTaxList() {
        return expenseVoucherChargeDetailTaxList;
    }

    public void setExpenseVoucherChargeDetailTaxList(List<ExpenseVoucherChargeDetailTax> expenseVoucherChargeDetailTaxList) {
        this.expenseVoucherChargeDetailTaxList = expenseVoucherChargeDetailTaxList;
    }

    public Integer getLcnChargeDetailSeq() {
        return lcnChargeDetailSeq;
    }

    public void setLcnChargeDetailSeq(Integer lcnChargeDetailSeq) {
        this.lcnChargeDetailSeq = lcnChargeDetailSeq;
    }
}
