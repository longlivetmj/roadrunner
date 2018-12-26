package com.tmj.tms.config.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "report_image")
public class ReportImage {

    private Integer reportImageSeq;
    private String fileName;
    private String fileType;
    private String uploadedBy;
    private Date uploadedDate;
    private byte[] fileData;
    private Integer companyProfileSeq;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "report_image_seq", allocationSize = 1)
    @Column(name = "report_image_seq", unique = true)
    public Integer getReportImageSeq() {
        return reportImageSeq;
    }

    public void setReportImageSeq(Integer reportImageSeq) {
        this.reportImageSeq = reportImageSeq;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Basic
    @CreatedBy
    @Column(name = "uploaded_by", nullable = false, length = 50, updatable = false)
    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    @Basic
    @CreatedDate
    @Column(name = "uploaded_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    @Basic
    @Column(name = "file_data")
    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Basic
    @Column(name = "company_profile_seq")
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportImage that = (ReportImage) o;
        return Objects.equals(reportImageSeq, that.reportImageSeq) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(fileType, that.fileType) &&
                Objects.equals(uploadedBy, that.uploadedBy) &&
                Objects.equals(uploadedDate, that.uploadedDate) &&
                Arrays.equals(fileData, that.fileData) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportImageSeq, fileName, fileType, uploadedBy, uploadedDate, fileData, companyProfileSeq);
    }
}
