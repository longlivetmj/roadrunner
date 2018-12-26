package com.tmj.tms.finance.datalayer.modal.auxiliary;

public class LocalCreditNoteSummaryDetailForExpenseVoucher {
    private String currencyCode;
    private Double totalInvoicedAmount;
    private Double totalCurrentAmount;
    private Double totalInvoicedVatAmount;
    private Double totalCurrentVatAmount;
    private Double totalInvoicedNbtAmount;
    private Double totalCurrentNbtAmount;
    private Double finalTotalInvoiceAmount;
    private Double finalTotalCurrentAmount;
    private Double balanceToBeCreditedAmount;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getTotalInvoicedAmount() {
        return totalInvoicedAmount;
    }

    public void setTotalInvoicedAmount(Double totalInvoicedAmount) {
        this.totalInvoicedAmount = totalInvoicedAmount;
    }

    public Double getTotalCurrentAmount() {
        return totalCurrentAmount;
    }

    public void setTotalCurrentAmount(Double totalCurrentAmount) {
        this.totalCurrentAmount = totalCurrentAmount;
    }

    public Double getTotalInvoicedVatAmount() {
        return totalInvoicedVatAmount;
    }

    public void setTotalInvoicedVatAmount(Double totalInvoicedVatAmount) {
        this.totalInvoicedVatAmount = totalInvoicedVatAmount;
    }

    public Double getTotalCurrentVatAmount() {
        return totalCurrentVatAmount;
    }

    public void setTotalCurrentVatAmount(Double totalCurrentVatAmount) {
        this.totalCurrentVatAmount = totalCurrentVatAmount;
    }

    public Double getTotalInvoicedNbtAmount() {
        return totalInvoicedNbtAmount;
    }

    public void setTotalInvoicedNbtAmount(Double totalInvoicedNbtAmount) {
        this.totalInvoicedNbtAmount = totalInvoicedNbtAmount;
    }

    public Double getTotalCurrentNbtAmount() {
        return totalCurrentNbtAmount;
    }

    public void setTotalCurrentNbtAmount(Double totalCurrentNbtAmount) {
        this.totalCurrentNbtAmount = totalCurrentNbtAmount;
    }

    public Double getFinalTotalInvoiceAmount() {
        return finalTotalInvoiceAmount;
    }

    public void setFinalTotalInvoiceAmount(Double finalTotalInvoiceAmount) {
        this.finalTotalInvoiceAmount = finalTotalInvoiceAmount;
    }

    public Double getFinalTotalCurrentAmount() {
        return finalTotalCurrentAmount;
    }

    public void setFinalTotalCurrentAmount(Double finalTotalCurrentAmount) {
        this.finalTotalCurrentAmount = finalTotalCurrentAmount;
    }

    public Double getBalanceToBeCreditedAmount() {
        return balanceToBeCreditedAmount;
    }

    public void setBalanceToBeCreditedAmount(Double balanceToBeCreditedAmount) {
        this.balanceToBeCreditedAmount = balanceToBeCreditedAmount;
    }
}
