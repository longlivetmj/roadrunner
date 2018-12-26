package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SALES_TYPE")
public class SalesType {
    private Integer salesTypeSeq;
    private String salesTypeName;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "SALES_TYPE_SEQ", allocationSize = 1)
    @Column(name = "SALES_TYPE_SEQ")
    public Integer getSalesTypeSeq() {
        return salesTypeSeq;
    }

    public void setSalesTypeSeq(Integer salesTypeSeq) {
        this.salesTypeSeq = salesTypeSeq;
    }

    @Basic
    @Column(name = "SALES_TYPE_NAME",nullable = false)
    public String getSalesTypeName() {
        return salesTypeName;
    }

    public void setSalesTypeName(String salesTypeName) {
        this.salesTypeName = salesTypeName;
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
    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "CREATED_DATE", nullable = true, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "LAST_MODIFIED_BY")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "LAST_MODIFIED_DATE", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalesType salesType = (SalesType) o;

        if (salesTypeSeq != salesType.salesTypeSeq) return false;
        if (salesTypeName != null ? !salesTypeName.equals(salesType.salesTypeName) : salesType.salesTypeName != null)
            return false;
        if (status != null ? !status.equals(salesType.status) : salesType.status != null) return false;
        if (createdBy != null ? !createdBy.equals(salesType.createdBy) : salesType.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(salesType.createdDate) : salesType.createdDate != null)
            return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(salesType.lastModifiedBy) : salesType.lastModifiedBy != null)
            return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(salesType.lastModifiedDate) : salesType.lastModifiedDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (salesTypeSeq ^ (salesTypeSeq >>> 32));
        result = 31 * result + (salesTypeName != null ? salesTypeName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }
}
