package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.utility.MasterDataStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "Employee.default", attributeNodes = {
                @NamedAttributeNode("stakeholder"),
                @NamedAttributeNode("employeeDesignation"),
                @NamedAttributeNode(value = "addressBook", subgraph = "addressBook")
        }, subgraphs = @NamedSubgraph(name = "addressBook", attributeNodes = @NamedAttributeNode("country")))
})
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "employee")
public class Employee {
    private Integer employeeSeq;
    private String employeeName;
    private String nicNo;
    private String licenseNo;
    private Date licenseExpiryDate;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer addressBookSeq;
    private Integer employeeDesignationSeq;
    private Integer companyProfileSeq;
    private Integer stakeholderSeq;
    private Integer personalPhoto;
    private Integer gsCertificate;
    private Date gsCertificateExpiryDate;
    private Integer policeReport;
    private Date policeReportExpiryDate;
    private Integer nic;

    private Integer financeIntegration;
    private Long financeIntegrationKey;

    private AddressBook addressBook;

    private CompanyProfile companyProfile;
    private EmployeeDesignation employeeDesignation;
    private Stakeholder stakeholder;

    private String statusDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "employee_seq", allocationSize = 1)
    @Column(name = "employee_seq", unique = true)
    public Integer getEmployeeSeq() {
        return employeeSeq;
    }

    public void setEmployeeSeq(Integer employeeSeq) {
        this.employeeSeq = employeeSeq;
    }

    @Basic
    @Column(name = "employee_name")
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        if (status != null) {
            this.setStatusDescription(MasterDataStatus.findOne(status).getStatus());
        }
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
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
    @Column(name = "last_modified_by", nullable = true)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "last_modified_date", nullable = true)
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_book_seq", nullable = false)
    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Basic
    @Column(name = "address_book_seq", insertable = false, updatable = false, nullable = false)
    public Integer getAddressBookSeq() {
        return addressBookSeq;
    }

    public void setAddressBookSeq(Integer addressBookSeq) {
        this.addressBookSeq = addressBookSeq;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_designation_seq", insertable = false, updatable = false)
    public EmployeeDesignation getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(EmployeeDesignation employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    @Basic
    @Column(name = "employee_designation_seq", nullable = false)
    public Integer getEmployeeDesignationSeq() {
        return employeeDesignationSeq;
    }

    public void setEmployeeDesignationSeq(Integer employeeDesignationSeq) {
        this.employeeDesignationSeq = employeeDesignationSeq;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_profile_seq", insertable = false, updatable = false, nullable = false)
    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    @Basic
    @Column(name = "company_profile_seq", updatable = false, nullable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Basic
    @Column(name = "nic_no", nullable = false, length = 30)
    public String getNicNo() {
        return nicNo;
    }

    public void setNicNo(String nicNo) {
        this.nicNo = nicNo;
    }

    @Basic
    @Column(name = "license_no", length = 30)
    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    @Basic
    @Column(name = "license_expiry_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(Date licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    @Basic
    @Column(name = "stakeholder_seq")
    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
    }

    @Basic
    @Column(name = "personal_photo")
    public Integer getPersonalPhoto() {
        return personalPhoto;
    }

    public void setPersonalPhoto(Integer personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    @Basic
    @Column(name = "gs_certificate")
    public Integer getGsCertificate() {
        return gsCertificate;
    }

    public void setGsCertificate(Integer gsCertificate) {
        this.gsCertificate = gsCertificate;
    }

    @Basic
    @Column(name = "gs_certificate_expiry_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getGsCertificateExpiryDate() {
        return gsCertificateExpiryDate;
    }

    public void setGsCertificateExpiryDate(Date gsCertificateExpiryDate) {
        this.gsCertificateExpiryDate = gsCertificateExpiryDate;
    }

    @Basic
    @Column(name = "police_report")
    public Integer getPoliceReport() {
        return policeReport;
    }

    public void setPoliceReport(Integer policeReport) {
        this.policeReport = policeReport;
    }

    @Basic
    @Column(name = "police_report_expiry_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getPoliceReportExpiryDate() {
        return policeReportExpiryDate;
    }

    public void setPoliceReportExpiryDate(Date policeReportExpiryDate) {
        this.policeReportExpiryDate = policeReportExpiryDate;
    }

    @Basic
    @Column(name = "nic")
    public Integer getNic() {
        return nic;
    }

    public void setNic(Integer nic) {
        this.nic = nic;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(Stakeholder stakeholder) {
        this.stakeholder = stakeholder;
    }

    @Basic
    @Column(name = "finance_integration")
    public Integer getFinanceIntegration() {
        return financeIntegration;
    }

    public void setFinanceIntegration(Integer financeIntegration) {
        this.financeIntegration = financeIntegration;
    }

    @Basic
    @Column(name = "finance_integration_key")
    public Long getFinanceIntegrationKey() {
        return financeIntegrationKey;
    }

    public void setFinanceIntegrationKey(Long financeIntegrationKey) {
        this.financeIntegrationKey = financeIntegrationKey;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeSeq, employee.employeeSeq) &&
                Objects.equals(employeeName, employee.employeeName) &&
                Objects.equals(nicNo, employee.nicNo) &&
                Objects.equals(licenseNo, employee.licenseNo) &&
                Objects.equals(licenseExpiryDate, employee.licenseExpiryDate) &&
                Objects.equals(status, employee.status) &&
                Objects.equals(addressBookSeq, employee.addressBookSeq) &&
                Objects.equals(employeeDesignationSeq, employee.employeeDesignationSeq) &&
                Objects.equals(companyProfileSeq, employee.companyProfileSeq) &&
                Objects.equals(stakeholderSeq, employee.stakeholderSeq) &&
                Objects.equals(personalPhoto, employee.personalPhoto) &&
                Objects.equals(gsCertificate, employee.gsCertificate) &&
                Objects.equals(gsCertificateExpiryDate, employee.gsCertificateExpiryDate) &&
                Objects.equals(policeReport, employee.policeReport) &&
                Objects.equals(policeReportExpiryDate, employee.policeReportExpiryDate) &&
                Objects.equals(nic, employee.nic) &&
                Objects.equals(statusDescription, employee.statusDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeSeq, employeeName, nicNo, licenseNo, licenseExpiryDate, status, addressBookSeq, employeeDesignationSeq, companyProfileSeq, stakeholderSeq, personalPhoto, gsCertificate, gsCertificateExpiryDate, policeReport, policeReportExpiryDate, nic, statusDescription);
    }
}
