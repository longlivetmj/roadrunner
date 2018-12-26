package com.tmj.tms.transport.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transport_bulk_booking")
public class TransportBulkBooking {

    private Integer transportBulkBookingSeq;

    @NotNull
    private Integer companyProfileSeq;

    @NotNull
    private Integer departmentSeq;

    @NotNull
    private Integer moduleSeq;

    @NotNull
    private Integer customerSeq;

    @NotNull
    private Integer invoiceCustomerSeq;

    @NotNull
    private Integer shipperSeq;

    private Integer uowSeq;

    private Integer uovSeq;

    @NotNull
    private Integer invoiceStatus;

    private Integer packageTypeSeq;

    @NotNull
    private Integer paymentMode;

    @NotNull
    private Integer cashOrCredit;

    private String createdBy;


    private Integer currentStatus;

    private String processOutPut;

    private Date createdDate;

    private String remarks;

    private Integer uploadDocumentSeq;

    private Stakeholder customer;
    private Stakeholder shipper;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "transport_bulk_booking_seq", allocationSize = 1)
    @Column(name = "transport_bulk_booking_seq", nullable = false, unique = true)
    public Integer getTransportBulkBookingSeq() {
        return transportBulkBookingSeq;
    }

    public void setTransportBulkBookingSeq(Integer transportBulkBookingSeq) {
        this.transportBulkBookingSeq = transportBulkBookingSeq;
    }

    @Basic
    @Column(name = "company_profile_seq", nullable = false, updatable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
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
    @Column(name = "module_seq", nullable = false, updatable = false)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Basic
    @Column(name = "customer_seq", nullable = false)
    public Integer getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(Integer customerSeq) {
        this.customerSeq = customerSeq;
    }

    @Basic
    @Column(name = "invoice_customer_seq", nullable = false)
    public Integer getInvoiceCustomerSeq() {
        return invoiceCustomerSeq;
    }

    public void setInvoiceCustomerSeq(Integer invoiceCustomerSeq) {
        this.invoiceCustomerSeq = invoiceCustomerSeq;
    }

    @Basic
    @Column(name = "shipper_seq", nullable = false)
    public Integer getShipperSeq() {
        return shipperSeq;
    }

    public void setShipperSeq(Integer shipperSeq) {
        this.shipperSeq = shipperSeq;
    }

    @Basic
    @Column(name = "uow_seq")
    public Integer getUowSeq() {
        return uowSeq;
    }

    public void setUowSeq(Integer uowSeq) {
        this.uowSeq = uowSeq;
    }

    @Basic
    @Column(name = "uov_seq")
    public Integer getUovSeq() {
        return uovSeq;
    }

    public void setUovSeq(Integer uovSeq) {
        this.uovSeq = uovSeq;
    }

    @Basic
    @Column(name = "invoice_status", nullable = false)
    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @Basic
    @Column(name = "package_type_seq")
    public Integer getPackageTypeSeq() {
        return packageTypeSeq;
    }

    public void setPackageTypeSeq(Integer packageTypeSeq) {
        this.packageTypeSeq = packageTypeSeq;
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
    @Column(name = "current_status", nullable = false)
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Basic
    @Column(name = "payment_mode")
    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Basic
    @Column(name = "cash_or_credit", nullable = false)
    public Integer getCashOrCredit() {
        return cashOrCredit;
    }

    public void setCashOrCredit(Integer cashOrCredit) {
        this.cashOrCredit = cashOrCredit;
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
    @Column(name = "process_out_put")
    public String getProcessOutPut() {
        return processOutPut;
    }

    public void setProcessOutPut(String processOutPut) {
        this.processOutPut = processOutPut;
    }

    @Basic
    @Column(name = "upload_document_seq", nullable = false, updatable = false)
    public Integer getUploadDocumentSeq() {
        return uploadDocumentSeq;
    }

    public void setUploadDocumentSeq(Integer uploadDocumentSeq) {
        this.uploadDocumentSeq = uploadDocumentSeq;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getCustomer() {
        return customer;
    }

    public void setCustomer(Stakeholder customer) {
        this.customer = customer;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipper_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getShipper() {
        return shipper;
    }

    public void setShipper(Stakeholder shipper) {
        this.shipper = shipper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportBulkBooking that = (TransportBulkBooking) o;
        return Objects.equals(transportBulkBookingSeq, that.transportBulkBookingSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(departmentSeq, that.departmentSeq) &&
                Objects.equals(moduleSeq, that.moduleSeq) &&
                Objects.equals(customerSeq, that.customerSeq) &&
                Objects.equals(invoiceCustomerSeq, that.invoiceCustomerSeq) &&
                Objects.equals(shipperSeq, that.shipperSeq) &&
                Objects.equals(uowSeq, that.uowSeq) &&
                Objects.equals(uovSeq, that.uovSeq) &&
                Objects.equals(invoiceStatus, that.invoiceStatus) &&
                Objects.equals(packageTypeSeq, that.packageTypeSeq) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(currentStatus, that.currentStatus) &&
                Objects.equals(paymentMode, that.paymentMode) &&
                Objects.equals(cashOrCredit, that.cashOrCredit) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(uploadDocumentSeq, that.uploadDocumentSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportBulkBookingSeq, companyProfileSeq, departmentSeq, moduleSeq, customerSeq, invoiceCustomerSeq, shipperSeq, uowSeq, uovSeq, invoiceStatus, packageTypeSeq, createdBy, createdDate, currentStatus, paymentMode, cashOrCredit, remarks, uploadDocumentSeq);
    }
}
