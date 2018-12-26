package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "AddressBook.default", attributeNodes = {
                @NamedAttributeNode("country")
        })
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "address_book")
public class AddressBook {
    private Integer addressBookSeq;
    private String firstName;
    private Integer countrySeq;
    private String middleInitials;
    private String lastName;
    private String title;
    private String designation;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String fax;
    private String email;
    private String telephone;
    private String mobile;
    private String pager;
    private String telephoneExtension;
    private String website;
    private String epfNo;
    private String nicNo;
    private String altEMail;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private String chaLicenseNo;

    private Country country;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "address_book_seq", allocationSize = 1)
    @Column(name = "address_book_seq", nullable = false, precision = 0, unique = true)
    public Integer getAddressBookSeq() {
        return addressBookSeq;
    }

    public void setAddressBookSeq(Integer addressBookSeq) {
        this.addressBookSeq = addressBookSeq;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "country_seq", nullable = true)
    public Integer getCountrySeq() {
        return countrySeq;
    }

    public void setCountrySeq(Integer countrySeq) {
        this.countrySeq = countrySeq;
    }


    @Basic
    @Column(name = "middle_initials")
    public String getMiddleInitials() {
        return middleInitials;
    }

    public void setMiddleInitials(String middleInitials) {
        this.middleInitials = middleInitials;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "designation")
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Basic
    @Column(name = "address1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Basic
    @Column(name = "address2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "zip", length = 10)
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Basic
    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "e_mail")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "pager")
    public String getPager() {
        return pager;
    }

    public void setPager(String pager) {
        this.pager = pager;
    }

    @Basic
    @Column(name = "telephone_extension")
    public String getTelephoneExtension() {
        return telephoneExtension;
    }

    public void setTelephoneExtension(String telephoneExtension) {
        this.telephoneExtension = telephoneExtension;
    }

    @Basic
    @Column(name = "website")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Basic
    @Column(name = "epf_no")
    public String getEpfNo() {
        return epfNo;
    }

    public void setEpfNo(String epfNo) {
        this.epfNo = epfNo;
    }

    @Basic
    @Column(name = "nic_no")
    public String getNicNo() {
        return nicNo;
    }

    public void setNicNo(String nicNo) {
        this.nicNo = nicNo;
    }

    @Basic
    @Column(name = "alt_e_mail")
    public String getAltEMail() {
        return altEMail;
    }

    public void setAltEMail(String altEMail) {
        this.altEMail = altEMail;
    }


    @Basic
    @CreatedBy
    @Column(name = "created_by", length = 50, updatable = false, nullable = false)
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
    @Column(name = "last_modified_by", length = 50, nullable = false)
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
    @Column(name = "cha_license_no", length = 50, nullable = true)
    public String getChaLicenseNo() {
        return chaLicenseNo;
    }

    public void setChaLicenseNo(String chaLicenseNo) {
        this.chaLicenseNo = chaLicenseNo;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_seq", insertable = false, updatable = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressBook)) return false;
        AddressBook that = (AddressBook) com.tmj.tms.utility.ReflectionUtils.nullifyStrings(o);
        return Objects.equals(getAddressBookSeq(), that.getAddressBookSeq()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getCountrySeq(), that.getCountrySeq()) &&
                Objects.equals(getMiddleInitials(), that.getMiddleInitials()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDesignation(), that.getDesignation()) &&
                Objects.equals(getAddress1(), that.getAddress1()) &&
                Objects.equals(getAddress2(), that.getAddress2()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getZip(), that.getZip()) &&
                Objects.equals(getFax(), that.getFax()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getTelephone(), that.getTelephone()) &&
                Objects.equals(getMobile(), that.getMobile()) &&
                Objects.equals(getPager(), that.getPager()) &&
                Objects.equals(getTelephoneExtension(), that.getTelephoneExtension()) &&
                Objects.equals(getWebsite(), that.getWebsite()) &&
                Objects.equals(getEpfNo(), that.getEpfNo()) &&
                Objects.equals(getNicNo(), that.getNicNo()) &&
                Objects.equals(getAltEMail(), that.getAltEMail()) &&
                Objects.equals(getChaLicenseNo(), that.getChaLicenseNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddressBookSeq(), getFirstName(), getCountrySeq(), getMiddleInitials(), getLastName(), getTitle(), getDesignation(), getAddress1(), getAddress2(), getCity(), getState(), getZip(), getFax(), getEmail(), getTelephone(), getMobile(), getPager(), getTelephoneExtension(), getWebsite(), getEpfNo(), getNicNo(), getAltEMail(), getChaLicenseNo());
    }
}
