package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "action_groups")
public class ActionGroup {
    private Integer actionGroupSeq;
    private Integer subModuleSeq;
    private String actionGroupName;
    private String description;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Integer status;
    private String icon;
    private Integer orderBy;

    private List<DocumentLink> documentLinkList;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "action_group_seq", allocationSize = 1)
    @Column(name = "action_group_seq", nullable = false, precision = 0, unique = true)
    public Integer getActionGroupSeq() {
        return actionGroupSeq;
    }

    public void setActionGroupSeq(Integer actionGroupSeq) {
        this.actionGroupSeq = actionGroupSeq;
    }

    @Basic
    @Column(name = "action_group_name", nullable = true, length = 100)
    public String getActionGroupName() {
        return actionGroupName;
    }

    public void setActionGroupName(String actionGroupName) {
        this.actionGroupName = actionGroupName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 500)
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
    @Column(name = "sub_module_seq", nullable = true, precision = 0)
    public Integer getSubModuleSeq() {
        return subModuleSeq;
    }

    public void setSubModuleSeq(Integer subModuleSeq) {
        this.subModuleSeq = subModuleSeq;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_group_seq", insertable = false, updatable = false)
    public List<DocumentLink> getDocumentLinkList() {
        return documentLinkList;
    }

    public void setDocumentLinkList(List<DocumentLink> documentLinkList) {
        this.documentLinkList = documentLinkList;
    }

    @Basic
    @Column(name = "icon", nullable = false, length = 50)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "order_by")
    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionGroup)) return false;

        ActionGroup that = (ActionGroup) o;

        if (getActionGroupSeq() != null ? !getActionGroupSeq().equals(that.getActionGroupSeq()) : that.getActionGroupSeq() != null)
            return false;
        if (getSubModuleSeq() != null ? !getSubModuleSeq().equals(that.getSubModuleSeq()) : that.getSubModuleSeq() != null)
            return false;
        if (getActionGroupName() != null ? !getActionGroupName().equals(that.getActionGroupName()) : that.getActionGroupName() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(that.getCreatedBy()) : that.getCreatedBy() != null)
            return false;
        if (getCreatedDate() != null ? !getCreatedDate().equals(that.getCreatedDate()) : that.getCreatedDate() != null)
            return false;
        if (getModifiedBy() != null ? !getModifiedBy().equals(that.getModifiedBy()) : that.getModifiedBy() != null)
            return false;
        if (getModifiedDate() != null ? !getModifiedDate().equals(that.getModifiedDate()) : that.getModifiedDate() != null)
            return false;
        return getStatus() != null ? getStatus().equals(that.getStatus()) : that.getStatus() == null;

    }

    @Override
    public int hashCode() {
        int result = getActionGroupSeq() != null ? getActionGroupSeq().hashCode() : 0;
        result = 31 * result + (getSubModuleSeq() != null ? getSubModuleSeq().hashCode() : 0);
        result = 31 * result + (getActionGroupName() != null ? getActionGroupName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getModifiedBy() != null ? getModifiedBy().hashCode() : 0);
        result = 31 * result + (getModifiedDate() != null ? getModifiedDate().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActionGroup{" +
                "actionGroupSeq=" + actionGroupSeq +
                ", subModuleSeq=" + subModuleSeq +
                ", actionGroupName='" + actionGroupName + '\'' +
                ", description='" + description + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", status=" + status +
                '}';
    }
}
