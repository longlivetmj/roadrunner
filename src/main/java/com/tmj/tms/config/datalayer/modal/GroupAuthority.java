package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "group_authorities")
public class GroupAuthority {
    private Integer groupAuthoritySeq;
    private Integer groupSeq;
    private Integer authoritySeq;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "group_authority_seq", allocationSize = 1)
    @Column(name = "group_authority_seq", nullable = false, precision = 0, unique = true)
    public Integer getGroupAuthoritySeq() {
        return groupAuthoritySeq;
    }

    public void setGroupAuthoritySeq(Integer groupAuthoritySeq) {
        this.groupAuthoritySeq = groupAuthoritySeq;
    }

    @Basic
    @Column(name = "group_seq", nullable = false, precision = 0)
    public Integer getGroupSeq() {
        return groupSeq;
    }

    public void setGroupSeq(Integer groupSeq) {
        this.groupSeq = groupSeq;
    }

    @Basic
    @Column(name = "authority_seq", nullable = false, precision = 0)
    public Integer getAuthoritySeq() {
        return authoritySeq;
    }

    public void setAuthoritySeq(Integer authoritySeq) {
        this.authoritySeq = authoritySeq;
    }

    @Basic
    @Column(name = "created_by", nullable = true, length = 50)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_date", nullable = true)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "modified_by", nullable = true, length = 50)
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Basic
    @Column(name = "modified_date", nullable = true)
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Basic
    @Column(name = "status", nullable = true, precision = 0)
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

        GroupAuthority that = (GroupAuthority) o;

        if (groupAuthoritySeq != null ? !groupAuthoritySeq.equals(that.groupAuthoritySeq) : that.groupAuthoritySeq != null)
            return false;
        if (groupSeq != null ? !groupSeq.equals(that.groupSeq) : that.groupSeq != null) return false;
        if (authoritySeq != null ? !authoritySeq.equals(that.authoritySeq) : that.authoritySeq != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (modifiedBy != null ? !modifiedBy.equals(that.modifiedBy) : that.modifiedBy != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupAuthoritySeq != null ? groupAuthoritySeq.hashCode() : 0;
        result = 31 * result + (groupSeq != null ? groupSeq.hashCode() : 0);
        result = 31 * result + (authoritySeq != null ? authoritySeq.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedBy != null ? modifiedBy.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
