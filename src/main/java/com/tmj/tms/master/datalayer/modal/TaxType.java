package com.tmj.tms.master.datalayer.modal;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tax_type")
public class TaxType {
    private Integer taxTypeSeq;
    private String taxTypeName;
    private String remarks;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    @Id
    @Column(name = "tax_type_seq", nullable = false, precision = 0)
    public Integer getTaxTypeSeq() {
        return taxTypeSeq;
    }

    public void setTaxTypeSeq(Integer taxTypeSeq) {
        this.taxTypeSeq = taxTypeSeq;
    }

    @Basic
    @Column(name = "tax_type_name", nullable = false, length = 50)
    public String getTaxTypeName() {
        return taxTypeName;
    }

    public void setTaxTypeName(String taxTypeName) {
        this.taxTypeName = taxTypeName;
    }

    @Basic
    @Column(name = "remarks", nullable = true, length = 150)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @Column(name = "created_by", nullable = false, length = 50)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_date", nullable = false)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "last_modified_by", nullable = false, length = 50)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "last_modified_date", nullable = false)
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Basic
    @Column(name = "status", nullable = false, precision = 0)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxType taxType = (TaxType) o;
        return Objects.equals(taxTypeSeq, taxType.taxTypeSeq) &&
                Objects.equals(taxTypeName, taxType.taxTypeName) &&
                Objects.equals(remarks, taxType.remarks) &&
                Objects.equals(createdBy, taxType.createdBy) &&
                Objects.equals(createdDate, taxType.createdDate) &&
                Objects.equals(lastModifiedBy, taxType.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, taxType.lastModifiedDate) &&
                Objects.equals(status, taxType.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taxTypeSeq, taxTypeName, remarks, createdBy, createdDate, lastModifiedBy, lastModifiedDate, status);
    }
}
