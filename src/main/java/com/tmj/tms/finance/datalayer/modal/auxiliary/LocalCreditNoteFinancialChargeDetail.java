package com.tmj.tms.finance.datalayer.modal.auxiliary;

import com.tmj.tms.finance.datalayer.modal.LocalInvoiceChargeDetailTax;

import javax.persistence.Transient;
import java.util.List;

public class LocalCreditNoteFinancialChargeDetail {
    private Integer localInvoiceFinChrMapSeq;
    private Integer lcnChargeDetailSeq;
    private Integer localInvoiceSeq;
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
    private List<LocalInvoiceChargeDetailTax> localInvoiceChargeDetailTaxList;

    public Integer getLocalInvoiceFinChrMapSeq() {
        return localInvoiceFinChrMapSeq;
    }

    public void setLocalInvoiceFinChrMapSeq(Integer localInvoiceFinChrMapSeq) {
        this.localInvoiceFinChrMapSeq = localInvoiceFinChrMapSeq;
    }

    public Integer getLocalInvoiceSeq() {
        return localInvoiceSeq;
    }

    public void setLocalInvoiceSeq(Integer localInvoiceSeq) {
        this.localInvoiceSeq = localInvoiceSeq;
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

    public List<LocalInvoiceChargeDetailTax> getLocalInvoiceChargeDetailTaxList() {
        return localInvoiceChargeDetailTaxList;
    }

    public void setLocalInvoiceChargeDetailTaxList(List<LocalInvoiceChargeDetailTax> localInvoiceChargeDetailTaxList) {
        this.localInvoiceChargeDetailTaxList = localInvoiceChargeDetailTaxList;
    }

    public Integer getLcnChargeDetailSeq() {
        return lcnChargeDetailSeq;
    }

    public void setLcnChargeDetailSeq(Integer lcnChargeDetailSeq) {
        this.lcnChargeDetailSeq = lcnChargeDetailSeq;
    }

    @Transient
    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus;
    }
}
