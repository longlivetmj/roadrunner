package com.tmj.tms.transport.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import org.hibernate.annotations.Where;
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
        @NamedEntityGraph(name = "TransportBookingVehicle.default"),
        @NamedEntityGraph(name = "TransportBookingVehicle.assignVehicle", attributeNodes = {
                @NamedAttributeNode("transportCompany"),
                @NamedAttributeNode("vehicle"),
                @NamedAttributeNode("driver"),
                @NamedAttributeNode("helper")
        }),
})
@Entity
@Where(clause = "status != 0")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transport_booking_vehicle")
public class TransportBookingVehicle {

    private Integer transportBookingVehicleSeq;

    @NotNull
    private Integer transportBookingSeq;

    @NotNull
    private Integer transportCompanySeq;

    @NotNull
    private Integer vehicleSeq;

    @NotNull
    private Integer driverSeq;

    private Integer helperSeq;

    @Size(max = 500)
    private String remarks;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private Stakeholder transportCompany;
    private Vehicle vehicle;
    private Employee driver;
    private Employee helper;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "transport_booking_vehicle_seq", allocationSize = 1)
    @Column(name = "transport_booking_vehicle_seq", nullable = false, unique = true)
    public Integer getTransportBookingVehicleSeq() {
        return transportBookingVehicleSeq;
    }

    public void setTransportBookingVehicleSeq(Integer transportBookingVehicleSeq) {
        this.transportBookingVehicleSeq = transportBookingVehicleSeq;
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
    @Column(name = "transport_company_seq", nullable = false)
    public Integer getTransportCompanySeq() {
        return transportCompanySeq;
    }

    public void setTransportCompanySeq(Integer transportCompanySeq) {
        this.transportCompanySeq = transportCompanySeq;
    }

    @Basic
    @Column(name = "vehicle_seq", nullable = false)
    public Integer getVehicleSeq() {
        return vehicleSeq;
    }

    public void setVehicleSeq(Integer vehicleSeq) {
        this.vehicleSeq = vehicleSeq;
    }

    @Basic
    @Column(name = "driver_seq", nullable = false)
    public Integer getDriverSeq() {
        return driverSeq;
    }

    public void setDriverSeq(Integer driverSeq) {
        this.driverSeq = driverSeq;
    }

    @Basic
    @Column(name = "helper_seq")
    public Integer getHelperSeq() {
        return helperSeq;
    }

    public void setHelperSeq(Integer helperSeq) {
        this.helperSeq = helperSeq;
    }

    @Basic
    @Column(name = "remarks", length = 500)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    @Column(name = "last_modified_by", nullable = false)
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
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_company_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(Stakeholder transportCompany) {
        this.transportCompany = transportCompany;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_seq", insertable = false, updatable = false)
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_seq", referencedColumnName = "employee_seq", insertable = false, updatable = false)
    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "helper_seq", referencedColumnName = "employee_seq", insertable = false, updatable = false)
    public Employee getHelper() {
        return helper;
    }

    public void setHelper(Employee helper) {
        this.helper = helper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportBookingVehicle that = (TransportBookingVehicle) o;
        return Objects.equals(transportBookingVehicleSeq, that.transportBookingVehicleSeq) &&
                Objects.equals(transportBookingSeq, that.transportBookingSeq) &&
                Objects.equals(transportCompanySeq, that.transportCompanySeq) &&
                Objects.equals(vehicleSeq, that.vehicleSeq) &&
                Objects.equals(driverSeq, that.driverSeq) &&
                Objects.equals(helperSeq, that.helperSeq) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportBookingVehicleSeq, transportBookingSeq, transportCompanySeq, vehicleSeq, driverSeq, helperSeq, remarks, status);
    }
}
