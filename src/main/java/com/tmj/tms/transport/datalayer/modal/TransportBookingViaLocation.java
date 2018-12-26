package com.tmj.tms.transport.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.PackageType;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.Unit;
import com.tmj.tms.transport.utility.DropPick;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;


@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "TransportBookingViaLocation.default"),
        @NamedEntityGraph(name = "TransportBookingViaLocation.createViaLocation", attributeNodes = {
                @NamedAttributeNode("viaLocation"),
                @NamedAttributeNode("packageType"),
                @NamedAttributeNode("consignee")
        }),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transport_booking_via_location")
public class TransportBookingViaLocation {

    private Integer transportBookingViaLocationSeq;
    private Integer transportBookingSeq;
    private Integer consigneeSeq;

    @NotNull
    private Integer viaLocationSeq;

    @NotNull
    @Size(max = 500)
    private String viaLocationAddress;

    @NotNull
    private Date requestedArrivalTime;
    private Date requestedDepartureTime;
    private Integer pieces;
    private Integer packageTypeSeq;
    private Double cbm;
    private Double weight;
    private Integer uowSeq;
    private Double volume;
    private Integer uovSeq;
    private String contactPerson;
    private String contactNo;
    private Integer dropPickStatus;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private FinalDestination viaLocation;
    private PackageType packageType;
    private Unit uow;
    private Unit uov;
    private Stakeholder consignee;
    private TransportBookingViaLocationFeedback transportBookingViaLocationFeedback;

    private String dropPickStatusDescription;
    private String statusDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "transport_booking_via_location_seq", allocationSize = 1)
    @Column(name = "transport_booking_via_location_seq", nullable = false, unique = true)
    public Integer getTransportBookingViaLocationSeq() {
        return transportBookingViaLocationSeq;
    }

    public void setTransportBookingViaLocationSeq(Integer transportBookingViaLocationSeq) {
        this.transportBookingViaLocationSeq = transportBookingViaLocationSeq;
    }

    @Basic
    @Column(name = "transport_booking_seq", nullable = false)
    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
    }

    @Basic
    @Column(name = "consignee_seq")
    public Integer getConsigneeSeq() {
        return consigneeSeq;
    }

    public void setConsigneeSeq(Integer consigneeSeq) {
        this.consigneeSeq = consigneeSeq;
    }

    @Basic
    @Column(name = "via_location_seq", nullable = false)
    public Integer getViaLocationSeq() {
        return viaLocationSeq;
    }

    public void setViaLocationSeq(Integer viaLocationSeq) {
        this.viaLocationSeq = viaLocationSeq;
    }

    @Basic
    @Column(name = "via_location_address", length = 500)
    public String getViaLocationAddress() {
        return viaLocationAddress;
    }

    public void setViaLocationAddress(String viaLocationAddress) {
        this.viaLocationAddress = viaLocationAddress;
    }

    @Basic
    @Column(name = "requested_arrival_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getRequestedArrivalTime() {
        return requestedArrivalTime;
    }

    public void setRequestedArrivalTime(Date requestedArrivalTime) {
        this.requestedArrivalTime = requestedArrivalTime;
    }

    @Basic
    @Column(name = "requested_departure_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getRequestedDepartureTime() {
        return requestedDepartureTime;
    }

    public void setRequestedDepartureTime(Date requestedDepartureTime) {
        this.requestedDepartureTime = requestedDepartureTime;
    }

    @Basic
    @Column(name = "pieces")
    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    @Basic
    @Column(name = "package_type_seq")
    public Integer getPackageTypeSeq() {
        return packageTypeSeq;
    }

    public void setPackageTypeSeq(Integer packageTypeSeq) {
        this.packageTypeSeq = packageTypeSeq;
    }

    @Basic
    @Column(name = "cbm")
    public Double getCbm() {
        return cbm;
    }

    public void setCbm(Double cbm) {
        this.cbm = cbm;
    }

    @Basic
    @Column(name = "weight")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "uow_seq")
    public Integer getUowSeq() {
        return uowSeq;
    }

    public void setUowSeq(Integer uowSeq) {
        this.uowSeq = uowSeq;
    }

    @Basic
    @Column(name = "volume")
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Basic
    @Column(name = "uov_seq")
    public Integer getUovSeq() {
        return uovSeq;
    }

    public void setUovSeq(Integer uovSeq) {
        this.uovSeq = uovSeq;
    }

    @Basic
    @Column(name = "contact_person")
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Basic
    @Column(name = "contact_no")
    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @Basic
    @Column(name = "drop_pick_status")
    public Integer getDropPickStatus() {
        return dropPickStatus;
    }

    public void setDropPickStatus(Integer dropPickStatus) {
        this.dropPickStatus = dropPickStatus;
        if (dropPickStatus != null) {
            this.setDropPickStatusDescription(DropPick.findOne(dropPickStatus).getDropPickStatusDescription());
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

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        if (status != null) {
            this.setStatusDescription(MasterDataStatus.findOne(status).getStatus());
        }
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "via_location_seq", referencedColumnName = "final_destination_seq", insertable = false, updatable = false)
    public FinalDestination getViaLocation() {
        return viaLocation;
    }

    public void setViaLocation(FinalDestination viaLocation) {
        this.viaLocation = viaLocation;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "package_type_seq", insertable = false, updatable = false)
    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "uow_seq", referencedColumnName = "unit_seq", insertable = false, updatable = false)
    public Unit getUow() {
        return uow;
    }

    public void setUow(Unit uow) {
        this.uow = uow;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "uov_seq", referencedColumnName = "unit_seq", insertable = false, updatable = false)
    public Unit getUov() {
        return uov;
    }

    public void setUov(Unit uov) {
        this.uov = uov;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "consignee_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getConsignee() {
        return consignee;
    }

    public void setConsignee(Stakeholder consignee) {
        this.consignee = consignee;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_booking_via_location_seq", insertable = false, updatable = false)
    public TransportBookingViaLocationFeedback getTransportBookingViaLocationFeedback() {
        return transportBookingViaLocationFeedback;
    }

    public void setTransportBookingViaLocationFeedback(TransportBookingViaLocationFeedback transportBookingViaLocationFeedback) {
        this.transportBookingViaLocationFeedback = transportBookingViaLocationFeedback;
    }

    @Transient
    public String getDropPickStatusDescription() {
        return dropPickStatusDescription;
    }

    public void setDropPickStatusDescription(String dropPickStatusDescription) {
        this.dropPickStatusDescription = dropPickStatusDescription;
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
        TransportBookingViaLocation that = (TransportBookingViaLocation) o;
        return Objects.equals(transportBookingViaLocationSeq, that.transportBookingViaLocationSeq) &&
                Objects.equals(transportBookingSeq, that.transportBookingSeq) &&
                Objects.equals(consigneeSeq, that.consigneeSeq) &&
                Objects.equals(viaLocationSeq, that.viaLocationSeq) &&
                Objects.equals(viaLocationAddress, that.viaLocationAddress) &&
                Objects.equals(requestedArrivalTime, that.requestedArrivalTime) &&
                Objects.equals(requestedDepartureTime, that.requestedDepartureTime) &&
                Objects.equals(pieces, that.pieces) &&
                Objects.equals(packageTypeSeq, that.packageTypeSeq) &&
                Objects.equals(cbm, that.cbm) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(uowSeq, that.uowSeq) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(uovSeq, that.uovSeq) &&
                Objects.equals(contactPerson, that.contactPerson) &&
                Objects.equals(contactNo, that.contactNo) &&
                Objects.equals(dropPickStatus, that.dropPickStatus) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportBookingViaLocationSeq, transportBookingSeq, consigneeSeq, viaLocationSeq, viaLocationAddress, requestedArrivalTime, requestedDepartureTime, pieces, packageTypeSeq, cbm, weight, uowSeq, volume, uovSeq, contactPerson, contactNo, dropPickStatus, status);
    }
}
