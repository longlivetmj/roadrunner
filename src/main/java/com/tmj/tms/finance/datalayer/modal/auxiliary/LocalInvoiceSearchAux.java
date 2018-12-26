package com.tmj.tms.finance.datalayer.modal.auxiliary;

import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;

public class LocalInvoiceSearchAux {
    private Integer moduleSeq;
    private Integer exchangeRateSourceType;
    private Integer sourceBankSeq;
    private Integer transportBookingSeq;
    private Integer baseCurrencySeq;
    private Integer invoicePartySeq;
    private String stakeholderAddress;
    private Integer targetType;
    private Integer referenceType;
    private Integer referenceSeq;

    private Stakeholder invoiceParty;
    private TransportBooking transportBooking;

    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getExchangeRateSourceType() {
        return exchangeRateSourceType;
    }

    public void setExchangeRateSourceType(Integer exchangeRateSourceType) {
        this.exchangeRateSourceType = exchangeRateSourceType;
    }

    public Integer getSourceBankSeq() {
        return sourceBankSeq;
    }

    public void setSourceBankSeq(Integer sourceBankSeq) {
        this.sourceBankSeq = sourceBankSeq;
    }

    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    public Integer getBaseCurrencySeq() {
        return baseCurrencySeq;
    }

    public void setBaseCurrencySeq(Integer baseCurrencySeq) {
        this.baseCurrencySeq = baseCurrencySeq;
    }

    public Integer getInvoicePartySeq() {
        return invoicePartySeq;
    }

    public void setInvoicePartySeq(Integer invoicePartySeq) {
        this.invoicePartySeq = invoicePartySeq;
    }

    public String getStakeholderAddress() {
        return stakeholderAddress;
    }

    public void setStakeholderAddress(String stakeholderAddress) {
        this.stakeholderAddress = stakeholderAddress;
    }

    public Stakeholder getInvoiceParty() {
        return invoiceParty;
    }

    public void setInvoiceParty(Stakeholder invoiceParty) {
        this.invoiceParty = invoiceParty;
    }

    public TransportBooking getTransportBooking() {
        return transportBooking;
    }

    public void setTransportBooking(TransportBooking transportBooking) {
        this.transportBooking = transportBooking;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceSeq() {
        return referenceSeq;
    }

    public void setReferenceSeq(Integer referenceSeq) {
        this.referenceSeq = referenceSeq;
    }
}
