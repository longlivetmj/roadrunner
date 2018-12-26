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
        @NamedEntityGraph(name = "SalesPerson.default", attributeNodes = {
                @NamedAttributeNode("stakeholder"),
                @NamedAttributeNode("companyProfile"),
                @NamedAttributeNode(value = "addressBook", subgraph = "addressBook")
        }, subgraphs = @NamedSubgraph(name = "addressBook", attributeNodes = @NamedAttributeNode("country")))
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sales_person")
public class SalesPerson {
    private Integer salesPersonSeq;
    private String salesPersonCode;
    private String salesPersonName;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private Integer addressBookSeq;
    private Integer stakeholderSeq;
    private Integer companyProfileSeq;

    private AddressBook addressBook;
    private Stakeholder stakeholder;
    private CompanyProfile companyProfile;

    private String statusDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "sales_person_seq", allocationSize = 1)
    @Column(name = "sales_person_seq", unique = true)
    public Integer getSalesPersonSeq() {
        return salesPersonSeq;
    }

    public void setSalesPersonSeq(Integer salesPersonSeq) {
        this.salesPersonSeq = salesPersonSeq;
    }


    @Basic
    @Column(name = "sales_person_code", length = 15, nullable = false, unique = true)
    public String getSalesPersonCode() {
        return salesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        this.salesPersonCode = salesPersonCode;
    }

    @Basic
    @Column(name = "sales_person_name", length = 50, nullable = false)
    public String getSalesPersonName() {
        return salesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
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
    @Column(name = "created_by", length = 50, nullable = false, updatable = false)
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
    @Column(name = "last_modified_by")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
    @JoinColumn(name = "address_book_seq", nullable = false)
    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeholder_seq", insertable = false, updatable = false, nullable = false)
    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(Stakeholder stakeholder) {
        this.stakeholder = stakeholder;
    }

    @Basic
    @Column(name = "stakeholder_seq", nullable = false)
    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesPerson)) return false;
        SalesPerson that = (SalesPerson) o;
        return Objects.equals(getSalesPersonSeq(), that.getSalesPersonSeq()) &&
                Objects.equals(getSalesPersonCode(), that.getSalesPersonCode()) &&
                Objects.equals(getSalesPersonName(), that.getSalesPersonName()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getAddressBookSeq(), that.getAddressBookSeq()) &&
                Objects.equals(getStakeholderSeq(), that.getStakeholderSeq()) &&
                Objects.equals(getCompanyProfileSeq(), that.getCompanyProfileSeq()) &&
                Objects.equals(getAddressBook(), that.getAddressBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSalesPersonSeq(), getSalesPersonCode(), getSalesPersonName(), getStatus(), getAddressBookSeq(), getStakeholderSeq(), getCompanyProfileSeq(), getAddressBook());
    }
}
