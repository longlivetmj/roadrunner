package com.tmj.tms.fleet.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Item;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
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
        @NamedEntityGraph(name = "ServiceAndMaintenanceLine.default", attributeNodes = {
                @NamedAttributeNode("currency"),
                @NamedAttributeNode("unit"),
                @NamedAttributeNode("item"),
                @NamedAttributeNode("supplier")
        })
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "service_and_maintenance_line")
public class ServiceAndMaintenanceLine {

    private Integer serviceAndMaintenanceLineSeq;
    private Integer serviceAndMaintenanceSeq;
    private Integer supplierSeq;

    private Integer itemSeq;
    private Integer currencySeq;
    private Integer unitSeq;
    private Double quantity;
    private Double unitPrice;
    private Double amount;
    private Integer kmExpiration;
    private Integer durationExpiration;
    private String remarks;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private Currency currency;
    private Unit unit;
    private Item item;
    private Stakeholder supplier;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "service_and_maintenance_line_seq", allocationSize = 1)
    @Column(name = "service_and_maintenance_line_seq", nullable = false, unique = true)
    public Integer getServiceAndMaintenanceLineSeq() {
        return serviceAndMaintenanceLineSeq;
    }

    public void setServiceAndMaintenanceLineSeq(Integer serviceAndMaintenanceLineSeq) {
        this.serviceAndMaintenanceLineSeq = serviceAndMaintenanceLineSeq;
    }

    @Basic
    @Column(name = "service_and_maintenance_seq", insertable = false, updatable = false)
    public Integer getServiceAndMaintenanceSeq() {
        return serviceAndMaintenanceSeq;
    }

    public void setServiceAndMaintenanceSeq(Integer serviceAndMaintenanceSeq) {
        this.serviceAndMaintenanceSeq = serviceAndMaintenanceSeq;
    }

    @Basic
    @Column(name = "supplier_seq", nullable = false)
    public Integer getSupplierSeq() {
        return supplierSeq;
    }

    public void setSupplierSeq(Integer supplierSeq) {
        this.supplierSeq = supplierSeq;
    }

    @Basic
    @Column(name = "item_seq", nullable = false)
    public Integer getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(Integer itemSeq) {
        this.itemSeq = itemSeq;
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
    @Column(name = "unit_seq", nullable = false)
    public Integer getUnitSeq() {
        return unitSeq;
    }

    public void setUnitSeq(Integer unitSeq) {
        this.unitSeq = unitSeq;
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
    @Column(name = "unit_price", nullable = false)
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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
    @Column(name = "km_expiration")
    public Integer getKmExpiration() {
        return kmExpiration;
    }

    public void setKmExpiration(Integer kmExpiration) {
        this.kmExpiration = kmExpiration;
    }

    @Basic
    @Column(name = "duration_expiration")
    public Integer getDurationExpiration() {
        return durationExpiration;
    }

    public void setDurationExpiration(Integer durationExpiration) {
        this.durationExpiration = durationExpiration;
    }

    @Basic
    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
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
    @Column(name = "last_modified_by", nullable = false)
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

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @JoinColumn(name = "item_seq", insertable = false, updatable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getSupplier() {
        return supplier;
    }

    public void setSupplier(Stakeholder supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceAndMaintenanceLine that = (ServiceAndMaintenanceLine) o;
        return Objects.equals(serviceAndMaintenanceLineSeq, that.serviceAndMaintenanceLineSeq) &&
                Objects.equals(serviceAndMaintenanceSeq, that.serviceAndMaintenanceSeq) &&
                Objects.equals(supplierSeq, that.supplierSeq) &&
                Objects.equals(itemSeq, that.itemSeq) &&
                Objects.equals(currencySeq, that.currencySeq) &&
                Objects.equals(unitSeq, that.unitSeq) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(unitPrice, that.unitPrice) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(kmExpiration, that.kmExpiration) &&
                Objects.equals(durationExpiration, that.durationExpiration) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceAndMaintenanceLineSeq, serviceAndMaintenanceSeq, supplierSeq, itemSeq, currencySeq, unitSeq, quantity, unitPrice, amount, kmExpiration, durationExpiration, remarks, status);
    }
}
