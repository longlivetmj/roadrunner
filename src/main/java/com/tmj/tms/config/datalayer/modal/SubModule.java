package com.tmj.tms.config.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sub_modules")
public class SubModule {
    private Integer subModuleSeq;
    private Integer moduleSeq;
    private String subModuleName;
    private String description;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Integer status;
    private String icon;

    private List<ActionGroup> actionGroupList;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "sub_module_seq", allocationSize = 1)
    @Column(name = "sub_module_seq", nullable = false, precision = 0, unique = true)
    public Integer getSubModuleSeq() {
        return subModuleSeq;
    }

    public void setSubModuleSeq(Integer subModuleSeq) {
        this.subModuleSeq = subModuleSeq;
    }

    @Basic
    @Column(name = "sub_module_name", nullable = true, length = 100)
    public String getSubModuleName() {
        return subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
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
    @Column(name = "module_seq", nullable = false)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_module_seq", insertable = false, updatable = false)
    @JsonIgnore
    public List<ActionGroup> getActionGroupList() {
        return actionGroupList;
    }

    public void setActionGroupList(List<ActionGroup> actionGroupList) {
        this.actionGroupList = actionGroupList;
    }

    @Basic
    @Column(name = "icon", nullable = false, length = 50)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubModule subModule = (SubModule) o;
        return Objects.equals(subModuleSeq, subModule.subModuleSeq) &&
                Objects.equals(moduleSeq, subModule.moduleSeq) &&
                Objects.equals(subModuleName, subModule.subModuleName) &&
                Objects.equals(description, subModule.description) &&
                Objects.equals(status, subModule.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subModuleSeq, moduleSeq, subModuleName, description, status);
    }
}
