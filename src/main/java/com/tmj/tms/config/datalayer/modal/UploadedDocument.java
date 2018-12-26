package com.tmj.tms.config.datalayer.modal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "uploaded_document")
public class UploadedDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "uploaded_document_seq", allocationSize = 1)
    @Column(name = "uploaded_document_seq", unique = true)
    private Integer uploadedDocumentSeq;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @CreatedBy
    @Column(name = "uploaded_by", updatable = false)
    private String uploadedBy;

    @CreatedDate
    @Column(name = "uploaded_date", updatable = false)
    private Date uploadedDate;

    @Column(name = "file_data")
    private byte[] fileData;


    public Integer getUploadedDocumentSeq() {
        return uploadedDocumentSeq;
    }

    public void setUploadedDocumentSeq(Integer uploadedDocumentSeq) {
        this.uploadedDocumentSeq = uploadedDocumentSeq;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadedDocument)) return false;

        UploadedDocument that = (UploadedDocument) o;

        if (getUploadedDocumentSeq() != null ? !getUploadedDocumentSeq().equals(that.getUploadedDocumentSeq()) : that.getUploadedDocumentSeq() != null)
            return false;
        if (getFileName() != null ? !getFileName().equals(that.getFileName()) : that.getFileName() != null)
            return false;
        if (getFileType() != null ? !getFileType().equals(that.getFileType()) : that.getFileType() != null)
            return false;
        if (!Arrays.equals(getFileData(), that.getFileData())) return false;
        if (getUploadedBy() != null ? !getUploadedBy().equals(that.getUploadedBy()) : that.getUploadedBy() != null)
            return false;
        return getUploadedDate() != null ? getUploadedDate().equals(that.getUploadedDate()) : that.getUploadedDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getUploadedDocumentSeq() != null ? getUploadedDocumentSeq().hashCode() : 0;
        result = 31 * result + (getFileName() != null ? getFileName().hashCode() : 0);
        result = 31 * result + (getFileType() != null ? getFileType().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getFileData());
        result = 31 * result + (getUploadedBy() != null ? getUploadedBy().hashCode() : 0);
        result = 31 * result + (getUploadedDate() != null ? getUploadedDate().hashCode() : 0);
        return result;
    }
}
