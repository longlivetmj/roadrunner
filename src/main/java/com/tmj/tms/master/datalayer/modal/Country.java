package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "Country.default", attributeNodes = {
                @NamedAttributeNode("currency"),
                @NamedAttributeNode("region")
        }),
        @NamedEntityGraph(name = "Country.search")
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "country")
public class Country implements Serializable {
    private Integer countrySeq;
    private String countryCode;
    private String countryName;
    private Integer regionSeq;
    private Integer currencySeq;
    private String iataCode;
    private Double latitude;
    private Double longitude;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private String statusDescription;

    private Currency currency;
    private Region region;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "country_seq", allocationSize = 1)
    @Column(name = "country_seq", nullable = false, precision = 0, unique = true)
    public Integer getCountrySeq() {
        return countrySeq;
    }

    public void setCountrySeq(Integer countrySeq) {
        this.countrySeq = countrySeq;
    }

    @Basic
    @Column(name = "country_code", nullable = false, length = 2, unique = true)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "country_name", nullable = false, length = 50)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Basic
    @Column(name = "region_seq")
    public Integer getRegionSeq() {
        return regionSeq;
    }

    public void setRegionSeq(Integer regionSeq) {
        this.regionSeq = regionSeq;
    }

    @Basic
    @Column(name = "currency_seq", nullable = false, precision = 0)
    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
    }

    @Basic
    @Column(name = "iata_code", length = 15)
    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
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

    @Basic
    @Column(name = "status", nullable = false, precision = 0)
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_seq", insertable = false, updatable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_seq", insertable = false, updatable = false)
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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
        if (!(o instanceof Country)) return false;
        Country country = (Country) o;
        return Objects.equals(getCountrySeq(), country.getCountrySeq()) &&
                Objects.equals(getCountryCode(), country.getCountryCode()) &&
                Objects.equals(getCountryName(), country.getCountryName()) &&
                Objects.equals(getRegionSeq(), country.getRegionSeq()) &&
                Objects.equals(getCurrencySeq(), country.getCurrencySeq()) &&
                Objects.equals(getIataCode(), country.getIataCode()) &&
                Objects.equals(getStatus(), country.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountrySeq(), getCountryCode(), getCountryName(), getRegionSeq(), getCurrencySeq(), getIataCode(), getStatus());
    }
}
