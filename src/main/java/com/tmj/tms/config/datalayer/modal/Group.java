package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "groups")
public class Group {
    private Integer groupSeq;
    private Integer moduleSeq;
    private String groupName;
    private String description;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "group_seq", allocationSize = 1)
    @Column(name = "group_seq", nullable = false, precision = 0, unique = true)
    public Integer getGroupSeq() {
        return groupSeq;
    }

    public void setGroupSeq(Integer groupSeq) {
        this.groupSeq = groupSeq;
    }

    @Basic
    @Column(name = "group_name", nullable = false, length = 100)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Basic
    @Column(name = "module_seq", nullable = false)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (groupSeq != null ? !groupSeq.equals(group.groupSeq) : group.groupSeq != null) return false;
        if (moduleSeq != null ? !moduleSeq.equals(group.moduleSeq) : group.moduleSeq != null) return false;
        if (groupName != null ? !groupName.equals(group.groupName) : group.groupName != null) return false;
        if (description != null ? !description.equals(group.description) : group.description != null) return false;
        if (createdBy != null ? !createdBy.equals(group.createdBy) : group.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(group.createdDate) : group.createdDate != null) return false;
        if (modifiedBy != null ? !modifiedBy.equals(group.modifiedBy) : group.modifiedBy != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(group.modifiedDate) : group.modifiedDate != null) return false;
        if (status != null ? !status.equals(group.status) : group.status != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = groupSeq != null ? groupSeq.hashCode() : 0;
        result = 31 * result + (moduleSeq != null ? moduleSeq.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedBy != null ? modifiedBy.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
