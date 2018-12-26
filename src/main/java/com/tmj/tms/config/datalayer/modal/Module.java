package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.*;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "module.default")
})
@Entity
@Table(name = "modules")
public class Module {
    private Integer moduleSeq;
    private String moduleName;
    private String description;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String urlPattern;
    private Integer financeEnabled;
    private String moduleCode;
    private Integer transportMode;
    private Integer core;
    private Integer client;

    private List<SubModule> subModuleList;
    private Set<CompanyModule> companyModules = new HashSet<CompanyModule>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "module_seq", allocationSize = 1)
    @Column(name = "module_seq", nullable = false, precision = 0, unique = true)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Basic
    @Column(name = "module_name", nullable = true, length = 100)
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
    @Column(name = "status", nullable = true, precision = 0)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @Column(name = "url_pattern", nullable = false)
    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Basic
    @Column(name = "finance_enabled", nullable = true, precision = 0)
    public Integer getFinanceEnabled() {
        return financeEnabled;
    }

    public void setFinanceEnabled(Integer financeEnabled) {
        this.financeEnabled = financeEnabled;
    }

    @Basic
    @Column(name = "module_code", nullable = true, length = 15)
    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Basic
    @Column(name = "transport_mode")
    public Integer getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(Integer transportMode) {
        this.transportMode = transportMode;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "module_seq", insertable = false, updatable = false)
    public List<SubModule> getSubModuleList() {
        return subModuleList;
    }

    public void setSubModuleList(List<SubModule> subModuleList) {
        this.subModuleList = subModuleList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "module_seq", insertable = false, updatable = false, nullable = false)
    public Set<CompanyModule> getCompanyModules() {
        return companyModules;
    }

    public void setCompanyModules(Set<CompanyModule> companyModules) {
        this.companyModules = companyModules;
    }

    @Basic
    @Column(name = "core")
    public Integer getCore() {
        return core;
    }

    public void setCore(Integer core) {
        this.core = core;
    }

    @Basic
    @Column(name = "client")
    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(moduleSeq, module.moduleSeq) &&
                Objects.equals(moduleName, module.moduleName) &&
                Objects.equals(description, module.description) &&
                Objects.equals(status, module.status) &&
                Objects.equals(urlPattern, module.urlPattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleSeq, moduleName, description, status, urlPattern);
    }
}
