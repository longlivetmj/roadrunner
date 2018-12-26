package com.tmj.tms.finance.datalayer.modal.auxiliary;

import java.util.Date;

public class ExchangeRateSearchAux {

    Integer baseCurrencySeq;
    private Date effectiveFrom;
    private Integer status;
    private Integer exchangeRateSeq;
    private Integer moduleSeq;
    private Integer transportBookingSeq;
    private Integer jobSeq;

    public Integer getBaseCurrencySeq() {
        return baseCurrencySeq;
    }

    public void setBaseCurrencySeq(Integer baseCurrencySeq) {
        this.baseCurrencySeq = baseCurrencySeq;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExchangeRateSeq() {
        return exchangeRateSeq;
    }

    public void setExchangeRateSeq(Integer exchangeRateSeq) {
        this.exchangeRateSeq = exchangeRateSeq;
    }

    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    public Integer getJobSeq() {
        return jobSeq;
    }

    public void setJobSeq(Integer jobSeq) {
        this.jobSeq = jobSeq;
    }
}
