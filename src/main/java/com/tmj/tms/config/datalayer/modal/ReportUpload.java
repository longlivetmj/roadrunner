package com.tmj.tms.config.datalayer.modal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "report_upload")
public class ReportUpload {

    private Integer reportUploadSeq;
    private String trackData;
    private String fileName;
    private String fileType;
    private String uploadedBy;
    private Date uploadedDate;
    private byte[] fileData;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "report_upload_seq", allocationSize = 1)
    @Column(name = "report_upload_seq", unique = true)
    public Integer getReportUploadSeq() {
        return reportUploadSeq;
    }

    public void setReportUploadSeq(Integer reportUploadSeq) {
        this.reportUploadSeq = reportUploadSeq;
    }

    @Column(name = "track_data")
    public String getTrackData() {
        return trackData;
    }

    public void setTrackData(String trackData) {
        this.trackData = trackData;
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

    @Column(name = "file_data")
    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @CreatedBy
    @Column(name = "uploaded_by", updatable = false)
    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    @CreatedDate
    @Column(name = "uploaded_date", updatable = false)
    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportUpload that = (ReportUpload) o;
        return reportUploadSeq == that.reportUploadSeq &&
                Objects.equals(trackData, that.trackData) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(fileType, that.fileType) &&
                Arrays.equals(fileData, that.fileData) &&
                Objects.equals(uploadedBy, that.uploadedBy) &&
                Objects.equals(uploadedDate, that.uploadedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportUploadSeq, trackData, fileName, fileType, fileData, uploadedBy, uploadedDate);
    }
}
