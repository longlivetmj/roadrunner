package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmj.tms.config.utility.MasterDataStatus;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "local_credit_note_header")
public class LocalCreditNoteHeader {
    private Integer localCreditNoteHeaderSeq;
    private String localCreditNoteNo;
    private Integer entryType;
    private String remarks;
    private Integer moduleSeq;
    private Integer invoiceTypeSeq;
    private Integer localInvoiceSeq;
    private Integer expenseVoucherSeq;
    private Integer companyProfileSeq;
    private Integer functionalType;
    private Double balanceToBeCreditedAmount;
    private Integer financialChargeSeq;

    private Double totalInvoicedAmount;
    private Double totalCurrentAmount;
    private Double totalInvoicedNbtAmount;
    private Double totalCurrentNbtAmount;
    private Double totalInvoicedVatAmount;
    private Double totalCurrentVatAmount;
    private Double finalTotalInvoiceAmount;
    private Double finalTotalCurrentAmount;
    private Integer departmentSeq;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;
    private Integer sVatStatus;
    private Integer creditNoteCount;
    private Integer financeIntegration;
    private Long financeIntegrationKey;

    private String statusDescription;

    private List<LocalCreditNoteChargeDetail> localCreditNoteChargeDetailList;
    private List<LocalCreditNoteTaxRateMapping> localCreditNoteTaxRateMappingList;

    private LocalInvoice localInvoice;
    private ExpenseVoucher expenseVoucher;
    private FinancialCharge financialCharge;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "local_credit_note_header_seq", allocationSize = 1)
    @Column(name = "local_credit_note_header_seq", unique = true)
    public Integer getLocalCreditNoteHeaderSeq() {
        return localCreditNoteHeaderSeq;
    }

    public void setLocalCreditNoteHeaderSeq(Integer localCreditNoteHeaderSeq) {
        this.localCreditNoteHeaderSeq = localCreditNoteHeaderSeq;
    }

    @Basic
    @Column(name = "entry_type", nullable = false)
    public Integer getEntryType() {
        return entryType;
    }

    public void setEntryType(Integer entryType) {
        this.entryType = entryType;
    }

    @Basic
    @Column(name = "local_credit_note_no", nullable = false)
    public String getLocalCreditNoteNo() {
        return localCreditNoteNo;
    }

    public void setLocalCreditNoteNo(String localCreditNoteNo) {
        this.localCreditNoteNo = localCreditNoteNo;
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
    @Column(name = "module_seq", nullable = false)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Basic
    @Column(name = "invoice_type_seq", nullable = false)
    public Integer getInvoiceTypeSeq() {
        return invoiceTypeSeq;
    }

    public void setInvoiceTypeSeq(Integer invoiceTypeSeq) {
        this.invoiceTypeSeq = invoiceTypeSeq;
    }

    @Basic
    @Column(name = "local_invoice_seq")
    public Integer getLocalInvoiceSeq() {
        return localInvoiceSeq;
    }

    public void setLocalInvoiceSeq(Integer localInvoiceSeq) {
        this.localInvoiceSeq = localInvoiceSeq;
    }

    @Basic
    @Column(name = "expense_voucher_seq")
    public Integer getExpenseVoucherSeq() {
        return expenseVoucherSeq;
    }

    public void setExpenseVoucherSeq(Integer expenseVoucherSeq) {
        this.expenseVoucherSeq = expenseVoucherSeq;
    }

    @Basic
    @Column(name = "company_profile_seq", nullable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "functional_type")
    public Integer getFunctionalType() {
        return functionalType;
    }

    public void setFunctionalType(Integer functionalType) {
        this.functionalType = functionalType;
    }

    @Basic
    @Column(name = "balance_to_be_credited_amount")
    public Double getBalanceToBeCreditedAmount() {
        return balanceToBeCreditedAmount;
    }

    public void setBalanceToBeCreditedAmount(Double balanceToBeCreditedAmount) {
        this.balanceToBeCreditedAmount = balanceToBeCreditedAmount;
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
    @Column(name = "total_invoiced_amount")
    public Double getTotalInvoicedAmount() {
        return totalInvoicedAmount;
    }

    public void setTotalInvoicedAmount(Double totalInvoicedAmount) {
        this.totalInvoicedAmount = totalInvoicedAmount;
    }

    @Basic
    @Column(name = "total_current_amount")
    public Double getTotalCurrentAmount() {
        return totalCurrentAmount;
    }

    public void setTotalCurrentAmount(Double totalCurrentAmount) {
        this.totalCurrentAmount = totalCurrentAmount;
    }

    @Basic
    @Column(name = "total_invoiced_nbt_amount")
    public Double getTotalInvoicedNbtAmount() {
        return totalInvoicedNbtAmount;
    }

    public void setTotalInvoicedNbtAmount(Double totalInvoicedNbtAmount) {
        this.totalInvoicedNbtAmount = totalInvoicedNbtAmount;
    }

    @Basic
    @Column(name = "total_current_nbt_amount")
    public Double getTotalCurrentNbtAmount() {
        return totalCurrentNbtAmount;
    }

    public void setTotalCurrentNbtAmount(Double totalCurrentNbtAmount) {
        this.totalCurrentNbtAmount = totalCurrentNbtAmount;
    }

    @Basic
    @Column(name = "total_invoiced_vat_amount")
    public Double getTotalInvoicedVatAmount() {
        return totalInvoicedVatAmount;
    }

    public void setTotalInvoicedVatAmount(Double totalInvoicedVatAmount) {
        this.totalInvoicedVatAmount = totalInvoicedVatAmount;
    }

    @Basic
    @Column(name = "total_current_vat_amount")
    public Double getTotalCurrentVatAmount() {
        return totalCurrentVatAmount;
    }

    public void setTotalCurrentVatAmount(Double totalCurrentVatAmount) {
        this.totalCurrentVatAmount = totalCurrentVatAmount;
    }


    @Basic
    @Column(name = "final_total_invoice_amount")
    public Double getFinalTotalInvoiceAmount() {
        return finalTotalInvoiceAmount;
    }

    public void setFinalTotalInvoiceAmount(Double finalTotalInvoiceAmount) {
        this.finalTotalInvoiceAmount = finalTotalInvoiceAmount;
    }

    @Basic
    @Column(name = "final_total_current_amount")
    public Double getFinalTotalCurrentAmount() {
        return finalTotalCurrentAmount;
    }

    public void setFinalTotalCurrentAmount(Double finalTotalCurrentAmount) {
        this.finalTotalCurrentAmount = finalTotalCurrentAmount;
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
    @Column(name = "status", nullable = true, precision = 0)
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
    @Column(name = "svat_status")
    public Integer getsVatStatus() {
        return sVatStatus;
    }

    public void setsVatStatus(Integer sVatStatus) {
        this.sVatStatus = sVatStatus;
    }

    @Basic
    @Column(name = "credit_note_count")
    public Integer getCreditNoteCount() {
        return creditNoteCount;
    }

    public void setCreditNoteCount(Integer creditNoteCount) {
        this.creditNoteCount = creditNoteCount;
    }

    @Basic
    @Column(name = "finance_integration")
    public Integer getFinanceIntegration() {
        return financeIntegration;
    }

    public void setFinanceIntegration(Integer financeIntegration) {
        this.financeIntegration = financeIntegration;
    }

    @Basic
    @Column(name = "finance_integration_key")
    public Long getFinanceIntegrationKey() {
        return financeIntegrationKey;
    }

    public void setFinanceIntegrationKey(Long financeIntegrationKey) {
        this.financeIntegrationKey = financeIntegrationKey;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @OneToOne
    @JoinColumn(name = "local_invoice_seq", insertable = false, updatable = false)
    @JsonIgnore
    public LocalInvoice getLocalInvoice() {
        return localInvoice;
    }

    public void setLocalInvoice(LocalInvoice localInvoice) {
        this.localInvoice = localInvoice;
    }

    @OneToOne
    @JoinColumn(name = "expense_voucher_seq", insertable = false, updatable = false)
    @JsonIgnore
    public ExpenseVoucher getExpenseVoucher() {
        return expenseVoucher;
    }

    public void setExpenseVoucher(ExpenseVoucher expenseVoucher) {
        this.expenseVoucher = expenseVoucher;
    }

    @OneToOne
    @JoinColumn(name = "financial_charge_seq", insertable = false, updatable = false)
    @JsonIgnore
    public FinancialCharge getFinancialCharge() {
        return financialCharge;
    }

    public void setFinancialCharge(FinancialCharge financialCharge) {
        this.financialCharge = financialCharge;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "local_credit_note_header_seq", nullable = false)
    @OrderBy("localCreditNoteChargeDetailSeq ASC")
    @Where(clause = "STATUS != 0")
    public List<LocalCreditNoteChargeDetail> getLocalCreditNoteChargeDetailList() {
        return localCreditNoteChargeDetailList;
    }

    public void setLocalCreditNoteChargeDetailList(List<LocalCreditNoteChargeDetail> localCreditNoteChargeDetailList) {
        this.localCreditNoteChargeDetailList = localCreditNoteChargeDetailList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "local_credit_note_header_seq", nullable = false)
    @OrderBy("localCreditNoteTaxRateMappingSeq ASC")
    @Where(clause = "STATUS != 0")
    public List<LocalCreditNoteTaxRateMapping> getLocalCreditNoteTaxRateMappingList() {
        return localCreditNoteTaxRateMappingList;
    }

    public void setLocalCreditNoteTaxRateMappingList(List<LocalCreditNoteTaxRateMapping> localCreditNoteTaxRateMappingList) {
        this.localCreditNoteTaxRateMappingList = localCreditNoteTaxRateMappingList;
    }


}
