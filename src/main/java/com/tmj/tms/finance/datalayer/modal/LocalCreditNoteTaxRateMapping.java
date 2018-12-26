package com.tmj.tms.finance.datalayer.modal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "local_credit_note_tax_rate_mapping")
public class LocalCreditNoteTaxRateMapping {
    private Integer localCreditNoteTaxRateMappingSeq;
    private Integer localCreditNoteHeaderSeq;
    private Integer taxRegistrationVatSeq;
    private Integer taxRegistrationOtherTaxSeq;
    private Integer taxTypeVatSeq;
    private Integer taxTypeOtherTaxSeq;
    private Double taxVatRate;
    private Double taxOtherRate;
    private Integer financialChargeSeq;
    private Integer financialChargeDetailSeq;
    private Integer localInvTaxRateMappingSeq;
    private Integer expenseVouTaxRateMapSeq;
    private Double vatDifference;
    private Double nbtDifference;
    private Double vatAmount;
    private Double otherTaxAmount;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "local_credit_note_tax_rate_mapping_seq", allocationSize = 1)
    @Column(name = "local_credit_note_tax_rate_mapping_seq", unique = true)
    public Integer getLocalCreditNoteTaxRateMappingSeq() {
        return localCreditNoteTaxRateMappingSeq;
    }

    public void setLocalCreditNoteTaxRateMappingSeq(Integer localCreditNoteTaxRateMappingSeq) {
        this.localCreditNoteTaxRateMappingSeq = localCreditNoteTaxRateMappingSeq;
    }

    @Basic
    @Column(name = "local_credit_note_header_seq", nullable = false, insertable = false, updatable = false)
    public Integer getLocalCreditNoteHeaderSeq() {
        return localCreditNoteHeaderSeq;
    }

    public void setLocalCreditNoteHeaderSeq(Integer localCreditNoteHeaderSeq) {
        this.localCreditNoteHeaderSeq = localCreditNoteHeaderSeq;
    }

    @Basic
    @Column(name = "tax_registration_vat_seq")
    public Integer getTaxRegistrationVatSeq() {
        return taxRegistrationVatSeq;
    }

    public void setTaxRegistrationVatSeq(Integer taxRegistrationVatSeq) {
        this.taxRegistrationVatSeq = taxRegistrationVatSeq;
    }

    @Basic
    @Column(name = "tax_registration_other_tax_seq")
    public Integer getTaxRegistrationOtherTaxSeq() {
        return taxRegistrationOtherTaxSeq;
    }

    public void setTaxRegistrationOtherTaxSeq(Integer taxRegistrationOtherTaxSeq) {
        this.taxRegistrationOtherTaxSeq = taxRegistrationOtherTaxSeq;
    }

    @Basic
    @Column(name = "tax_type_vat_seq")
    public Integer getTaxTypeVatSeq() {
        return taxTypeVatSeq;
    }

    public void setTaxTypeVatSeq(Integer taxTypeVatSeq) {
        this.taxTypeVatSeq = taxTypeVatSeq;
    }

    @Basic
    @Column(name = "tax_type_other_tax_seq")
    public Integer getTaxTypeOtherTaxSeq() {
        return taxTypeOtherTaxSeq;
    }

    public void setTaxTypeOtherTaxSeq(Integer taxTypeOtherTaxSeq) {
        this.taxTypeOtherTaxSeq = taxTypeOtherTaxSeq;
    }

    @Basic
    @Column(name = "tax_vat_rate")
    public Double getTaxVatRate() {
        return taxVatRate;
    }

    public void setTaxVatRate(Double taxVatRate) {
        this.taxVatRate = taxVatRate;
    }

    @Basic
    @Column(name = "tax_other_rate")
    public Double getTaxOtherRate() {
        return taxOtherRate;
    }

    public void setTaxOtherRate(Double taxOtherRate) {
        this.taxOtherRate = taxOtherRate;
    }

    @Basic
    @Column(name = "financial_charge_seq")
    public Integer getFinancialChargeSeq() {
        return financialChargeSeq;
    }

    public void setFinancialChargeSeq(Integer financialChargeSeq) {
        this.financialChargeSeq = financialChargeSeq;
    }

    @Basic
    @Column(name = "financial_charge_detail_seq")
    public Integer getFinancialChargeDetailSeq() {
        return financialChargeDetailSeq;
    }

    public void setFinancialChargeDetailSeq(Integer financialChargeDetailSeq) {
        this.financialChargeDetailSeq = financialChargeDetailSeq;
    }

    @Basic
    @Column(name = "local_inv_tax_rate_mapping_seq")
    public Integer getLocalInvTaxRateMappingSeq() {
        return localInvTaxRateMappingSeq;
    }

    public void setLocalInvTaxRateMappingSeq(Integer localInvTaxRateMappingSeq) {
        this.localInvTaxRateMappingSeq = localInvTaxRateMappingSeq;
    }

    @Basic
    @Column(name = "expense_vou_tax_rate_map_seq")
    public Integer getExpenseVouTaxRateMapSeq() {
        return expenseVouTaxRateMapSeq;
    }

    public void setExpenseVouTaxRateMapSeq(Integer expenseVouTaxRateMapSeq) {
        this.expenseVouTaxRateMapSeq = expenseVouTaxRateMapSeq;
    }

    @Basic
    @Column(name = "vat_difference")
    public Double getVatDifference() {
        return vatDifference;
    }

    public void setVatDifference(Double vatDifference) {
        this.vatDifference = vatDifference;
    }

    @Basic
    @Column(name = "nbt_difference")
    public Double getNbtDifference() {
        return nbtDifference;
    }

    public void setNbtDifference(Double nbtDifference) {
        this.nbtDifference = nbtDifference;
    }

    @Basic
    @Column(name = "vat_amount")
    public Double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    @Basic
    @Column(name = "other_tax_amount")
    public Double getOtherTaxAmount() {
        return otherTaxAmount;
    }

    public void setOtherTaxAmount(Double otherTaxAmount) {
        this.otherTaxAmount = otherTaxAmount;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @CreatedDate
    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "last_modified_by")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "last_modified_date")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
