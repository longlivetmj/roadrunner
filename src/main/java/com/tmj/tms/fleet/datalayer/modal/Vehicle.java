package com.tmj.tms.fleet.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.utility.FuelType;
import com.tmj.tms.fleet.utility.PaymentMode;
import com.tmj.tms.fleet.utility.VehicleActivation;
import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "Vehicle.default"),
        @NamedEntityGraph(name = "Vehicle.createVehicle", attributeNodes = {
                @NamedAttributeNode("stakeholder"),
                @NamedAttributeNode("vehicleManufacturer"),
                @NamedAttributeNode("vehicleModal"),
                @NamedAttributeNode("country"),
                @NamedAttributeNode("tyreSize"),
                @NamedAttributeNode("defaultDriver"),
                @NamedAttributeNode("secondaryDriver"),
                @NamedAttributeNode("defaultHelper"),
                @NamedAttributeNode("secondaryHelper"),
                @NamedAttributeNode("basedLocation"),
                @NamedAttributeNode("vehicleVehicleTypeList")
        }),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "vehicle")
public class Vehicle {

    private Integer vehicleSeq;
    private String vehicleCode;
    private String vehicleNo;
    private Integer companyProfileSeq;
    private Integer stakeholderSeq;
    private String engineNo;
    private String chassisNo;
    private Integer currentMileage;
    private Date mileageSyncedDate;
    private Integer yearOfManufacture;
    private Integer vehicleManufacturerSeq;
    private Integer vehicleModalSeq;
    private Integer countrySeq;
    private Integer tyreSizeSeq;
    private Integer engineCapacity;
    private Integer fuelType;
    private Integer fuelConsumption;
    private Integer defaultDriverSeq;
    private Integer secondaryDriverSeq;
    private Integer defaultHelperSeq;
    private Integer secondaryHelperSeq;
    private Integer gpsServiceProviderSeq;
    private String gpsTerminalKey;
    private Integer vehiclePhoto;
    private Integer vehicleRegistration;
    private Integer basedLocationSeq;
    private Integer paymentMode;
    private Double paymentModeValue;
    private Integer vehicleActivation;
    private String vehicleActivationBy;
    private Date vehicleActivationDate;
    private Integer noOfTyres;

    private List<VehicleVehicleType> vehicleVehicleTypeList;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private Integer financeIntegration;
    private Long financeIntegrationKey;

    private Stakeholder stakeholder;
    private VehicleManufacturer vehicleManufacturer;
    private VehicleModal vehicleModal;
    private Country country;
    private TyreSize tyreSize;
    private Employee defaultDriver;
    private Employee secondaryDriver;
    private Employee defaultHelper;
    private Employee secondaryHelper;
    private FinalDestination basedLocation;

    private String fuelTypeDescription;
    private String paymentModeDescription;
    private String vehicleActivationDescription;
    private String statusDescription;

    private List<Integer> vehicleTypeSeqList;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "vehicle_seq", allocationSize = 1)
    @Column(name = "vehicle_seq", nullable = false, unique = true)
    public Integer getVehicleSeq() {
        return vehicleSeq;
    }

    public void setVehicleSeq(Integer vehicleSeq) {
        this.vehicleSeq = vehicleSeq;
    }

    @Basic
    @Column(name = "vehicle_code", nullable = false, length = 20)
    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    @Basic
    @Column(name = "vehicle_no", nullable = false, length = 20)
    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
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
    @Column(name = "stakeholder_seq", nullable = false)
    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
    }

    @Basic
    @Column(name = "engine_no", nullable = false, length = 200)
    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    @Basic
    @Column(name = "chassis_no", nullable = false, length = 200)
    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    @Basic
    @Column(name = "current_mileage", nullable = false)
    public Integer getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(Integer currentMileage) {
        this.currentMileage = currentMileage;
    }

    @Basic
    @Column(name = "mileage_synced_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getMileageSyncedDate() {
        return mileageSyncedDate;
    }

    public void setMileageSyncedDate(Date mileageSyncedDate) {
        this.mileageSyncedDate = mileageSyncedDate;
    }

    @Basic
    @Column(name = "year_of_manufacture", nullable = false)
    public Integer getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    @Basic
    @Column(name = "vehicle_manufacturer_seq", nullable = false)
    public Integer getVehicleManufacturerSeq() {
        return vehicleManufacturerSeq;
    }

    public void setVehicleManufacturerSeq(Integer vehicleManufacturerSeq) {
        this.vehicleManufacturerSeq = vehicleManufacturerSeq;
    }

    @Basic
    @Column(name = "vehicle_modal_seq", nullable = false)
    public Integer getVehicleModalSeq() {
        return vehicleModalSeq;
    }

    public void setVehicleModalSeq(Integer vehicleModalSeq) {
        this.vehicleModalSeq = vehicleModalSeq;
    }

    @Basic
    @Column(name = "country_seq", nullable = false)
    public Integer getCountrySeq() {
        return countrySeq;
    }

    public void setCountrySeq(Integer countrySeq) {
        this.countrySeq = countrySeq;
    }

    @Basic
    @Column(name = "tyre_size_seq", nullable = false)
    public Integer getTyreSizeSeq() {
        return tyreSizeSeq;
    }

    public void setTyreSizeSeq(Integer tyreSizeSeq) {
        this.tyreSizeSeq = tyreSizeSeq;
    }

    @Basic
    @Column(name = "engine_capacity", nullable = false)
    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    @Basic
    @Column(name = "fuel_type", nullable = false)
    public Integer getFuelType() {
        return fuelType;
    }

    public void setFuelType(Integer fuelType) {
        this.fuelType = fuelType;
        if (fuelType != null) {
            this.setFuelTypeDescription(FuelType.findOne(fuelType).getFuelTypeDescription());
        }
    }

    @Basic
    @Column(name = "fuel_consumption", nullable = false)
    public Integer getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Integer fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    @Basic
    @Column(name = "default_driver_seq", nullable = false)
    public Integer getDefaultDriverSeq() {
        return defaultDriverSeq;
    }

    public void setDefaultDriverSeq(Integer defaultDriverSeq) {
        this.defaultDriverSeq = defaultDriverSeq;
    }

    @Basic
    @Column(name = "secondary_driver_seq")
    public Integer getSecondaryDriverSeq() {
        return secondaryDriverSeq;
    }

    public void setSecondaryDriverSeq(Integer secondaryDriverSeq) {
        this.secondaryDriverSeq = secondaryDriverSeq;
    }

    @Basic
    @Column(name = "default_helper_seq")
    public Integer getDefaultHelperSeq() {
        return defaultHelperSeq;
    }

    public void setDefaultHelperSeq(Integer defaultHelperSeq) {
        this.defaultHelperSeq = defaultHelperSeq;
    }

    @Basic
    @Column(name = "secondary_helper_seq")
    public Integer getSecondaryHelperSeq() {
        return secondaryHelperSeq;
    }

    public void setSecondaryHelperSeq(Integer secondaryHelperSeq) {
        this.secondaryHelperSeq = secondaryHelperSeq;
    }

    @Basic
    @Column(name = "gps_service_provider_seq")
    public Integer getGpsServiceProviderSeq() {
        return gpsServiceProviderSeq;
    }

    public void setGpsServiceProviderSeq(Integer gpsServiceProviderSeq) {
        this.gpsServiceProviderSeq = gpsServiceProviderSeq;
    }

    @Basic
    @Column(name = "gps_terminal_key")
    public String getGpsTerminalKey() {
        return gpsTerminalKey;
    }

    public void setGpsTerminalKey(String gpsTerminalKey) {
        this.gpsTerminalKey = gpsTerminalKey;
    }

    @Basic
    @Column(name = "vehicle_photo")
    public Integer getVehiclePhoto() {
        return vehiclePhoto;
    }

    public void setVehiclePhoto(Integer vehiclePhoto) {
        this.vehiclePhoto = vehiclePhoto;
    }

    @Basic
    @Column(name = "vehicle_registration")
    public Integer getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(Integer vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    @Basic
    @Column(name = "based_location_seq")
    public Integer getBasedLocationSeq() {
        return basedLocationSeq;
    }

    public void setBasedLocationSeq(Integer basedLocationSeq) {
        this.basedLocationSeq = basedLocationSeq;
    }

    @Basic
    @Column(name = "payment_mode")
    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
        if (paymentMode != null) {
            this.setPaymentModeDescription(PaymentMode.findOne(paymentMode).getPaymentModeDescription());
        }
    }

    @Basic
    @Column(name = "payment_mode_value")
    public Double getPaymentModeValue() {
        return paymentModeValue;
    }

    public void setPaymentModeValue(Double paymentModeValue) {
        this.paymentModeValue = paymentModeValue;
    }

    @Basic
    @Column(name = "vehicle_activation", nullable = false)
    public Integer getVehicleActivation() {
        return vehicleActivation;
    }

    public void setVehicleActivation(Integer vehicleActivation) {
        this.vehicleActivation = vehicleActivation;
        if (vehicleActivation != null) {
            this.setVehicleActivationDescription(VehicleActivation.findOne(vehicleActivation).getVehicleActivationDescription());
        }
    }

    @Basic
    @Column(name = "vehicle_activation_by", nullable = false)
    public String getVehicleActivationBy() {
        return vehicleActivationBy;
    }

    public void setVehicleActivationBy(String vehicleActivationBy) {
        this.vehicleActivationBy = vehicleActivationBy;
    }

    @Basic
    @Column(name = "vehicle_activation_date", nullable = false)
    public Date getVehicleActivationDate() {
        return vehicleActivationDate;
    }

    public void setVehicleActivationDate(Date vehicleActivationDate) {
        this.vehicleActivationDate = vehicleActivationDate;
    }

    @Basic
    @Column(name = "no_of_tyres")
    public Integer getNoOfTyres() {
        return noOfTyres;
    }

    public void setNoOfTyres(Integer noOfTyres) {
        this.noOfTyres = noOfTyres;
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
        if (status != null) {
            this.setStatusDescription(MasterDataStatus.findOne(status).getStatus());
        }
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(Stakeholder stakeholder) {
        this.stakeholder = stakeholder;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_manufacturer_seq", insertable = false, updatable = false)
    public VehicleManufacturer getVehicleManufacturer() {
        return vehicleManufacturer;
    }

    public void setVehicleManufacturer(VehicleManufacturer vehicleManufacturer) {
        this.vehicleManufacturer = vehicleManufacturer;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_modal_seq", insertable = false, updatable = false)
    public VehicleModal getVehicleModal() {
        return vehicleModal;
    }

    public void setVehicleModal(VehicleModal vehicleModal) {
        this.vehicleModal = vehicleModal;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_Seq", insertable = false, updatable = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tyre_size_seq", insertable = false, updatable = false)
    public TyreSize getTyreSize() {
        return tyreSize;
    }

    public void setTyreSize(TyreSize tyreSize) {
        this.tyreSize = tyreSize;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_driver_seq", referencedColumnName = "employee_seq", insertable = false, updatable = false)
    public Employee getDefaultDriver() {
        return defaultDriver;
    }

    public void setDefaultDriver(Employee defaultDriver) {
        this.defaultDriver = defaultDriver;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_driver_seq", referencedColumnName = "employee_seq", insertable = false, updatable = false)
    public Employee getSecondaryDriver() {
        return secondaryDriver;
    }

    public void setSecondaryDriver(Employee secondaryDriver) {
        this.secondaryDriver = secondaryDriver;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_helper_seq", referencedColumnName = "employee_seq", insertable = false, updatable = false)
    public Employee getDefaultHelper() {
        return defaultHelper;
    }

    public void setDefaultHelper(Employee defaultHelper) {
        this.defaultHelper = defaultHelper;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_helper_seq", referencedColumnName = "employee_seq", insertable = false, updatable = false)
    public Employee getSecondaryHelper() {
        return secondaryHelper;
    }

    public void setSecondaryHelper(Employee secondaryHelper) {
        this.secondaryHelper = secondaryHelper;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "based_location_seq", insertable = false, updatable = false)
    public FinalDestination getBasedLocation() {
        return basedLocation;
    }

    public void setBasedLocation(FinalDestination basedLocation) {
        this.basedLocation = basedLocation;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "vehicle_seq", nullable = false)
    public List<VehicleVehicleType> getVehicleVehicleTypeList() {
        return vehicleVehicleTypeList;
    }

    public void setVehicleVehicleTypeList(List<VehicleVehicleType> vehicleVehicleTypeList) {
        this.vehicleVehicleTypeList = vehicleVehicleTypeList;
    }

    @Transient
    public String getFuelTypeDescription() {
        return fuelTypeDescription;
    }

    public void setFuelTypeDescription(String fuelTypeDescription) {
        this.fuelTypeDescription = fuelTypeDescription;
    }

    @Transient
    public String getPaymentModeDescription() {
        return paymentModeDescription;
    }

    public void setPaymentModeDescription(String paymentModeDescription) {
        this.paymentModeDescription = paymentModeDescription;
    }

    @Transient
    public String getVehicleActivationDescription() {
        return vehicleActivationDescription;
    }

    public void setVehicleActivationDescription(String vehicleActivationDescription) {
        this.vehicleActivationDescription = vehicleActivationDescription;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Transient
    public List<Integer> getVehicleTypeSeqList() {
        return vehicleTypeSeqList;
    }

    public void setVehicleTypeSeqList(List<Integer> vehicleTypeSeqList) {
        this.vehicleTypeSeqList = vehicleTypeSeqList;
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
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleSeq, vehicle.vehicleSeq) &&
                Objects.equals(vehicleCode, vehicle.vehicleCode) &&
                Objects.equals(companyProfileSeq, vehicle.companyProfileSeq) &&
                Objects.equals(stakeholderSeq, vehicle.stakeholderSeq) &&
                Objects.equals(engineNo, vehicle.engineNo) &&
                Objects.equals(chassisNo, vehicle.chassisNo) &&
                Objects.equals(currentMileage, vehicle.currentMileage) &&
                Objects.equals(mileageSyncedDate, vehicle.mileageSyncedDate) &&
                Objects.equals(yearOfManufacture, vehicle.yearOfManufacture) &&
                Objects.equals(vehicleManufacturerSeq, vehicle.vehicleManufacturerSeq) &&
                Objects.equals(vehicleModalSeq, vehicle.vehicleModalSeq) &&
                Objects.equals(countrySeq, vehicle.countrySeq) &&
                Objects.equals(tyreSizeSeq, vehicle.tyreSizeSeq) &&
                Objects.equals(engineCapacity, vehicle.engineCapacity) &&
                Objects.equals(fuelType, vehicle.fuelType) &&
                Objects.equals(fuelConsumption, vehicle.fuelConsumption) &&
                Objects.equals(defaultDriverSeq, vehicle.defaultDriverSeq) &&
                Objects.equals(secondaryDriverSeq, vehicle.secondaryDriverSeq) &&
                Objects.equals(defaultHelperSeq, vehicle.defaultHelperSeq) &&
                Objects.equals(secondaryHelperSeq, vehicle.secondaryHelperSeq) &&
                Objects.equals(gpsServiceProviderSeq, vehicle.gpsServiceProviderSeq) &&
                Objects.equals(gpsTerminalKey, vehicle.gpsTerminalKey) &&
                Objects.equals(vehiclePhoto, vehicle.vehiclePhoto) &&
                Objects.equals(vehicleRegistration, vehicle.vehicleRegistration) &&
                Objects.equals(basedLocationSeq, vehicle.basedLocationSeq) &&
                Objects.equals(paymentMode, vehicle.paymentMode) &&
                Objects.equals(vehicleNo, vehicle.vehicleNo) &&
                Objects.equals(paymentModeValue, vehicle.paymentModeValue) &&
                Objects.equals(vehicleActivation, vehicle.vehicleActivation) &&
                Objects.equals(vehicleActivationBy, vehicle.vehicleActivationBy) &&
                Objects.equals(vehicleActivationDate, vehicle.vehicleActivationDate) &&
                Objects.equals(status, vehicle.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleSeq, vehicleCode, companyProfileSeq, stakeholderSeq, engineNo, chassisNo, currentMileage, mileageSyncedDate, vehicleNo, yearOfManufacture, vehicleManufacturerSeq, vehicleModalSeq, countrySeq, tyreSizeSeq, engineCapacity, fuelType, fuelConsumption, defaultDriverSeq, secondaryDriverSeq, defaultHelperSeq, secondaryHelperSeq, gpsServiceProviderSeq, gpsTerminalKey, vehiclePhoto, vehicleRegistration, basedLocationSeq, paymentMode, paymentModeValue, vehicleActivation, vehicleActivationBy, vehicleActivationDate, status);
    }
}
