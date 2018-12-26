package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_groups")
public class UserGroup {
    private Integer userGroupSeq;
    private Integer userSeq;
    private Integer groupSeq;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Integer status;
    private Integer userModuleSeq;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "user_group_seq", allocationSize = 1)
    @Column(name = "user_group_seq", nullable = false, precision = 0, unique = true)
    public Integer getUserGroupSeq() {
        return userGroupSeq;
    }

    public void setUserGroupSeq(Integer userGroupSeq) {
        this.userGroupSeq = userGroupSeq;
    }

    @Basic
    @Column(name = "user_seq", nullable = false, precision = 0)
    public Integer getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Integer userSeq) {
        this.userSeq = userSeq;
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
    @Column(name = "status", nullable = false, precision = 0)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "user_module_seq", precision = 0)
    public Integer getUserModuleSeq() {
        return userModuleSeq;
    }

    public void setUserModuleSeq(Integer userModuleSeq) {
        this.userModuleSeq = userModuleSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroup)) return false;

        UserGroup userGroup = (UserGroup) o;

        if (getUserGroupSeq() != null ? !getUserGroupSeq().equals(userGroup.getUserGroupSeq()) : userGroup.getUserGroupSeq() != null)
            return false;
        if (getUserSeq() != null ? !getUserSeq().equals(userGroup.getUserSeq()) : userGroup.getUserSeq() != null)
            return false;
        if (getGroupSeq() != null ? !getGroupSeq().equals(userGroup.getGroupSeq()) : userGroup.getGroupSeq() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(userGroup.getCreatedBy()) : userGroup.getCreatedBy() != null)
            return false;
        if (getCreatedDate() != null ? !getCreatedDate().equals(userGroup.getCreatedDate()) : userGroup.getCreatedDate() != null)
            return false;
        if (getModifiedBy() != null ? !getModifiedBy().equals(userGroup.getModifiedBy()) : userGroup.getModifiedBy() != null)
            return false;
        if (getModifiedDate() != null ? !getModifiedDate().equals(userGroup.getModifiedDate()) : userGroup.getModifiedDate() != null)
            return false;
        return !(getStatus() != null ? !getStatus().equals(userGroup.getStatus()) : userGroup.getStatus() != null);

    }

    @Override
    public int hashCode() {
        int result = getUserGroupSeq() != null ? getUserGroupSeq().hashCode() : 0;
        result = 31 * result + (getUserSeq() != null ? getUserSeq().hashCode() : 0);
        result = 31 * result + (getGroupSeq() != null ? getGroupSeq().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getModifiedBy() != null ? getModifiedBy().hashCode() : 0);
        result = 31 * result + (getModifiedDate() != null ? getModifiedDate().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }
}
