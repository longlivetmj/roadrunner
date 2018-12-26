package com.tmj.tms.config.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.finance.utility.ReferenceType;
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

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "DepartmentCharge.default", attributeNodes = {
                @NamedAttributeNode("currency"),
                @NamedAttributeNode("charge"),
                @NamedAttributeNode("unit"),
                @NamedAttributeNode("module"),
                @NamedAttributeNode("department")
        })
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "department_charge")
public class DepartmentCharge {
    private Integer departmentChargeSeq;
    private Integer chargeSeq;
    private Integer moduleSeq;
    private Integer departmentSeq;
    private Integer chargeType;
    private Integer referenceType;
    private Integer currencySeq;
    private Integer unitSeq;
    private Integer quantity;
    private Double amount;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;
    private Integer defaultOrder;

    private String chargeTypeDescription;
    private String referenceTypeDescription;
    private String statusDescription;

    private Currency currency;
    private Charge charge;
    private Unit unit;
    private Module module;
    private Department department;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "DEPARTMENT_CHARGE_SEQ", allocationSize = 1)
    @Column(name = "department_charge_seq", nullable = false, unique = true)
    public Integer getDepartmentChargeSeq() {
        return departmentChargeSeq;
    }

    public void setDepartmentChargeSeq(Integer departmentChargeSeq) {
        this.departmentChargeSeq = departmentChargeSeq;
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
    @Column(name = "module_seq", nullable = false)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Basic
    @Column(name = "department_seq", nullable = false)
    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
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
    @Column(name = "reference_type", nullable = false)
    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
        if (referenceType != null) {
            this.setReferenceTypeDescription(ReferenceType.findOne(referenceType).getReferenceTypeDescription());
        }
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        if (status != null) {
            this.setStatusDescription(MasterDataStatus.findOne(status).getStatus());
        }
    }

    @Basic
    @Column(name = "currency_seq")
    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
    }

    @Basic
    @Column(name = "unit_seq")
    public Integer getUnitSeq() {
        return unitSeq;
    }

    public void setUnitSeq(Integer unitSeq) {
        this.unitSeq = unitSeq;
    }

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", nullable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    @Column(name = "created_date", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    @Column(name = "last_modified_date", nullable = false)
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Basic
    @Column(name = "default_order", nullable = false)
    public Integer getDefaultOrder() {
        return defaultOrder;
    }

    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_seq", insertable = false, updatable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_seq", insertable = false, updatable = false)
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_seq", insertable = false, updatable = false)
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_seq", insertable = false, updatable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_seq", insertable = false, updatable = false)
    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    @Transient
    public String getChargeTypeDescription() {
        return chargeTypeDescription;
    }

    public void setChargeTypeDescription(String chargeTypeDescription) {
        this.chargeTypeDescription = chargeTypeDescription;
    }

    @Transient
    public String getReferenceTypeDescription() {
        return referenceTypeDescription;
    }

    public void setReferenceTypeDescription(String referenceTypeDescription) {
        this.referenceTypeDescription = referenceTypeDescription;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentCharge that = (DepartmentCharge) o;
        return Objects.equals(departmentChargeSeq, that.departmentChargeSeq) &&
                Objects.equals(chargeSeq, that.chargeSeq) &&
                Objects.equals(moduleSeq, that.moduleSeq) &&
                Objects.equals(departmentSeq, that.departmentSeq) &&
                Objects.equals(chargeType, that.chargeType) &&
                Objects.equals(referenceType, that.referenceType) &&
                Objects.equals(currencySeq, that.currencySeq) &&
                Objects.equals(unitSeq, that.unitSeq) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(defaultOrder, that.defaultOrder) &&
                Objects.equals(chargeTypeDescription, that.chargeTypeDescription) &&
                Objects.equals(referenceTypeDescription, that.referenceTypeDescription) &&
                Objects.equals(statusDescription, that.statusDescription) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(charge, that.charge) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(module, that.module) &&
                Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentChargeSeq, chargeSeq, moduleSeq, departmentSeq, chargeType, referenceType, currencySeq, unitSeq, quantity, amount, createdBy, createdDate, lastModifiedBy, lastModifiedDate, status, defaultOrder, chargeTypeDescription, referenceTypeDescription, statusDescription, currency, charge, unit, module, department);
    }
}
