package com.tmj.tms.finance.datalayer.modal.auxiliary;

import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Unit;

import java.util.List;

public class SummedCharge {

    private Charge charge;
    private Unit unit;
    private Integer unitSeq;
    private Currency currency;
    private Double quantity;
    private Double chargeValue;
    private String checkedStatus;
    private Integer chargeSeq;
    private List<FinancialChargeDetail> financialChargeDetailList;

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getChargeValue() {
        return chargeValue;
    }

    public void setChargeValue(Double chargeValue) {
        this.chargeValue = chargeValue;
    }

    public List<FinancialChargeDetail> getFinancialChargeDetailList() {
        return financialChargeDetailList;
    }

    public void setFinancialChargeDetailList(List<FinancialChargeDetail> financialChargeDetailList) {
        this.financialChargeDetailList = financialChargeDetailList;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

    public Integer getChargeSeq() {
        return chargeSeq;
    }

    public void setChargeSeq(Integer chargeSeq) {
        this.chargeSeq = chargeSeq;
    }

    public Integer getUnitSeq() {
        return unitSeq;
    }

    public void setUnitSeq(Integer unitSeq) {
        this.unitSeq = unitSeq;
    }
}
