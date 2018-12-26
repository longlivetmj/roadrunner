package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "document_links")
public class DocumentLink {
    private Integer documentLinkSeq;
    private String linkName;
    private String icon;
    private String pageName;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Integer status;
    private Integer moduleSeq;
    private Integer subModuleSeq;
    private Integer actionGroupSeq;
    private Integer orderBy;
    private Integer externalLink;
    private String externalPage;
    private List<Authority> authorities = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "document_link_seq", allocationSize = 1)
    @Column(name = "document_link_seq", nullable = false, precision = 0, unique = true)
    public Integer getDocumentLinkSeq() {
        return documentLinkSeq;
    }

    public void setDocumentLinkSeq(Integer documentLinkSeq) {
        this.documentLinkSeq = documentLinkSeq;
    }

    @Basic
    @Column(name = "link_name", nullable = false, length = 200)
    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    @Basic
    @Column(name = "page_name", nullable = false, length = 200)
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
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

    @Column(name = "module_seq", nullable = false)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Basic
    @Column(name = "sub_module_seq", nullable = false)
    public Integer getSubModuleSeq() {
        return subModuleSeq;
    }

    public void setSubModuleSeq(Integer subModuleSeq) {
        this.subModuleSeq = subModuleSeq;
    }

    @Basic
    @Column(name = "action_group_seq", nullable = false)
    public Integer getActionGroupSeq() {
        return actionGroupSeq;
    }

    public void setActionGroupSeq(Integer actionGroupSeq) {
        this.actionGroupSeq = actionGroupSeq;
    }

    @Basic
    @Column(name = "order_by")
    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
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
    @Column(name = "external_link")
    public Integer getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(Integer externalLink) {
        this.externalLink = externalLink;
    }

    @Basic
    @Column(name = "external_page")
    public String getExternalPage() {
        return externalPage;
    }

    public void setExternalPage(String externalPage) {
        this.externalPage = externalPage;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "document_link_seq", nullable = false)
    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentLink that = (DocumentLink) o;
        return Objects.equals(documentLinkSeq, that.documentLinkSeq) &&
                Objects.equals(linkName, that.linkName) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(pageName, that.pageName) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(modifiedBy, that.modifiedBy) &&
                Objects.equals(modifiedDate, that.modifiedDate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(moduleSeq, that.moduleSeq) &&
                Objects.equals(subModuleSeq, that.subModuleSeq) &&
                Objects.equals(actionGroupSeq, that.actionGroupSeq) &&
                Objects.equals(orderBy, that.orderBy) &&
                Objects.equals(externalLink, that.externalLink) &&
                Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentLinkSeq, linkName, icon, pageName, createdBy, createdDate, modifiedBy, modifiedDate, status, moduleSeq, subModuleSeq, actionGroupSeq, orderBy, externalLink, authorities);
    }
}
