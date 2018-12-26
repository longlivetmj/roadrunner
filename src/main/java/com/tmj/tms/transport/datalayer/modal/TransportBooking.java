package com.tmj.tms.transport.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.finance.datalayer.modal.ExpenseVoucher;
import com.tmj.tms.finance.datalayer.modal.LocalInvoice;
import com.tmj.tms.fleet.utility.PaymentMode;
import com.tmj.tms.master.datalayer.modal.*;
import com.tmj.tms.master.utility.StakeholderCashType;
import com.tmj.tms.utility.NumberFormatter;
import com.tmj.tms.utility.TrueOrFalse;
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
import java.util.List;
import java.util.Objects;


@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "TransportBooking.default"),
        @NamedEntityGraph(name = "TransportBooking.forGoogleMaps", attributeNodes = {
                @NamedAttributeNode("pickupLocation"),
                @NamedAttributeNode("deliveryLocation")
        }),
        @NamedEntityGraph(name = "TransportBooking.localInvoice", attributeNodes = {
                @NamedAttributeNode("job")
        }),
        @NamedEntityGraph(name = "TransportBooking.createBooking", attributeNodes = {
                @NamedAttributeNode("customer"),
                @NamedAttributeNode("shipper"),
                @NamedAttributeNode("consignee"),
                @NamedAttributeNode("invoiceCustomer"),
                @NamedAttributeNode("job"),
                @NamedAttributeNode("packageType"),
                @NamedAttributeNode("vehicleType"),
                @NamedAttributeNode("containerType"),
                @NamedAttributeNode("pickupLocation"),
                @NamedAttributeNode("deliveryLocation"),
                @NamedAttributeNode("actualStartLocation"),
                @NamedAttributeNode("actualEndLocation"),
                @NamedAttributeNode("transportBookingStatus")
        }),
        @NamedEntityGraph(name = "TransportBooking.vehicleAssignment", attributeNodes = {
                @NamedAttributeNode("customer"),
                @NamedAttributeNode("shipper"),
                @NamedAttributeNode("consignee"),
                @NamedAttributeNode("invoiceCustomer"),
                @NamedAttributeNode("job"),
                @NamedAttributeNode("packageType"),
                @NamedAttributeNode("vehicleType"),
                @NamedAttributeNode("containerType"),
                @NamedAttributeNode("pickupLocation"),
                @NamedAttributeNode("deliveryLocation"),
                @NamedAttributeNode("transportBookingStatus"),
                @NamedAttributeNode("transportBookingVehicleList")
        }),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transport_booking")
public class TransportBooking {

    private Integer transportBookingSeq;

    @NotNull
    private Integer companyProfileSeq;

    private String prefix;

    @NotNull
    private Integer departmentSeq;

    @NotNull
    private Integer moduleSeq;

    @NotNull
    private Integer customerSeq;

    private Integer jobSeq;

    @NotNull
    private Integer invoiceCustomerSeq;

    @NotNull
    private Integer shipperSeq;

    private Integer consigneeSeq;

    @Size(max = 500)
    private String customerReferenceNo;

    @NotNull
    private Integer invoiceStatus;
    private Integer pieces;
    private Integer packageTypeSeq;
    private Double cbm;
    private Double weight;
    private Integer uowSeq;
    private Double volume;
    private Integer uovSeq;

    @NotNull
    private Integer vehicleTypeSeq;

    private Integer containerTypeSeq;

    @NotNull
    private Integer pickupLocationSeq;

    @NotNull
    private Date requestedArrivalTime;

    @Size(max = 200)
    private String pickupContactPerson;

    @Size(max = 50)
    private String pickupContactNo;

    @Size(max = 200)
    private String deliveryContactPerson;

    @Size(max = 50)
    private String deliveryContactNo;

    @NotNull
    private Integer deliveryLocationSeq;

    @NotNull
    private Date requestedDeliveryTime;

    @Size(max = 500)
    private String remarks;

    @Size(max = 500)
    private String pickupLocationAddress;

    @Size(max = 500)
    private String deliveryLocationAddress;

    @NotNull
    private Integer paymentMode;

    private String humanReadableEta;

    private Integer etaInMinutes;

    @NotNull
    private Integer cashOrCredit;
    private Double proposedTransportCharge;
    private Double estimatedKm;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer currentStatus;

    private Integer actualStartLocationSeq;
    private Integer actualEndLocationSeq;

    private List<TransportBookingViaLocation> transportBookingViaLocationList;

    private Department department;
    private Stakeholder customer;
    private Stakeholder shipper;
    private Stakeholder consignee;
    private Stakeholder invoiceCustomer;
    private Job job;
    private PackageType packageType;
    private Unit uow;
    private Unit uov;
    private VehicleType vehicleType;
    private ContainerType containerType;
    private FinalDestination pickupLocation;
    private FinalDestination deliveryLocation;
    private TransportBookingStatus transportBookingStatus;
    private List<TransportBookingVehicle> transportBookingVehicleList;
    private TransportBookingFeedback transportBookingFeedback;
    private FinalDestination actualStartLocation;
    private FinalDestination actualEndLocation;

    private String bookingNo;
    private String invoiceStatusDescription;
    private String paymentModeDescription;
    private String cashOrCreditDescription;
    private String viaLocationString;

    private Double revenue;
    private Double cost;

    private TransportBookingVehicle transportBookingVehicleTemp;
    private TransportBookingFeedback transportBookingFeedbackTemp;
    private List<TransportBookingViaLocation> transportBookingViaLocationListTemp;

    private List<LocalInvoice> localInvoiceList;
    private List<ExpenseVoucher> expenseVoucherList;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "transport_booking_seq", allocationSize = 1)
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
    @Column(name = "department_seq", nullable = false)
    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
    }

    @Basic
    @Column(name = "module_seq", nullable = false, updatable = false)
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @Basic
    @Column(name = "customer_seq", nullable = false)
    public Integer getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(Integer customerSeq) {
        this.customerSeq = customerSeq;
    }

    @Basic
    @Column(name = "prefix", updatable = false)
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "job_seq", nullable = false, updatable = false)
    public Integer getJobSeq() {
        return jobSeq;
    }

    public void setJobSeq(Integer jobSeq) {
        this.jobSeq = jobSeq;
    }

    @Basic
    @Column(name = "invoice_customer_seq", nullable = false)
    public Integer getInvoiceCustomerSeq() {
        return invoiceCustomerSeq;
    }

    public void setInvoiceCustomerSeq(Integer invoiceCustomerSeq) {
        this.invoiceCustomerSeq = invoiceCustomerSeq;
    }

    @Basic
    @Column(name = "shipper_seq", nullable = false)
    public Integer getShipperSeq() {
        return shipperSeq;
    }

    public void setShipperSeq(Integer shipperSeq) {
        this.shipperSeq = shipperSeq;
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
    @Column(name = "customer_reference_no", length = 500)
    public String getCustomerReferenceNo() {
        return customerReferenceNo;
    }

    public void setCustomerReferenceNo(String customerReferenceNo) {
        this.customerReferenceNo = customerReferenceNo;
    }

    @Basic
    @Column(name = "invoice_status", nullable = false)
    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
        if (invoiceStatus != null) {
            this.setInvoiceStatusDescription(TrueOrFalse.findOne(invoiceStatus).getTrueOrFalse());
        }
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
    @Column(name = "vehicle_type_seq", nullable = false)
    public Integer getVehicleTypeSeq() {
        return vehicleTypeSeq;
    }

    public void setVehicleTypeSeq(Integer vehicleTypeSeq) {
        this.vehicleTypeSeq = vehicleTypeSeq;
    }

    @Basic
    @Column(name = "container_type_seq")
    public Integer getContainerTypeSeq() {
        return containerTypeSeq;
    }

    public void setContainerTypeSeq(Integer containerTypeSeq) {
        this.containerTypeSeq = containerTypeSeq;
    }

    @Basic
    @Column(name = "pickup_location_seq", nullable = false)
    public Integer getPickupLocationSeq() {
        return pickupLocationSeq;
    }

    public void setPickupLocationSeq(Integer pickupLocationSeq) {
        this.pickupLocationSeq = pickupLocationSeq;
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
    @Column(name = "pickup_contact_person", length = 200)
    public String getPickupContactPerson() {
        return pickupContactPerson;
    }

    public void setPickupContactPerson(String pickupContactPerson) {
        this.pickupContactPerson = pickupContactPerson;
    }

    @Basic
    @Column(name = "pickup_contact_no", length = 50)
    public String getPickupContactNo() {
        return pickupContactNo;
    }

    public void setPickupContactNo(String pickupContactNo) {
        this.pickupContactNo = pickupContactNo;
    }

    @Basic
    @Column(name = "delivery_contact_person", length = 200)
    public String getDeliveryContactPerson() {
        return deliveryContactPerson;
    }

    public void setDeliveryContactPerson(String deliveryContactPerson) {
        this.deliveryContactPerson = deliveryContactPerson;
    }

    @Basic
    @Column(name = "delivery_contact_no", length = 50)
    public String getDeliveryContactNo() {
        return deliveryContactNo;
    }

    public void setDeliveryContactNo(String deliveryContactNo) {
        this.deliveryContactNo = deliveryContactNo;
    }

    @Basic
    @Column(name = "delivery_location_seq", nullable = false)
    public Integer getDeliveryLocationSeq() {
        return deliveryLocationSeq;
    }

    public void setDeliveryLocationSeq(Integer deliveryLocationSeq) {
        this.deliveryLocationSeq = deliveryLocationSeq;
    }

    @Basic
    @Column(name = "requested_delivery_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getRequestedDeliveryTime() {
        return requestedDeliveryTime;
    }

    public void setRequestedDeliveryTime(Date requestedDeliveryTime) {
        this.requestedDeliveryTime = requestedDeliveryTime;
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
    @Column(name = "pickup_location_address", nullable = false)
    public String getPickupLocationAddress() {
        return pickupLocationAddress;
    }

    public void setPickupLocationAddress(String pickupLocationAddress) {
        this.pickupLocationAddress = pickupLocationAddress;
    }

    @Basic
    @Column(name = "delivery_location_address", nullable = false)
    public String getDeliveryLocationAddress() {
        return deliveryLocationAddress;
    }

    public void setDeliveryLocationAddress(String deliveryLocationAddress) {
        this.deliveryLocationAddress = deliveryLocationAddress;
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
    @Column(name = "cash_or_credit", nullable = false)
    public Integer getCashOrCredit() {
        return cashOrCredit;
    }

    public void setCashOrCredit(Integer cashOrCredit) {
        this.cashOrCredit = cashOrCredit;
        if (cashOrCredit != null) {
            this.setCashOrCreditDescription(StakeholderCashType.findOne(cashOrCredit).getStakeholderCashType());
        }
    }

    @Basic
    @Column(name = "proposed_transport_charge")
    public Double getProposedTransportCharge() {
        return proposedTransportCharge;
    }

    public void setProposedTransportCharge(Double proposedTransportCharge) {
        this.proposedTransportCharge = proposedTransportCharge;
    }

    @Basic
    @Column(name = "estimated_km")
    public Double getEstimatedKm() {
        return estimatedKm;
    }

    public void setEstimatedKm(Double estimatedKm) {
        this.estimatedKm = estimatedKm;
    }

    @Basic
    @Column(name = "human_readable_eta")
    public String getHumanReadableEta() {
        return humanReadableEta;
    }

    public void setHumanReadableEta(String humanReadableEta) {
        this.humanReadableEta = humanReadableEta;
    }

    @Basic
    @Column(name = "eta_in_minutes")
    public Integer getEtaInMinutes() {
        return etaInMinutes;
    }

    public void setEtaInMinutes(Integer etaInMinutes) {
        this.etaInMinutes = etaInMinutes;
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
    @Column(name = "current_status", nullable = false)
    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Basic
    @Column(name = "actual_start_location_seq")
    public Integer getActualStartLocationSeq() {
        return actualStartLocationSeq;
    }

    public void setActualStartLocationSeq(Integer actualStartLocationSeq) {
        this.actualStartLocationSeq = actualStartLocationSeq;
    }

    @Basic
    @Column(name = "actual_end_location_seq")
    public Integer getActualEndLocationSeq() {
        return actualEndLocationSeq;
    }

    public void setActualEndLocationSeq(Integer actualEndLocationSeq) {
        this.actualEndLocationSeq = actualEndLocationSeq;
    }

    @OrderBy("requestedArrivalTime")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_booking_seq", insertable = false, updatable = false)
    @Where(clause = "status != 0 ")
    public List<TransportBookingViaLocation> getTransportBookingViaLocationList() {
        return transportBookingViaLocationList;
    }

    public void setTransportBookingViaLocationList(List<TransportBookingViaLocation> transportBookingViaLocationList) {
        this.transportBookingViaLocationList = transportBookingViaLocationList;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_seq", insertable = false, updatable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getCustomer() {
        return customer;
    }

    public void setCustomer(Stakeholder customer) {
        this.customer = customer;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipper_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getShipper() {
        return shipper;
    }

    public void setShipper(Stakeholder shipper) {
        this.shipper = shipper;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consignee_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getConsignee() {
        return consignee;
    }

    public void setConsignee(Stakeholder consignee) {
        this.consignee = consignee;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_customer_seq", referencedColumnName = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getInvoiceCustomer() {
        return invoiceCustomer;
    }

    public void setInvoiceCustomer(Stakeholder invoiceCustomer) {
        this.invoiceCustomer = invoiceCustomer;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seq", insertable = false, updatable = false)
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_type_seq", insertable = false, updatable = false)
    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uow_seq", referencedColumnName = "unit_seq", insertable = false, updatable = false)
    public Unit getUow() {
        return uow;
    }

    public void setUow(Unit uow) {
        this.uow = uow;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uov_seq", referencedColumnName = "unit_seq", insertable = false, updatable = false)
    public Unit getUov() {
        return uov;
    }

    public void setUov(Unit uov) {
        this.uov = uov;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_type_seq", insertable = false, updatable = false)
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_type_seq", insertable = false, updatable = false)
    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_location_seq", referencedColumnName = "final_destination_seq", insertable = false, updatable = false)
    public FinalDestination getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(FinalDestination pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_location_seq", referencedColumnName = "final_destination_seq", insertable = false, updatable = false)
    public FinalDestination getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(FinalDestination deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_status", insertable = false, updatable = false)
    public TransportBookingStatus getTransportBookingStatus() {
        return transportBookingStatus;
    }

    public void setTransportBookingStatus(TransportBookingStatus transportBookingStatus) {
        this.transportBookingStatus = transportBookingStatus;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_booking_seq", referencedColumnName = "transport_booking_seq", insertable = false, updatable = false)
    @Where(clause = "status != 0 ")
    public List<TransportBookingVehicle> getTransportBookingVehicleList() {
        return transportBookingVehicleList;
    }

    public void setTransportBookingVehicleList(List<TransportBookingVehicle> transportBookingVehicleList) {
        this.transportBookingVehicleList = transportBookingVehicleList;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_seq", referencedColumnName = "transport_booking_seq", insertable = false, updatable = false)
    @Where(clause = "status != 0 ")
    public List<LocalInvoice> getLocalInvoiceList() {
        return localInvoiceList;
    }

    public void setLocalInvoiceList(List<LocalInvoice> localInvoiceList) {
        this.localInvoiceList = localInvoiceList;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_seq", referencedColumnName = "transport_booking_seq", insertable = false, updatable = false)
    @Where(clause = "status != 0 ")
    public List<ExpenseVoucher> getExpenseVoucherList() {
        return expenseVoucherList;
    }

    public void setExpenseVoucherList(List<ExpenseVoucher> expenseVoucherList) {
        this.expenseVoucherList = expenseVoucherList;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_booking_seq", insertable = false, updatable = false)
    public TransportBookingFeedback getTransportBookingFeedback() {
        return transportBookingFeedback;
    }

    public void setTransportBookingFeedback(TransportBookingFeedback transportBookingFeedback) {
        this.transportBookingFeedback = transportBookingFeedback;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actual_start_location_seq", referencedColumnName = "final_destination_seq", insertable = false, updatable = false)
    public FinalDestination getActualStartLocation() {
        return actualStartLocation;
    }

    public void setActualStartLocation(FinalDestination actualStartLocation) {
        this.actualStartLocation = actualStartLocation;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actual_end_location_seq", referencedColumnName = "final_destination_seq", insertable = false, updatable = false)
    public FinalDestination getActualEndLocation() {
        return actualEndLocation;
    }

    public void setActualEndLocation(FinalDestination actualEndLocation) {
        this.actualEndLocation = actualEndLocation;
    }

    @Transient
    public String getPaymentModeDescription() {
        return paymentModeDescription;
    }

    public void setPaymentModeDescription(String paymentModeDescription) {
        this.paymentModeDescription = paymentModeDescription;
    }

    @Transient
    public String getCashOrCreditDescription() {
        return cashOrCreditDescription;
    }

    public void setCashOrCreditDescription(String cashOrCreditDescription) {
        this.cashOrCreditDescription = cashOrCreditDescription;
    }

    @Transient
    public String getInvoiceStatusDescription() {
        return invoiceStatusDescription;
    }

    public void setInvoiceStatusDescription(String invoiceStatusDescription) {
        this.invoiceStatusDescription = invoiceStatusDescription;

    }

    @Transient
    public String getBookingNo() {
        return this.getPrefix() + bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    @Transient
    public String getViaLocationString() {
        return viaLocationString;
    }

    public void setViaLocationString(String viaLocationString) {
        this.viaLocationString = viaLocationString;
    }

    @Transient
    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    @Transient
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Transient
    public TransportBookingVehicle getTransportBookingVehicleTemp() {
        return transportBookingVehicleTemp;
    }

    public void setTransportBookingVehicleTemp(TransportBookingVehicle transportBookingVehicleTemp) {
        this.transportBookingVehicleTemp = transportBookingVehicleTemp;
    }

    @Transient
    public TransportBookingFeedback getTransportBookingFeedbackTemp() {
        return transportBookingFeedbackTemp;
    }

    public void setTransportBookingFeedbackTemp(TransportBookingFeedback transportBookingFeedbackTemp) {
        this.transportBookingFeedbackTemp = transportBookingFeedbackTemp;
    }

    @Transient
    public List<TransportBookingViaLocation> getTransportBookingViaLocationListTemp() {
        return transportBookingViaLocationListTemp;
    }

    public void setTransportBookingViaLocationListTemp(List<TransportBookingViaLocation> transportBookingViaLocationListTemp) {
        this.transportBookingViaLocationListTemp = transportBookingViaLocationListTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportBooking that = (TransportBooking) o;
        return Objects.equals(transportBookingSeq, that.transportBookingSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(departmentSeq, that.departmentSeq) &&
                Objects.equals(moduleSeq, that.moduleSeq) &&
                Objects.equals(customerSeq, that.customerSeq) &&
                Objects.equals(jobSeq, that.jobSeq) &&
                Objects.equals(invoiceCustomerSeq, that.invoiceCustomerSeq) &&
                Objects.equals(shipperSeq, that.shipperSeq) &&
                Objects.equals(consigneeSeq, that.consigneeSeq) &&
                Objects.equals(customerReferenceNo, that.customerReferenceNo) &&
                Objects.equals(invoiceStatus, that.invoiceStatus) &&
                Objects.equals(pieces, that.pieces) &&
                Objects.equals(packageTypeSeq, that.packageTypeSeq) &&
                Objects.equals(cbm, that.cbm) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(uowSeq, that.uowSeq) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(uovSeq, that.uovSeq) &&
                Objects.equals(vehicleTypeSeq, that.vehicleTypeSeq) &&
                Objects.equals(containerTypeSeq, that.containerTypeSeq) &&
                Objects.equals(pickupLocationSeq, that.pickupLocationSeq) &&
                Objects.equals(requestedArrivalTime, that.requestedArrivalTime) &&
                Objects.equals(deliveryLocationSeq, that.deliveryLocationSeq) &&
                Objects.equals(requestedDeliveryTime, that.requestedDeliveryTime) &&
                Objects.equals(pickupContactPerson, that.pickupContactPerson) &&
                Objects.equals(pickupContactNo, that.pickupContactNo) &&
                Objects.equals(deliveryContactPerson, that.deliveryContactPerson) &&
                Objects.equals(deliveryContactNo, that.deliveryContactNo) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(pickupLocationAddress, that.pickupLocationAddress) &&
                Objects.equals(deliveryLocationAddress, that.deliveryLocationAddress) &&
                Objects.equals(paymentMode, that.paymentMode) &&
                Objects.equals(cashOrCredit, that.cashOrCredit) &&
                Objects.equals(proposedTransportCharge, that.proposedTransportCharge) &&
                Objects.equals(estimatedKm, that.estimatedKm) &&
                Objects.equals(currentStatus, that.currentStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportBookingSeq, companyProfileSeq, departmentSeq, moduleSeq, customerSeq, jobSeq, invoiceCustomerSeq, shipperSeq,
                consigneeSeq, customerReferenceNo, invoiceStatus, pieces, packageTypeSeq, cbm, weight, uowSeq, volume, uovSeq,
                vehicleTypeSeq, containerTypeSeq, pickupLocationSeq, requestedArrivalTime, deliveryLocationSeq, requestedDeliveryTime, pickupContactPerson, pickupContactNo,
                deliveryContactPerson, deliveryContactNo, remarks, pickupLocationAddress, deliveryLocationAddress, paymentMode, cashOrCredit, proposedTransportCharge, estimatedKm, currentStatus);
    }
}
