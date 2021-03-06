package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.master.datalayer.modal.Charge;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bulk_invoice_charge_detail")
public class BulkInvoiceChargeDetail {
    private Integer bulkInvoiceChargeDetailSeq;
    private Integer bulkInvoiceSeq;
    private Double amount;
    private Integer chargeSeq;
    private Double chargeValue;
    private Double quantity;
    private Integer unitSeq;
    private Integer status;
    private Integer currencySeq;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private Charge charge;

    private List<BulkInvoiceChargeDetailTax> bulkInvoiceChargeDetailTaxList;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "bulk_Invoice_charge_detail_seq", allocationSize = 1)
    @Column(name = "bulk_Invoice_charge_detail_seq", unique = true)
    public Integer getBulkInvoiceChargeDetailSeq() {
        return bulkInvoiceChargeDetailSeq;
    }

    public void setBulkInvoiceChargeDetailSeq(Integer bulkInvoiceChargeDetailSeq) {
        this.bulkInvoiceChargeDetailSeq = bulkInvoiceChargeDetailSeq;
    }

    @Basic
    @Column(name = "bulk_invoice_seq", nullable = false, insertable = false, updatable = false)
    public Integer getBulkInvoiceSeq() {
        return bulkInvoiceSeq;
    }

    public void setBulkInvoiceSeq(Integer bulkInvoiceSeq) {
        this.bulkInvoiceSeq = bulkInvoiceSeq;
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
    @Column(name = "charge_seq")
    public Integer getChargeSeq() {
        return chargeSeq;
    }

    public void setChargeSeq(Integer chargeSeq) {
        this.chargeSeq = chargeSeq;
    }

    @Basic
    @Column(name = "charge_value")
    public Double getChargeValue() {
        return chargeValue;
    }

    public void setChargeValue(Double chargeValue) {
        this.chargeValue = chargeValue;
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
    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @OrderBy(value = "taxTypeSeq")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "bulk_invoice_charge_detail_seq", nullable = false)
    public List<BulkInvoiceChargeDetailTax> getBulkInvoiceChargeDetailTaxList() {
        return bulkInvoiceChargeDetailTaxList;
    }

    public void setBulkInvoiceChargeDetailTaxList(List<BulkInvoiceChargeDetailTax> bulkInvoiceChargeDetailTaxList) {
        this.bulkInvoiceChargeDetailTaxList = bulkInvoiceChargeDetailTaxList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulkInvoiceChargeDetail that = (BulkInvoiceChargeDetail) o;
        return Objects.equals(bulkInvoiceChargeDetailSeq, that.bulkInvoiceChargeDetailSeq) &&
                Objects.equals(bulkInvoiceSeq, that.bulkInvoiceSeq) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(chargeSeq, that.chargeSeq) &&
                Objects.equals(chargeValue, that.chargeValue) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(unitSeq, that.unitSeq) &&
                Objects.equals(status, that.status) &&
                Objects.equals(currencySeq, that.currencySeq) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(charge, that.charge) &&
                Objects.equals(bulkInvoiceChargeDetailTaxList, that.bulkInvoiceChargeDetailTaxList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bulkInvoiceChargeDetailSeq, bulkInvoiceSeq, amount, chargeSeq, chargeValue, quantity, unitSeq, status, currencySeq, createdBy, createdDate, lastModifiedBy, lastModifiedDate, charge, bulkInvoiceChargeDetailTaxList);
    }
}
