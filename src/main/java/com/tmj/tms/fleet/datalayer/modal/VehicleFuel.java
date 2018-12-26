package com.tmj.tms.fleet.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.utility.FuelType;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.utility.StakeholderCashType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "VehicleFuel.default"),
        @NamedEntityGraph(name = "VehicleFuel.create", attributeNodes = {
                @NamedAttributeNode("vehicle"),
                @NamedAttributeNode("employee"),
                @NamedAttributeNode("supplier"),
                @NamedAttributeNode("stakeholder"),
                @NamedAttributeNode("fuelTypeVariant")
        }),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "vehicle_fuel")
public class VehicleFuel {

    private Integer vehicleFuelSeq;
    private Integer vehicleSeq;
    private Integer employeeSeq;
    private Integer companyProfileSeq;
    private Integer supplierSeq;
    private Integer fuelType;
    private Integer fuelTypeVariantSeq;
    private Integer stakeholderSeq;
    private Integer meterReading;
    private Double unitPrice;
    private Double quantity;
    private Double amount;
    private Date transactionDate;
    private Integer stakeholderCashTypeSeq;
    private String remarks;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private Integer financeIntegration;
    private Long financeIntegrationKey;

    private Vehicle vehicle;
    private Employee employee;
    private Stakeholder supplier;
    private Stakeholder stakeholder;
    private FuelTypeVariant fuelTypeVariant;

    private String stakeholderCashType;
    private String statusDescription;
    private String fuelTypeDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "vehicle_fuel_seq", allocationSize = 1)
    @Column(name = "vehicle_fuel_seq", nullable = false, unique = true)
    public Integer getVehicleFuelSeq() {
        return vehicleFuelSeq;
    }

    public void setVehicleFuelSeq(Integer vehicleFuelSeq) {
        this.vehicleFuelSeq = vehicleFuelSeq;
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
    @Column(name = "employee_seq", nullable = false)
    public Integer getEmployeeSeq() {
        return employeeSeq;
    }

    public void setEmployeeSeq(Integer employeeSeq) {
        this.employeeSeq = employeeSeq;
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
    @Column(name = "supplier_seq", nullable = false)
    public Integer getSupplierSeq() {
        return supplierSeq;
    }

    public void setSupplierSeq(Integer supplierSeq) {
        this.supplierSeq = supplierSeq;
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
    @Column(name = "meter_reading", nullable = false)
    public Integer getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(Integer meterReading) {
        this.meterReading = meterReading;
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
    @Column(name = "quantity", nullable = false)
    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "amount", nullable = false)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "transaction_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Basic
    @Column(name = "stakeholder_cash_type_seq", nullable = false)
    public Integer getStakeholderCashTypeSeq() {
        return stakeholderCashTypeSeq;
    }

    public void setStakeholderCashTypeSeq(Integer stakeholderCashTypeSeq) {
        this.stakeholderCashTypeSeq = stakeholderCashTypeSeq;
        if (stakeholderCashTypeSeq != null) {
            this.setStakeholderCashType(StakeholderCashType.findOne(stakeholderCashTypeSeq).getStakeholderCashType());
        }
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
    @Column(name = "fuel_type_variant_seq", nullable = false)
    public Integer getFuelTypeVariantSeq() {
        return fuelTypeVariantSeq;
    }

    public void setFuelTypeVariantSeq(Integer fuelTypeVariantSeq) {
        this.fuelTypeVariantSeq = fuelTypeVariantSeq;
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
        if (status != null) {
            this.setStatusDescription(MasterDataStatus.findOne(status).getStatus());
        }
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_seq", insertable = false, updatable = false)
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_seq", insertable = false, updatable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getSupplier() {
        return supplier;
    }

    public void setSupplier(Stakeholder supplier) {
        this.supplier = supplier;
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
    @JoinColumn(name = "fuel_type_variant_seq", insertable = false, updatable = false)
    public FuelTypeVariant getFuelTypeVariant() {
        return fuelTypeVariant;
    }

    public void setFuelTypeVariant(FuelTypeVariant fuelTypeVariant) {
        this.fuelTypeVariant = fuelTypeVariant;
    }

    @Transient
    public String getStakeholderCashType() {
        return stakeholderCashType;
    }

    public void setStakeholderCashType(String stakeholderCashType) {
        this.stakeholderCashType = stakeholderCashType;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Transient
    public String getFuelTypeDescription() {
        return fuelTypeDescription;
    }

    public void setFuelTypeDescription(String fuelTypeDescription) {
        this.fuelTypeDescription = fuelTypeDescription;
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
        VehicleFuel that = (VehicleFuel) o;
        return Objects.equals(vehicleFuelSeq, that.vehicleFuelSeq) &&
                Objects.equals(vehicleSeq, that.vehicleSeq) &&
                Objects.equals(employeeSeq, that.employeeSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(supplierSeq, that.supplierSeq) &&
                Objects.equals(fuelType, that.fuelType) &&
                Objects.equals(fuelTypeVariantSeq, that.fuelTypeVariantSeq) &&
                Objects.equals(stakeholderSeq, that.stakeholderSeq) &&
                Objects.equals(meterReading, that.meterReading) &&
                Objects.equals(unitPrice, that.unitPrice) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(stakeholderCashTypeSeq, that.stakeholderCashTypeSeq) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleFuelSeq, vehicleSeq, employeeSeq, companyProfileSeq, supplierSeq, fuelType, fuelTypeVariantSeq, stakeholderSeq, meterReading, unitPrice, quantity, amount, transactionDate, stakeholderCashTypeSeq, remarks, status);
    }
}
