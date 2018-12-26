package com.tmj.tms.fleet.datalayer.modal;

import com.tmj.tms.master.datalayer.modal.VehicleType;

import javax.persistence.*;

@Entity
@Table(name = "vehicle_vehicle_type")
public class VehicleVehicleType {

    private Integer vehicleVehicleTypeSeq;
    private Integer vehicleSeq;
    private Integer vehicleTypeSeq;
    private Integer status;

    private VehicleType vehicleType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "vehicle_vehicle_type_seq", allocationSize = 1)
    @Column(name = "vehicle_vehicle_type_seq", nullable = false, unique = true)
    public Integer getVehicleVehicleTypeSeq() {
        return vehicleVehicleTypeSeq;
    }

    public void setVehicleVehicleTypeSeq(Integer vehicleVehicleTypeSeq) {
        this.vehicleVehicleTypeSeq = vehicleVehicleTypeSeq;
    }

    @Basic
    @Column(name = "vehicle_seq", nullable = false, insertable = false, updatable = false)
    public Integer getVehicleSeq() {
        return vehicleSeq;
    }

    public void setVehicleSeq(Integer vehicleSeq) {
        this.vehicleSeq = vehicleSeq;
    }

    @Basic
    @Column(name = "vehicle_type_seq", nullable = false)
    public Integer getVehicleTypeSeq() {
        return vehicleTypeSeq;
    }

    public void setVehicleTypeSeq(Integer vehicleTypeSeq) {
        this.vehicleTypeSeq = vehicleTypeSeq;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_type_seq", insertable = false, updatable = false)
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
