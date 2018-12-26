package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.utility.DurationType;
import com.tmj.tms.master.utility.ItemType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "item.default", attributeNodes = {
                @NamedAttributeNode("unit")
        })
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "item")
public class Item {

    private Integer itemSeq;
    private Integer companyProfileSeq;
    private Integer itemType;
    private String itemName;
    private String itemCode;
    private String itemDescription;

    private Integer unitSeq;
    private Double unitPrice;
    private Integer kmExpiration;
    private Integer durationExpiration;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private String statusDescription;
    private String itemTypeDescription;
    private String durationExpirationDescription;

    private Unit unit;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "item_seq", allocationSize = 1)
    @Column(name = "item_seq", unique = true)
    public Integer getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(Integer itemSeq) {
        this.itemSeq = itemSeq;
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
    @Column(name = "item_type", nullable = false)
    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
        if (itemType != null) {
            this.setItemTypeDescription(ItemType.findOne(itemType).getItemTypeDescription());
        }
    }

    @Basic
    @Column(name = "item_name", nullable = false, length = 100)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "item_code", nullable = false, length = 20)
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Basic
    @Column(name = "item_description", length = 300)
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Basic
    @Column(name = "unit_seq", nullable = false)
    public Integer getUnitSeq() {
        return unitSeq;
    }

    public void setUnitSeq(Integer unitSeq) {
        this.unitSeq = unitSeq;
    }

    @Basic
    @Column(name = "unit_price", nullable = false)
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "km_expiration")
    public Integer getKmExpiration() {
        return kmExpiration;
    }

    public void setKmExpiration(Integer kmExpiration) {
        this.kmExpiration = kmExpiration;
    }

    @Basic
    @Column(name = "duration_expiration")
    public Integer getDurationExpiration() {
        return durationExpiration;
    }

    public void setDurationExpiration(Integer durationExpiration) {
        this.durationExpiration = durationExpiration;
        if (durationExpiration != null) {
            this.setDurationExpirationDescription(DurationType.findOne(durationExpiration).getDurationExpirationDescription());
        }
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

    @Transient
    public String getItemTypeDescription() {
        return itemTypeDescription;
    }

    public void setItemTypeDescription(String itemTypeDescription) {
        this.itemTypeDescription = itemTypeDescription;
    }

    @Transient
    public String getDurationExpirationDescription() {
        return durationExpirationDescription;
    }

    public void setDurationExpirationDescription(String durationExpirationDescription) {
        this.durationExpirationDescription = durationExpirationDescription;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_seq", insertable = false, updatable = false)
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemSeq, item.itemSeq) &&
                Objects.equals(companyProfileSeq, item.companyProfileSeq) &&
                Objects.equals(itemType, item.itemType) &&
                Objects.equals(itemName, item.itemName) &&
                Objects.equals(itemCode, item.itemCode) &&
                Objects.equals(itemDescription, item.itemDescription) &&
                Objects.equals(unitSeq, item.unitSeq) &&
                Objects.equals(unitPrice, item.unitPrice) &&
                Objects.equals(kmExpiration, item.kmExpiration) &&
                Objects.equals(durationExpiration, item.durationExpiration) &&
                Objects.equals(status, item.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemSeq, companyProfileSeq, itemType, itemName, itemCode, itemDescription, unitSeq, unitPrice, kmExpiration, durationExpiration, status);
    }
}
