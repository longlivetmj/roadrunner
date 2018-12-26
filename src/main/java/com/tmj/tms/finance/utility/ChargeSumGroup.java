package com.tmj.tms.finance.utility;

import java.util.Objects;

public class ChargeSumGroup {

    private Integer chargeSeq;
    private Integer unitSeq;

    public ChargeSumGroup(Integer chargeSeq, Integer unitSeq) {
        this.chargeSeq = chargeSeq;
        this.unitSeq = unitSeq;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargeSumGroup that = (ChargeSumGroup) o;
        return Objects.equals(chargeSeq, that.chargeSeq) &&
                Objects.equals(unitSeq, that.unitSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chargeSeq, unitSeq);
    }
}
