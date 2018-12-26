package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.master.datalayer.modal.TaxType;
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
@Table(name = "expense_voucher_charge_detail_tax")
public class ExpenseVoucherChargeDetailTax {
    private Integer expenseVoucherChargeDetailTaxSeq;
    private Integer expenseVoucherChargeDetailSeq;
    private Integer taxRegistrationSeq;
    private Integer taxTypeSeq;
    private Double taxPercentage;
    private Double taxAmount;
    private Integer status;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private TaxRegistration taxRegistration;
    private TaxType taxType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "expense_voucher_charge_detail_tax_seq", allocationSize = 1)
    @Column(name = "expense_voucher_charge_detail_tax_seq", unique = true)
    public Integer getExpenseVoucherChargeDetailTaxSeq() {
        return expenseVoucherChargeDetailTaxSeq;
    }

    public void setExpenseVoucherChargeDetailTaxSeq(Integer expenseVoucherChargeDetailTaxSeq) {
        this.expenseVoucherChargeDetailTaxSeq = expenseVoucherChargeDetailTaxSeq;
    }

    @Basic
    @Column(name = "expense_voucher_charge_detail_seq", insertable = false, updatable = false)
    public Integer getExpenseVoucherChargeDetailSeq() {
        return expenseVoucherChargeDetailSeq;
    }

    public void setExpenseVoucherChargeDetailSeq(Integer expenseVoucherChargeDetailSeq) {
        this.expenseVoucherChargeDetailSeq = expenseVoucherChargeDetailSeq;
    }

    @Basic
    @Column(name = "tax_registration_seq")
    public Integer getTaxRegistrationSeq() {
        return taxRegistrationSeq;
    }

    public void setTaxRegistrationSeq(Integer taxRegistrationSeq) {
        this.taxRegistrationSeq = taxRegistrationSeq;
    }

    @Basic
    @Column(name = "tax_type_seq")
    public Integer getTaxTypeSeq() {
        return taxTypeSeq;
    }

    public void setTaxTypeSeq(Integer taxTypeSeq) {
        this.taxTypeSeq = taxTypeSeq;
    }

    @Basic
    @Column(name = "tax_percentage")
    public Double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @Basic
    @Column(name = "tax_amount")
    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
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
    @JoinColumn(name = "tax_registration_seq", insertable = false, updatable = false)
    public TaxRegistration getTaxRegistration() {
        return taxRegistration;
    }

    public void setTaxRegistration(TaxRegistration taxRegistration) {
        this.taxRegistration = taxRegistration;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_type_seq", insertable = false, updatable = false)
    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseVoucherChargeDetailTax that = (ExpenseVoucherChargeDetailTax) o;
        return Objects.equals(expenseVoucherChargeDetailTaxSeq, that.expenseVoucherChargeDetailTaxSeq) &&
                Objects.equals(expenseVoucherChargeDetailSeq, that.expenseVoucherChargeDetailSeq) &&
                Objects.equals(taxRegistrationSeq, that.taxRegistrationSeq) &&
                Objects.equals(taxTypeSeq, that.taxTypeSeq) &&
                Objects.equals(taxPercentage, that.taxPercentage) &&
                Objects.equals(taxAmount, that.taxAmount) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseVoucherChargeDetailTaxSeq, expenseVoucherChargeDetailSeq, taxRegistrationSeq, taxTypeSeq, taxPercentage, taxAmount, status);
    }
}
