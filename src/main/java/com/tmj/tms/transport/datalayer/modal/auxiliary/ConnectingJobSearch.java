package com.tmj.tms.transport.datalayer.modal.auxiliary;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.utility.NumberFormatter;

import javax.persistence.*;
import java.util.Date;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "ConnectingJobSearch.default", attributeNodes = {
                @NamedAttributeNode("pickupLocation")
        })
})
@Entity
@Table(name = "transport_booking")
public class ConnectingJobSearch {

    private Integer transportBookingSeq;

    private Integer companyProfileSeq;

    private String prefix;

    private Date requestedArrivalTime;

    private Integer pickupLocationSeq;

    private Integer currentStatus;

    private FinalDestination pickupLocation;

    private String bookingNo;

    @Id
    @Column(name = "transport_booking_seq", nullable = false, unique = true)
    public Integer getTransportBookingSeq() {
        return transportBookingSeq;
    }

    public void setTransportBookingSeq(Integer transportBookingSeq) {
        this.transportBookingSeq = transportBookingSeq;
        if (transportBookingSeq != null) {
            this.setBookingNo(NumberFormatter.convertToSixDigit(transportBookingSeq));
        }
    }

    @Basic
    @Column(name = "company_profile_seq", nullable = false, updatable = false)
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "prefix", nullable = false, updatable = false)
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "requested_arrival_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getRequestedArrivalTime() {
        return requestedArrivalTime;
    }

    public void setRequestedArrivalTime(Date requestedArrivalTime) {
        this.requestedArrivalTime = requestedArrivalTime;
    }

    @Basic
    @Column(name = "pickup_location_seq", nullable = false)
    public Integer getPickupLocationSeq() {
        return pickupLocationSeq;
    }

    public void setPickupLocationSeq(Integer pickupLocationSeq) {
        this.pickupLocationSeq = pickupLocationSeq;
    }

    @Transient
    public String getBookingNo() {
        return this.getPrefix() + bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    @Basic
    @Column(name = "current_status", nullable = false)
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_location_seq", referencedColumnName = "final_destination_seq", insertable = false, updatable = false)
    public FinalDestination getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(FinalDestination pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
}
