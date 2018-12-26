package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.finance.utility.ReferenceType;
import com.tmj.tms.finance.utility.TargetType;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "FinancialCharge.default"),
        @NamedEntityGraph(name = "FinancialCharge.create", attributeNodes = {
                @NamedAttributeNode(value = "financialChargeDetails", subgraph = "financialChargeDetails")
        }, subgraphs = @NamedSubgraph(name = "financialChargeDetails", attributeNodes = {@NamedAttributeNode("charge"),
                @NamedAttributeNode("currency"),
                @NamedAttributeNode("unit")})),
})

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "financial_charge")
public class FinancialCharge {
    private Integer financialChargeSeq;
    private Integer companyProfileSeq;
    private Integer moduleSeq;
    private Integer targetType;
    private Integer referenceType;
    private Integer referenceSeq;
    private String referenceNo;
    private Integer status;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private Module module;
    private List<FinancialChargeDetail> financialChargeDetails;

    private String targetTypeDescription;
    private String referenceTypeDescription;

    private TransportBooking transportBooking;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "financial_charge_seq", allocationSize = 1)
    @Column(name = "financial_charge_seq", unique = true)
    public Integer getFinancialChargeSeq() {
        return financialChargeSeq;
    }

    public void setFinancialChargeSeq(Integer financialChargeSeq) {
        this.financialChargeSeq = financialChargeSeq;
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
    @Column(name = "module_seq", nullable = false, updatable = false)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Basic
    @Column(name = "target_type", nullable = false, updatable = false)
    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
        if (targetType != null) {
            this.setTargetTypeDescription(TargetType.findOne(targetType).getTargetTypeDescription());
        }
    }

    @Basic
    @Column(name = "reference_type", nullable = false, updatable = false)
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
    @Column(name = "reference_seq", nullable = false, updatable = false)
    public Integer getReferenceSeq() {
        return referenceSeq;
    }

    public void setReferenceSeq(Integer referenceSeq) {
        this.referenceSeq = referenceSeq;
    }

    @Basic
    @Column(name = "reference_no", nullable = false, updatable = false, length = 100)
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
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

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_charge_seq", nullable = false)
    @Where(clause = "STATUS != 0")
    @OrderBy(value = "financialChargeDetailSeq")
    public List<FinancialChargeDetail> getFinancialChargeDetails() {
        return financialChargeDetails;
    }

    public void setFinancialChargeDetails(List<FinancialChargeDetail> financialChargeDetails) {
        this.financialChargeDetails = financialChargeDetails;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_seq", insertable = false, updatable = false)
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Transient
    public String getTargetTypeDescription() {
        return targetTypeDescription;
    }

    public void setTargetTypeDescription(String targetTypeDescription) {
        this.targetTypeDescription = targetTypeDescription;
    }

    @Transient
    public String getReferenceTypeDescription() {
        return referenceTypeDescription;
    }

    public void setReferenceTypeDescription(String referenceTypeDescription) {
        this.referenceTypeDescription = referenceTypeDescription;
    }

    @Transient
    public TransportBooking getTransportBooking() {
        return transportBooking;
    }

    public void setTransportBooking(TransportBooking transportBooking) {
        this.transportBooking = transportBooking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialCharge that = (FinancialCharge) o;
        return Objects.equals(financialChargeSeq, that.financialChargeSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(moduleSeq, that.moduleSeq) &&
                Objects.equals(targetType, that.targetType) &&
                Objects.equals(referenceType, that.referenceType) &&
                Objects.equals(referenceSeq, that.referenceSeq) &&
                Objects.equals(referenceNo, that.referenceNo) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(financialChargeDetails, that.financialChargeDetails) &&
                Objects.equals(targetTypeDescription, that.targetTypeDescription) &&
                Objects.equals(referenceTypeDescription, that.referenceTypeDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(financialChargeSeq, companyProfileSeq, moduleSeq, targetType, referenceType, referenceSeq, referenceNo, status, createdBy, createdDate, lastModifiedBy, lastModifiedDate, financialChargeDetails, targetTypeDescription, referenceTypeDescription);
    }
}
