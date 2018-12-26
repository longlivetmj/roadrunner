package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.transport.utility.DateFilterType;
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
@Table(name = "bulk_invoice")
public class BulkInvoice {
    private Integer bulkInvoiceSeq;
    private String bulkInvoiceNo;
    private Integer moduleSeq;
    private Integer exchangeRateSeq;
    private Integer currencySeq;
    private Integer stakeholderSeq;
    private Integer shipperSeq;
    private Integer vehicleSeq;
    private Integer dateFilterType;
    private Date startDate;
    private Date endDate;
    private Integer departmentSeq;
    private String remark;
    private Integer companyProfileSeq;
    private Double finalWithoutTaxAmount;
    private Double finalOtherTaxAmount;
    private Double finalVatAmount;
    private Double finalTotalAmount;
    private Integer status;
    private String amountInWord;
    private Integer vatOrSVat;
    private Integer targetType;
    private Integer financeIntegration;
    private Long financeIntegrationKey;

    private List<BulkInvoiceLocation> bulkInvoiceLocationList;

    private List<BulkInvoiceDetail> bulkInvoiceDetailList;
    private List<BulkInvoiceChargeDetail> bulkInvoiceChargeDetailList;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private Module module;
    private ExchangeRate exchangeRate;
    private Currency currency;
    private Stakeholder stakeholder;
    private Stakeholder shipper;
    private Department department;
    private Vehicle vehicle;

    private String statusDescription;
    private String dateFilterTypeDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "bulk_invoice_seq", allocationSize = 1)
    @Column(name = "bulk_invoice_seq", unique = true)
    public Integer getBulkInvoiceSeq() {
        return bulkInvoiceSeq;
    }

    public void setBulkInvoiceSeq(Integer bulkInvoiceSeq) {
        this.bulkInvoiceSeq = bulkInvoiceSeq;
    }

    @Basic
    @Column(name = "bulk_invoice_no", nullable = false)
    public String getBulkInvoiceNo() {
        return bulkInvoiceNo;
    }

    public void setBulkInvoiceNo(String bulkInvoiceNo) {
        this.bulkInvoiceNo = bulkInvoiceNo;
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
    @Column(name = "exchange_rate_seq", nullable = false)
    public Integer getExchangeRateSeq() {
        return exchangeRateSeq;
    }

    public void setExchangeRateSeq(Integer exchangeRateSeq) {
        this.exchangeRateSeq = exchangeRateSeq;
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
    @Column(name = "stakeholder_seq")
    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
    }

    @Basic
    @Column(name = "shipper_seq")
    public Integer getShipperSeq() {
        return shipperSeq;
    }

    public void setShipperSeq(Integer shipperSeq) {
        this.shipperSeq = shipperSeq;
    }

    @Basic
    @Column(name = "vehicle_seq")
    public Integer getVehicleSeq() {
        return vehicleSeq;
    }

    public void setVehicleSeq(Integer vehicleSeq) {
        this.vehicleSeq = vehicleSeq;
    }

    @Basic
    @Column(name = "date_filter_type")
    public Integer getDateFilterType() {
        return dateFilterType;
    }

    public void setDateFilterType(Integer dateFilterType) {
        this.dateFilterType = dateFilterType;
        if (dateFilterType != null) {
            this.setDateFilterTypeDescription(DateFilterType.findOne(dateFilterType).getDateFilterTypeDescription());
        }
    }

    @Basic
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "remark", length = 512)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "company_profile_seq")
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "final_without_tax_amount", nullable = false)
    public Double getFinalWithoutTaxAmount() {
        return finalWithoutTaxAmount;
    }

    public void setFinalWithoutTaxAmount(Double finalWithoutTaxAmount) {
        this.finalWithoutTaxAmount = finalWithoutTaxAmount;
    }

    @Basic
    @Column(name = "final_other_tax_amount", nullable = false)
    public Double getFinalOtherTaxAmount() {
        return finalOtherTaxAmount;
    }

    public void setFinalOtherTaxAmount(Double finalOtherTaxAmount) {
        this.finalOtherTaxAmount = finalOtherTaxAmount;
    }

    @Basic
    @Column(name = "final_vat_amount", nullable = false)
    public Double getFinalVatAmount() {
        return finalVatAmount;
    }

    public void setFinalVatAmount(Double finalVatAmount) {
        this.finalVatAmount = finalVatAmount;
    }


    @Basic
    @Column(name = "final_total_amount", nullable = false)
    public Double getFinalTotalAmount() {
        return finalTotalAmount;
    }

    public void setFinalTotalAmount(Double finalTotalAmount) {
        this.finalTotalAmount = finalTotalAmount;
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
    @Column(name = "target_type", nullable = false)
    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    @Basic
    @Column(name = "status")
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

    @Basic
    @Column(name = "amount_in_word")
    public String getAmountInWord() {
        return amountInWord;
    }

    public void setAmountInWord(String amountInWord) {
        this.amountInWord = amountInWord;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "module_seq", insertable = false, updatable = false)
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_rate_seq", insertable = false, updatable = false)
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_seq", insertable = false, updatable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "vat_or_svat")
    public Integer getVatOrSVat() {
        return vatOrSVat;
    }

    public void setVatOrSVat(Integer vatOrSVat) {
        this.vatOrSVat = vatOrSVat;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(Stakeholder stakeholder) {
        this.stakeholder = stakeholder;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_seq", insertable = false, updatable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "bulk_invoice_seq", nullable = false)
    public List<BulkInvoiceDetail> getBulkInvoiceDetailList() {
        return bulkInvoiceDetailList;
    }

    public void setBulkInvoiceDetailList(List<BulkInvoiceDetail> bulkInvoiceDetailList) {
        this.bulkInvoiceDetailList = bulkInvoiceDetailList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "bulk_invoice_seq", nullable = false)
    public List<BulkInvoiceLocation> getBulkInvoiceLocationList() {
        return bulkInvoiceLocationList;
    }

    public void setBulkInvoiceLocationList(List<BulkInvoiceLocation> bulkInvoiceLocationList) {
        this.bulkInvoiceLocationList = bulkInvoiceLocationList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "bulk_invoice_seq", nullable = false)
    public List<BulkInvoiceChargeDetail> getBulkInvoiceChargeDetailList() {
        return bulkInvoiceChargeDetailList;
    }

    public void setBulkInvoiceChargeDetailList(List<BulkInvoiceChargeDetail> bulkInvoiceChargeDetailList) {
        this.bulkInvoiceChargeDetailList = bulkInvoiceChargeDetailList;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_seq", insertable = false, updatable = false)
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "shipper_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getShipper() {
        return shipper;
    }

    public void setShipper(Stakeholder shipper) {
        this.shipper = shipper;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Transient
    public String getDateFilterTypeDescription() {
        return dateFilterTypeDescription;
    }

    public void setDateFilterTypeDescription(String dateFilterTypeDescription) {
        this.dateFilterTypeDescription = dateFilterTypeDescription;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulkInvoice that = (BulkInvoice) o;
        return Objects.equals(bulkInvoiceSeq, that.bulkInvoiceSeq) &&
                Objects.equals(bulkInvoiceNo, that.bulkInvoiceNo) &&
                Objects.equals(moduleSeq, that.moduleSeq) &&
                Objects.equals(exchangeRateSeq, that.exchangeRateSeq) &&
                Objects.equals(currencySeq, that.currencySeq) &&
                Objects.equals(stakeholderSeq, that.stakeholderSeq) &&
                Objects.equals(shipperSeq, that.shipperSeq) &&
                Objects.equals(vehicleSeq, that.vehicleSeq) &&
                Objects.equals(dateFilterType, that.dateFilterType) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(departmentSeq, that.departmentSeq) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(finalWithoutTaxAmount, that.finalWithoutTaxAmount) &&
                Objects.equals(finalOtherTaxAmount, that.finalOtherTaxAmount) &&
                Objects.equals(finalVatAmount, that.finalVatAmount) &&
                Objects.equals(finalTotalAmount, that.finalTotalAmount) &&
                Objects.equals(status, that.status) &&
                Objects.equals(amountInWord, that.amountInWord) &&
                Objects.equals(vatOrSVat, that.vatOrSVat) &&
                Objects.equals(targetType, that.targetType) &&
                Objects.equals(bulkInvoiceDetailList, that.bulkInvoiceDetailList) &&
                Objects.equals(bulkInvoiceChargeDetailList, that.bulkInvoiceChargeDetailList) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(module, that.module) &&
                Objects.equals(exchangeRate, that.exchangeRate) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(stakeholder, that.stakeholder) &&
                Objects.equals(department, that.department) &&
                Objects.equals(statusDescription, that.statusDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bulkInvoiceSeq, bulkInvoiceNo, moduleSeq, exchangeRateSeq, currencySeq, stakeholderSeq, shipperSeq, vehicleSeq, dateFilterType, startDate, endDate, departmentSeq, remark, companyProfileSeq, finalWithoutTaxAmount, finalOtherTaxAmount, finalVatAmount, finalTotalAmount, status, amountInWord, vatOrSVat, targetType, bulkInvoiceDetailList, bulkInvoiceChargeDetailList, createdBy, createdDate, lastModifiedBy, lastModifiedDate, module, exchangeRate, currency, stakeholder, department, statusDescription);
    }
}
