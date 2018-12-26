package com.tmj.tms.config.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "REPORT_DJ_INFO_MAPPING")
public class ReportDjInfoMapping {
    private Integer reportDjInfoMappingSeq;
    private Integer dynamicJasperInfoSeq;
    private Integer reportSeq;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private DynamicJasperInfo dynamicJasperInfo;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "REPORT_DJ_INFO_MAPPING_SEQ", allocationSize = 1)
    @Column(name = "REPORT_DJ_INFO_MAPPING_SEQ", unique = true)
    public Integer getReportDjInfoMappingSeq() {
        return reportDjInfoMappingSeq;
    }

    public void setReportDjInfoMappingSeq(Integer reportDjInfoMappingSeq) {
        this.reportDjInfoMappingSeq = reportDjInfoMappingSeq;
    }

    @Basic
    @Column(name = "DYNAMIC_JASPER_INFO_SEQ")
    public Integer getDynamicJasperInfoSeq() {
        return dynamicJasperInfoSeq;
    }

    public void setDynamicJasperInfoSeq(Integer dynamicJasperInfoSeq) {
        this.dynamicJasperInfoSeq = dynamicJasperInfoSeq;
    }

    @Basic
    @Column(name = "REPORT_SEQ", insertable = false, updatable = false, nullable = false)
    public Integer getReportSeq() {
        return reportSeq;
    }

    public void setReportSeq(Integer reportSeq) {
        this.reportSeq = reportSeq;
    }

    @Basic
    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false, length = 50, updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @CreatedDate
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY", nullable = false, length = 50)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DYNAMIC_JASPER_INFO_SEQ", insertable = false, updatable = false, nullable = true)
    public DynamicJasperInfo getDynamicJasperInfo() {
        return dynamicJasperInfo;
    }

    public void setDynamicJasperInfo(DynamicJasperInfo dynamicJasperInfo) {
        this.dynamicJasperInfo = dynamicJasperInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportDjInfoMapping that = (ReportDjInfoMapping) o;
        return Objects.equals(dynamicJasperInfoSeq, that.dynamicJasperInfoSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dynamicJasperInfoSeq);
    }
}
