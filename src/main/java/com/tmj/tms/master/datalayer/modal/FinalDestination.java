package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        @NamedEntityGraph(name = "FinalDestination.default", attributeNodes = {
                @NamedAttributeNode("addressBook"),
                @NamedAttributeNode("location")
        }),
        @NamedEntityGraph(name = "FinalDestination.Search", attributeNodes = {
                @NamedAttributeNode("addressBook")
        }),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "final_destination")
public class FinalDestination {
    private Integer finalDestinationSeq;
    private Integer companyProfileSeq;
    private String destination;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer addressBookSeq;
    private Integer locationSeq;
    private Double latitude;
    private Double longitude;
    private Integer status;
    private String statusDescription;

    private AddressBook addressBook;
    private Location location;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "final_destination_seq", allocationSize = 1)
    @Column(name = "final_destination_seq", unique = true)
    public Integer getFinalDestinationSeq() {
        return finalDestinationSeq;
    }

    public void setFinalDestinationSeq(Integer finalDestinationSeq) {
        this.finalDestinationSeq = finalDestinationSeq;
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
    @Column(name = "final_destination_code", length = 500, nullable = false)
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    @Basic
    @Column(name = "location_seq")
    public Integer getLocationSeq() {
        return locationSeq;
    }

    public void setLocationSeq(Integer locationSeq) {
        this.locationSeq = locationSeq;
    }

    @Basic
    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_seq", insertable = false, updatable = false, nullable = false)
    public Location getLocation() {
        return location;
    }


    public void setLocation(Location location) {
        this.location = location;
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
        if (o == null || getClass() != o.getClass()) return false;
        FinalDestination that = (FinalDestination) o;
        return Objects.equals(finalDestinationSeq, that.finalDestinationSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(addressBookSeq, that.addressBookSeq) &&
                Objects.equals(locationSeq, that.locationSeq) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(finalDestinationSeq, companyProfileSeq, destination, addressBookSeq, locationSeq, latitude, longitude, status);
    }
}
