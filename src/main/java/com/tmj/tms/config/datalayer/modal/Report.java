package com.tmj.tms.config.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "Report.default", attributeNodes = {
                @NamedAttributeNode("companyProfile")
        })
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "report")
@Where(clause = "STATUS != 0")
public class Report {
    private Integer reportSeq;
    private String reportName;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer companyProfileSeq;
    private Integer documentLinkSeq;
    private Integer isDefaultReport;
    private Integer moduleSeq;
    private Integer reportCategoryTypeSeq;
    private String displayName;
    private String reportText1;
    private String reportText2;
    private String reportText3;
    private String reportText4;
    private String reportText5;
    private String reportText6;
    private String reportText7;

    private Integer reportImageSeq1;
    private Integer reportImageSeq2;
    private Integer reportImageSeq3;
    private Integer reportImageSeq4;
    private Integer reportImageSeq5;

    private CompanyProfile companyProfile;
    private List<ReportDjInfoMapping> reportDjInfoMappingList;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "report_seq", allocationSize = 1)
    @Column(name = "report_seq", unique = true)
    public Integer getReportSeq() {
        return reportSeq;
    }

    public void setReportSeq(Integer reportSeq) {
        this.reportSeq = reportSeq;
    }

    @Basic
    @Column(name = "report_name", nullable = false, length = 100)
    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    @Basic
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false, length = 50)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Basic
    @Column(name = "company_profile_seq", nullable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "document_link_seq")
    public Integer getDocumentLinkSeq() {
        return documentLinkSeq;
    }

    public void setDocumentLinkSeq(Integer documentLinkSeq) {
        this.documentLinkSeq = documentLinkSeq;
    }

    @Basic
    @Column(name = "module_seq")
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Basic
    @Column(name = "is_default_report")
    public Integer getIsDefaultReport() {
        return isDefaultReport;
    }

    public void setIsDefaultReport(Integer isDefaultReport) {
        this.isDefaultReport = isDefaultReport;
    }

    @Basic
    @Column(name = "report_category_type_seq")
    public Integer getReportCategoryTypeSeq() {
        return reportCategoryTypeSeq;
    }

    public void setReportCategoryTypeSeq(Integer reportCategoryTypeSeq) {
        this.reportCategoryTypeSeq = reportCategoryTypeSeq;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_profile_seq", insertable = false, updatable = false)
    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "report_seq", nullable = false)
    @OrderBy("reportDjInfoMappingSeq ASC")
    @Where(clause = "status != 0 ")
    public List<ReportDjInfoMapping> getReportDjInfoMappingList() {
        return reportDjInfoMappingList;
    }

    public void setReportDjInfoMappingList(List<ReportDjInfoMapping> reportDjInfoMappingList) {
        this.reportDjInfoMappingList = reportDjInfoMappingList;
    }

    @Basic
    @Column(name = "display_name", length = 1000)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "report_text1", length = 1000)
    public String getReportText1() {
        return reportText1;
    }

    public void setReportText1(String reportText1) {
        this.reportText1 = reportText1;
    }

    @Basic
    @Column(name = "report_text2", length = 1000)
    public String getReportText2() {
        return reportText2;
    }

    public void setReportText2(String reportText2) {
        this.reportText2 = reportText2;
    }

    @Basic
    @Column(name = "report_text3", length = 1000)
    public String getReportText3() {
        return reportText3;
    }

    public void setReportText3(String reportText3) {
        this.reportText3 = reportText3;
    }

    @Basic
    @Column(name = "report_text4", length = 1000)
    public String getReportText4() {
        return reportText4;
    }

    public void setReportText4(String reportText4) {
        this.reportText4 = reportText4;
    }

    @Basic
    @Column(name = "report_text5", length = 1000)
    public String getReportText5() {
        return reportText5;
    }

    public void setReportText5(String reportText5) {
        this.reportText5 = reportText5;
    }

    @Basic
    @Column(name = "report_text6", length = 1000)
    public String getReportText6() {
        return reportText6;
    }

    public void setReportText6(String reportText6) {
        this.reportText6 = reportText6;
    }

    @Basic
    @Column(name = "report_text7", length = 1000)
    public String getReportText7() {
        return reportText7;
    }

    public void setReportText7(String reportText7) {
        this.reportText7 = reportText7;
    }

    @Basic
    @Column(name = "report_image_seq1")
    public Integer getReportImageSeq1() {
        return reportImageSeq1;
    }

    public void setReportImageSeq1(Integer reportImageSeq1) {
        this.reportImageSeq1 = reportImageSeq1;
    }

    @Basic
    @Column(name = "report_image_seq2")
    public Integer getReportImageSeq2() {
        return reportImageSeq2;
    }

    public void setReportImageSeq2(Integer reportImageSeq2) {
        this.reportImageSeq2 = reportImageSeq2;
    }

    @Basic
    @Column(name = "report_image_seq3")
    public Integer getReportImageSeq3() {
        return reportImageSeq3;
    }

    public void setReportImageSeq3(Integer reportImageSeq3) {
        this.reportImageSeq3 = reportImageSeq3;
    }

    @Basic
    @Column(name = "report_image_seq4")
    public Integer getReportImageSeq4() {
        return reportImageSeq4;
    }

    public void setReportImageSeq4(Integer reportImageSeq4) {
        this.reportImageSeq4 = reportImageSeq4;
    }

    @Basic
    @Column(name = "report_image_seq5")
    public Integer getReportImageSeq5() {
        return reportImageSeq5;
    }

    public void setReportImageSeq5(Integer reportImageSeq5) {
        this.reportImageSeq5 = reportImageSeq5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(reportSeq, report.reportSeq) &&
                Objects.equals(reportName, report.reportName) &&
                Objects.equals(status, report.status) &&
                Objects.equals(createdBy, report.createdBy) &&
                Objects.equals(createdDate, report.createdDate) &&
                Objects.equals(lastModifiedBy, report.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, report.lastModifiedDate) &&
                Objects.equals(companyProfileSeq, report.companyProfileSeq) &&
                Objects.equals(documentLinkSeq, report.documentLinkSeq) &&
                Objects.equals(isDefaultReport, report.isDefaultReport) &&
                Objects.equals(moduleSeq, report.moduleSeq) &&
                Objects.equals(reportCategoryTypeSeq, report.reportCategoryTypeSeq) &&
                Objects.equals(reportText1, report.reportText1) &&
                Objects.equals(reportText2, report.reportText2) &&
                Objects.equals(reportText3, report.reportText3) &&
                Objects.equals(reportText4, report.reportText4) &&
                Objects.equals(reportText5, report.reportText5) &&
                Objects.equals(reportText6, report.reportText6) &&
                Objects.equals(reportText7, report.reportText7) &&
                Objects.equals(companyProfile, report.companyProfile) &&
                Objects.equals(reportDjInfoMappingList, report.reportDjInfoMappingList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportSeq, reportName, status, createdBy, createdDate, lastModifiedBy, lastModifiedDate, companyProfileSeq, documentLinkSeq, isDefaultReport, moduleSeq, reportCategoryTypeSeq, reportText1, reportText2, reportText3, reportText4, reportText5, reportText6, reportText7, companyProfile, reportDjInfoMappingList);
    }
}
