package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Unit;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "financial_charge_detail")
public class FinancialChargeDetail {
    private Integer financialChargeDetailSeq;
    private Integer financialChargeSeq;
    private Integer chargeType;
    private Integer chargeSeq;
    private Double chargeValue;
    private Integer currencySeq;
    private Double quantity;
    private Double amount;
    private Integer unitSeq;
    private Integer evStatus;
    private Integer liStatus;
    private Integer lcnStatus;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private Charge charge;
    private Currency currency;
    private Unit unit;

    private String chargeTypeDescription;

    private Double dynamicRate;
    private String checkedStatus;
    private Double tempAmount;
    private String tempCurrencyCode;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "financial_charge_detail_seq", allocationSize = 1)
    @Column(name = "financial_charge_detail_seq", unique = true)
    public Integer getFinancialChargeDetailSeq() {
        return financialChargeDetailSeq;
    }

    public void setFinancialChargeDetailSeq(Integer financialChargeDetailSeq) {
        this.financialChargeDetailSeq = financialChargeDetailSeq;
    }

    @Column(name = "financial_charge_seq", nullable = false, insertable = false, updatable = false)
    public Integer getFinancialChargeSeq() {
        return financialChargeSeq;
    }

    public void setFinancialChargeSeq(Integer financialChargeSeq) {
        this.financialChargeSeq = financialChargeSeq;
    }

    @Basic
    @Column(name = "charge_seq", nullable = false)
    public Integer getChargeSeq() {
        return chargeSeq;
    }

    public void setChargeSeq(Integer chargeSeq) {
        this.chargeSeq = chargeSeq;
    }

    @Basic
    @Column(name = "charge_value", nullable = false)
    public Double getChargeValue() {
        return chargeValue;
    }

    public void setChargeValue(Double chargeValue) {
        this.chargeValue = chargeValue;
    }

    @Basic
    @Column(name = "charge_type", nullable = false)
    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
        if (chargeType != null) {
            this.setChargeTypeDescription(ChargeType.findOne(chargeType).getChargeTypeDiscription());
        }
    }

    @Basic
    @Column(name = "quantity", nullable = false)
    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "currency_seq", nullable = false)
    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
    }

    @Basic
    @Column(name = "amount", nullable = false)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "unit_seq", nullable = false)
    public Integer getUnitSeq() {
        return unitSeq;
    }

    public void setUnitSeq(Integer unitSeq) {
        this.unitSeq = unitSeq;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "ev_status")
    public Integer getEvStatus() {
        return evStatus;
    }

    public void setEvStatus(Integer evStatus) {
        this.evStatus = evStatus;
    }

    @Basic
    @Column(name = "li_status")
    public Integer getLiStatus() {
        return liStatus;
    }

    public void setLiStatus(Integer liStatus) {
        this.liStatus = liStatus;
    }

    @Basic
    @Column(name = "lcn_status")
    public Integer getLcnStatus() {
        return lcnStatus;
    }

    public void setLcnStatus(Integer lcnStatus) {
        this.lcnStatus = lcnStatus;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false, length = 50)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_seq", insertable = false, updatable = false)
    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_seq", insertable = false, updatable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_seq", insertable = false, updatable = false)
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Transient
    public String getChargeTypeDescription() {
        return chargeTypeDescription;
    }

    public void setChargeTypeDescription(String chargeTypeDescription) {
        this.chargeTypeDescription = chargeTypeDescription;
    }

    @Transient
    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

    @Transient
    public Double getTempAmount() {
        return tempAmount;
    }

    public void setTempAmount(Double tempAmount) {
        this.tempAmount = tempAmount;
    }

    @Transient
    public String getTempCurrencyCode() {
        return tempCurrencyCode;
    }

    public void setTempCurrencyCode(String tempCurrencyCode) {
        this.tempCurrencyCode = tempCurrencyCode;
    }

    @Transient
    public Double getDynamicRate() {
        return dynamicRate;
    }

    public void setDynamicRate(Double dynamicRate) {
        this.dynamicRate = dynamicRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialChargeDetail that = (FinancialChargeDetail) o;
        return Objects.equals(financialChargeDetailSeq, that.financialChargeDetailSeq) &&
                Objects.equals(financialChargeSeq, that.financialChargeSeq) &&
                Objects.equals(chargeType, that.chargeType) &&
                Objects.equals(chargeSeq, that.chargeSeq) &&
                Objects.equals(chargeValue, that.chargeValue) &&
                Objects.equals(currencySeq, that.currencySeq) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(unitSeq, that.unitSeq) &&
                Objects.equals(evStatus, that.evStatus) &&
                Objects.equals(liStatus, that.liStatus) &&
                Objects.equals(lcnStatus, that.lcnStatus) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(charge, that.charge) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(chargeTypeDescription, that.chargeTypeDescription) &&
                Objects.equals(dynamicRate, that.dynamicRate) &&
                Objects.equals(checkedStatus, that.checkedStatus) &&
                Objects.equals(tempAmount, that.tempAmount) &&
                Objects.equals(tempCurrencyCode, that.tempCurrencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(financialChargeDetailSeq, financialChargeSeq, chargeType, chargeSeq, chargeValue, currencySeq, quantity, amount, unitSeq, evStatus, liStatus, lcnStatus, status, createdBy, createdDate, lastModifiedBy, lastModifiedDate, charge, currency, unit, chargeTypeDescription, dynamicRate, checkedStatus, tempAmount, tempCurrencyCode);
    }
}
