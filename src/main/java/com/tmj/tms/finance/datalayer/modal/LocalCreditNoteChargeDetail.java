package com.tmj.tms.finance.datalayer.modal;

import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.modal.Currency;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "local_credit_note_charge_detail")
public class LocalCreditNoteChargeDetail {

    private Integer localCreditNoteChargeDetailSeq;
    private Integer localCreditNoteHeaderSeq;
    private Integer localInvoiceChargeDetailSeq;
    private Integer expenseVoucherChargeDetailSeq;
    private Integer financialChargeDetailSeq;
    private Integer financialChargeSeq;
    private Integer chargeSeq;
    private Integer currencySeq;
    private Double invoicedAmount;
    private Double currentAmount;
    private Double amountDifferential;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private LocalInvoiceChargeDetail localInvoiceChargeDetail;
    private ExpenseVoucherChargeDetail expenseVoucherChargeDetail;
    private Charge charge;
    private Currency currency;
    private FinancialCharge financialCharge;
    private FinancialChargeDetail financialChargeDetail;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "local_credit_note_charge_detail_seq", allocationSize = 1)
    @Column(name = "local_credit_note_charge_detail_seq", nullable = false, unique = true)
    public Integer getLocalCreditNoteChargeDetailSeq() {
        return localCreditNoteChargeDetailSeq;
    }

    public void setLocalCreditNoteChargeDetailSeq(Integer localCreditNoteChargeDetailSeq) {
        this.localCreditNoteChargeDetailSeq = localCreditNoteChargeDetailSeq;
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
    @Column(name = "local_invoice_charge_detail_seq")
    public Integer getLocalInvoiceChargeDetailSeq() {
        return localInvoiceChargeDetailSeq;
    }

    public void setLocalInvoiceChargeDetailSeq(Integer localInvoiceChargeDetailSeq) {
        this.localInvoiceChargeDetailSeq = localInvoiceChargeDetailSeq;
    }

    @Basic
    @Column(name = "expense_voucher_charge_detail_seq")
    public Integer getExpenseVoucherChargeDetailSeq() {
        return expenseVoucherChargeDetailSeq;
    }

    public void setExpenseVoucherChargeDetailSeq(Integer expenseVoucherChargeDetailSeq) {
        this.expenseVoucherChargeDetailSeq = expenseVoucherChargeDetailSeq;
    }

    @Basic
    @Column(name = "financial_charge_detail_seq", nullable = false)
    public Integer getFinancialChargeDetailSeq() {
        return financialChargeDetailSeq;
    }

    public void setFinancialChargeDetailSeq(Integer financialChargeDetailSeq) {
        this.financialChargeDetailSeq = financialChargeDetailSeq;
    }

    @Basic
    @Column(name = "financial_charge_seq", nullable = false)
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
    @Column(name = "currency_seq", nullable = false)
    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
    }

    @Basic
    @Column(name = "invoiced_amount", nullable = false)
    public Double getInvoicedAmount() {
        return invoicedAmount;
    }

    public void setInvoicedAmount(Double invoicedAmount) {
        this.invoicedAmount = invoicedAmount;
    }

    @Basic
    @Column(name = "current_amount", nullable = false)
    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    @Basic
    @Column(name = "amount_differential", nullable = false)
    public Double getAmountDifferential() {
        return amountDifferential;
    }

    public void setAmountDifferential(Double amountDifferential) {
        this.amountDifferential = amountDifferential;
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
    @JoinColumn(name = "expense_vou_fin_chr_map_seq", insertable = false, updatable = false)
    public ExpenseVoucherChargeDetail getExpenseVoucherChargeDetail() {
        return expenseVoucherChargeDetail;
    }

    public void setExpenseVoucherChargeDetail(ExpenseVoucherChargeDetail expenseVoucherChargeDetail) {
        this.expenseVoucherChargeDetail = expenseVoucherChargeDetail;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_invoice_fin_chr_map_seq", insertable = false, updatable = false)
    public LocalInvoiceChargeDetail getLocalInvoiceChargeDetail() {
        return localInvoiceChargeDetail;
    }

    public void setLocalInvoiceChargeDetail(LocalInvoiceChargeDetail localInvoiceChargeDetail) {
        this.localInvoiceChargeDetail = localInvoiceChargeDetail;
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
    @JoinColumn(name = "financial_charge_seq", insertable = false, updatable = false)
    public FinancialCharge getFinancialCharge() {
        return financialCharge;
    }

    public void setFinancialCharge(FinancialCharge financialCharge) {
        this.financialCharge = financialCharge;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_charge_detail_seq", insertable = false, updatable = false)
    public FinancialChargeDetail getFinancialChargeDetail() {
        return financialChargeDetail;
    }

    public void setFinancialChargeDetail(FinancialChargeDetail financialChargeDetail) {
        this.financialChargeDetail = financialChargeDetail;
    }


}
