package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.utility.StakeholderCashType;
import com.tmj.tms.master.utility.StakeholderCreditType;
import com.tmj.tms.master.utility.StakeholderSvatType;
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
        @NamedEntityGraph(name = "Stakeholder.default", attributeNodes = {
                @NamedAttributeNode("currency"),
                @NamedAttributeNode("salesPerson"),
                @NamedAttributeNode("customerGroup"),
                @NamedAttributeNode("taxType"),
                @NamedAttributeNode("bank"),
                @NamedAttributeNode(value = "addressBook", subgraph = "addressBook"),
        }, subgraphs = @NamedSubgraph(name = "addressBook", attributeNodes = @NamedAttributeNode("country"))),
        @NamedEntityGraph(name = "Stakeholder.createStakeholder", attributeNodes = {
                @NamedAttributeNode("addressBook"),
                @NamedAttributeNode("currency"),
                @NamedAttributeNode("salesPerson"),
                @NamedAttributeNode("customerGroup"),
                @NamedAttributeNode("taxType"),
                @NamedAttributeNode("bank")
        }),
        @NamedEntityGraph(name = "Stakeholder.sendEmailStakeholderDetail", attributeNodes = {
                @NamedAttributeNode("currency"),
                @NamedAttributeNode("salesPerson"),
                @NamedAttributeNode("customerGroup"),
                @NamedAttributeNode("taxType"),
                @NamedAttributeNode("bank"),
                @NamedAttributeNode("stakeholderTypeMappings"),
                @NamedAttributeNode(value = "addressBook", subgraph = "addressBook")
        }, subgraphs = @NamedSubgraph(name = "addressBook", attributeNodes = @NamedAttributeNode("country"))),
        @NamedEntityGraph(name = "Stakeholder.search", attributeNodes = {
                @NamedAttributeNode("addressBook"),
                @NamedAttributeNode("customerGroup"),
        }),
        @NamedEntityGraph(name = "Stakeholder.filtering"),
        @NamedEntityGraph(name = "Stakeholder.addressBook", attributeNodes = {
                @NamedAttributeNode(value = "addressBook", subgraph = "addressBook")
        }, subgraphs = @NamedSubgraph(name = "addressBook", attributeNodes = @NamedAttributeNode("country")))
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "stakeholder")
public class Stakeholder {
    private Integer stakeholderSeq;
    private Integer companyProfileSeq;
    private String stakeholderName;
    private String stakeholderCode;
    private Integer stakeholderGroupSeq;
    private String globalStakeholderCode;
    private Integer addressBookSeq;
    private Integer taxTypeSeq;
    private Integer currencySeq;
    private String taxRegNo;
    private String airEmail;
    private String euNumber;
    private String companyRegNo;
    private Integer stakeholderSvatTypeSeq;
    private String suspendedTaxRegNo;
    private String asycudaCode;
    private Date dateJoined;
    private Double creditLimit;
    private Double creditPeriod;
    private String exportLicenceNo;
    private String nominee;
    private String tqbCode;
    private String iataCode;
    private Integer bankSeq;
    private String bankAccountNo;
    private String beneficiaryName;
    private String beneficiaryBranchName;
    private Integer salesPersonSeq;
    private Integer stakeholderCashTypeSeq;
    private Double actualOutStandingAmount;
    private Double actualCreditAmount;
    private Integer creditTypeSeq;
    private Double creditAmount;
    private Integer financeIntegration;
    private Long financeIntegrationKey;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private List<StakeholderTypeMapping> stakeholderTypeMappings;
    private AddressBook addressBook;
    private Currency currency;
    private SalesPerson salesPerson;
    private CustomerGroup customerGroup;
    private Bank bank;
    private TaxType taxType;

    private String statusDescription;
    private String stakeholderCashTypeDescription;
    private String creditTypeDescription;
    private String stakeholderSvatDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "stakeholder_seq", allocationSize = 1)
    @Column(name = "stakeholder_seq")
    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
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
    @Column(name = "stakeholder_name", nullable = false, length = 100)
    public String getStakeholderName() {
        return stakeholderName;
    }

    public void setStakeholderName(String stakeholderName) {
        this.stakeholderName = stakeholderName;
    }

    @Basic
    @Column(name = "stakeholder_code", nullable = false, length = 25)
    public String getStakeholderCode() {
        return stakeholderCode;
    }

    public void setStakeholderCode(String stakeholderCode) {
        this.stakeholderCode = stakeholderCode;
    }

    @Basic
    @Column(name = "stakeholder_group_seq", nullable = true)
    public Integer getStakeholderGroupSeq() {
        return stakeholderGroupSeq;
    }

    public void setStakeholderGroupSeq(Integer stakeholderGroupSeq) {
        this.stakeholderGroupSeq = stakeholderGroupSeq;
    }

    @Basic
    @Column(name = "global_stakeholder_code", nullable = true, length = 25)
    public String getGlobalStakeholderCode() {
        return globalStakeholderCode;
    }

    public void setGlobalStakeholderCode(String globalStakeholderCode) {
        this.globalStakeholderCode = globalStakeholderCode;
    }

    @Basic
    @Column(name = "status")
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
    @Column(name = "address_book_seq", insertable = false, updatable = false, nullable = false)
    public Integer getAddressBookSeq() {
        return addressBookSeq;
    }

    public void setAddressBookSeq(Integer addressBookSeq) {
        this.addressBookSeq = addressBookSeq;
    }

    @Basic
    @Column(name = "tax_type_seq", nullable = true)
    public Integer getTaxTypeSeq() {
        return taxTypeSeq;
    }

    public void setTaxTypeSeq(Integer taxTypeSeq) {
        this.taxTypeSeq = taxTypeSeq;
    }

    @Basic
    @Column(name = "currency_seq", nullable = false)
    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
    }

    @Basic
    @Column(name = "tax_reg_no", nullable = true, length = 55)
    public String getTaxRegNo() {
        return taxRegNo;
    }

    public void setTaxRegNo(String taxRegNo) {
        this.taxRegNo = taxRegNo;
    }

    @Basic
    @Column(name = "air_email", nullable = true, length = 50)
    public String getAirEmail() {
        return airEmail;
    }

    public void setAirEmail(String airEmail) {
        this.airEmail = airEmail;
    }

    @Basic
    @Column(name = "eu_number", nullable = true, length = 55)
    public String getEuNumber() {
        return euNumber;
    }

    public void setEuNumber(String euNumber) {
        this.euNumber = euNumber;
    }

    @Basic
    @Column(name = "company_reg_no", nullable = true)
    public String getCompanyRegNo() {
        return companyRegNo;
    }

    public void setCompanyRegNo(String companyRegNo) {
        this.companyRegNo = companyRegNo;
    }

    @Basic
    @Column(name = "stakeholder_svat_type_seq", nullable = true)
    public Integer getStakeholderSvatTypeSeq() {
        return stakeholderSvatTypeSeq;
    }

    public void setStakeholderSvatTypeSeq(Integer stakeholderSvatTypeSeq) {
        this.stakeholderSvatTypeSeq = stakeholderSvatTypeSeq;
        if (stakeholderSvatTypeSeq != null) {
            this.setStakeholderSvatDescription(StakeholderSvatType.findOne(stakeholderSvatTypeSeq).getStakeholderSvatType());
        }
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

    @Basic
    @Column(name = "suspended_tax_reg_no", nullable = true)
    public String getSuspendedTaxRegNo() {
        return suspendedTaxRegNo;
    }

    public void setSuspendedTaxRegNo(String suspendedTaxRegNo) {
        this.suspendedTaxRegNo = suspendedTaxRegNo;
    }

    @Basic
    @Column(name = "asycuda_code", nullable = true)
    public String getAsycudaCode() {
        return asycudaCode;
    }

    public void setAsycudaCode(String asycudaCode) {
        this.asycudaCode = asycudaCode;
    }

    @Basic
    @Column(name = "date_joined", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Basic
    @Column(name = "credit_limit", nullable = true)
    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Basic
    @Column(name = "credit_period", nullable = true)
    public Double getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(Double creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    @Basic
    @Column(name = "export_licence_no", nullable = true)
    public String getExportLicenceNo() {
        return exportLicenceNo;
    }

    public void setExportLicenceNo(String exportLicenceNo) {
        this.exportLicenceNo = exportLicenceNo;
    }

    @Basic
    @Column(name = "nominee", nullable = true)
    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    @Basic
    @Column(name = "tqb_code", nullable = true)
    public String getTqbCode() {
        return tqbCode;
    }

    public void setTqbCode(String tqbCode) {
        this.tqbCode = tqbCode;
    }

    @Basic
    @Column(name = "iata_code", nullable = true)
    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    @Basic
    @Column(name = "bank_seq", nullable = true)
    public Integer getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(Integer bankSeq) {
        this.bankSeq = bankSeq;
    }

    @Basic
    @Column(name = "bank_account_no", nullable = true)
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    @Basic
    @Column(name = "beneficiary_name", nullable = true)
    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    @Basic
    @Column(name = "beneficiary_branch_name", nullable = true)
    public String getBeneficiaryBranchName() {
        return beneficiaryBranchName;
    }

    public void setBeneficiaryBranchName(String beneficiaryBranchName) {
        this.beneficiaryBranchName = beneficiaryBranchName;
    }

    @Basic
    @Column(name = "sales_person_seq", nullable = true)
    public Integer getSalesPersonSeq() {
        return salesPersonSeq;
    }

    public void setSalesPersonSeq(Integer salesPersonSeq) {
        this.salesPersonSeq = salesPersonSeq;
    }

    @Basic
    @Column(name = "stakeholder_cash_type_seq", nullable = true)
    public Integer getStakeholderCashTypeSeq() {
        return stakeholderCashTypeSeq;
    }

    public void setStakeholderCashTypeSeq(Integer stakeholderCashTypeSeq) {
        this.stakeholderCashTypeSeq = stakeholderCashTypeSeq;
        if (stakeholderCashTypeSeq != null) {
            this.setStakeholderCashTypeDescription(StakeholderCashType.findOne(stakeholderCashTypeSeq).getStakeholderCashType());
        }
    }

    @Basic
    @Column(name = "actual_outstanding_amount", nullable = true)
    public Double getActualOutStandingAmount() {
        return actualOutStandingAmount;
    }

    public void setActualOutStandingAmount(Double actualOutStandingAmount) {
        this.actualOutStandingAmount = actualOutStandingAmount;
    }

    @Basic
    @Column(name = "actual_credit_amount", nullable = true)
    public Double getActualCreditAmount() {
        return actualCreditAmount;
    }

    public void setActualCreditAmount(Double actualCreditAmount) {
        this.actualCreditAmount = actualCreditAmount;
    }

    @Basic
    @Column(name = "credit_amount", nullable = true)
    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    @Basic
    @Column(name = "credit_type_seq", nullable = true)
    public Integer getCreditTypeSeq() {
        return creditTypeSeq;
    }

    public void setCreditTypeSeq(Integer creditTypeSeq) {
        this.creditTypeSeq = creditTypeSeq;
        if (creditTypeSeq != null) {
            this.setCreditTypeDescription(StakeholderCreditType.findOne(creditTypeSeq).getCreditTypeDescription());
        }
    }

    @Basic
    @CreatedBy
    @Column(name = "created_by", nullable = true, updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @CreatedDate
    @Column(name = "created_date", nullable = true, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = true)
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_book_seq", nullable = false)
    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
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
    @JoinColumn(name = "sales_person_seq", insertable = false, updatable = false)
    public SalesPerson getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeholder_group_seq", insertable = false, updatable = false)
    public CustomerGroup getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(CustomerGroup customerGroup) {
        this.customerGroup = customerGroup;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_seq", insertable = false, updatable = false)
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Transient
    public String getStakeholderCashTypeDescription() {
        return stakeholderCashTypeDescription;
    }

    public void setStakeholderCashTypeDescription(String stakeholderCashTypeDescription) {
        this.stakeholderCashTypeDescription = stakeholderCashTypeDescription;

    }

    @Transient
    public String getCreditTypeDescription() {
        return creditTypeDescription;
    }

    public void setCreditTypeDescription(String creditTypeDescription) {
        this.creditTypeDescription = creditTypeDescription;
    }

    @Transient
    public String getStakeholderSvatDescription() {
        return stakeholderSvatDescription;
    }

    public void setStakeholderSvatDescription(String stakeholderSvatDescription) {
        this.stakeholderSvatDescription = stakeholderSvatDescription;
    }

    //    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "stakeholder_seq", nullable = false)
    public List<StakeholderTypeMapping> getStakeholderTypeMappings() {
        return stakeholderTypeMappings;
    }

    public void setStakeholderTypeMappings(List<StakeholderTypeMapping> stakeholderTypeMappings) {
        this.stakeholderTypeMappings = stakeholderTypeMappings;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_type_seq", insertable = false, updatable = false)
    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stakeholder that = (Stakeholder) o;
        return Objects.equals(stakeholderSeq, that.stakeholderSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(stakeholderName, that.stakeholderName) &&
                Objects.equals(stakeholderCode, that.stakeholderCode) &&
                Objects.equals(stakeholderGroupSeq, that.stakeholderGroupSeq) &&
                Objects.equals(globalStakeholderCode, that.globalStakeholderCode) &&
                Objects.equals(addressBookSeq, that.addressBookSeq) &&
                Objects.equals(taxTypeSeq, that.taxTypeSeq) &&
                Objects.equals(currencySeq, that.currencySeq) &&
                Objects.equals(taxRegNo, that.taxRegNo) &&
                Objects.equals(airEmail, that.airEmail) &&
                Objects.equals(euNumber, that.euNumber) &&
                Objects.equals(companyRegNo, that.companyRegNo) &&
                Objects.equals(stakeholderSvatTypeSeq, that.stakeholderSvatTypeSeq) &&
                Objects.equals(suspendedTaxRegNo, that.suspendedTaxRegNo) &&
                Objects.equals(asycudaCode, that.asycudaCode) &&
                Objects.equals(dateJoined, that.dateJoined) &&
                Objects.equals(creditLimit, that.creditLimit) &&
                Objects.equals(creditPeriod, that.creditPeriod) &&
                Objects.equals(exportLicenceNo, that.exportLicenceNo) &&
                Objects.equals(nominee, that.nominee) &&
                Objects.equals(tqbCode, that.tqbCode) &&
                Objects.equals(iataCode, that.iataCode) &&
                Objects.equals(bankSeq, that.bankSeq) &&
                Objects.equals(bankAccountNo, that.bankAccountNo) &&
                Objects.equals(beneficiaryName, that.beneficiaryName) &&
                Objects.equals(beneficiaryBranchName, that.beneficiaryBranchName) &&
                Objects.equals(salesPersonSeq, that.salesPersonSeq) &&
                Objects.equals(stakeholderCashTypeSeq, that.stakeholderCashTypeSeq) &&
                Objects.equals(actualOutStandingAmount, that.actualOutStandingAmount) &&
                Objects.equals(actualCreditAmount, that.actualCreditAmount) &&
                Objects.equals(creditTypeSeq, that.creditTypeSeq) &&
                Objects.equals(creditAmount, that.creditAmount) &&
                Objects.equals(status, that.status) &&
                Objects.equals(stakeholderTypeMappings, that.stakeholderTypeMappings) &&
                Objects.equals(addressBook, that.addressBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stakeholderSeq, companyProfileSeq, stakeholderName, stakeholderCode, stakeholderGroupSeq, globalStakeholderCode, addressBookSeq, taxTypeSeq, currencySeq, taxRegNo, airEmail, euNumber, companyRegNo, stakeholderSvatTypeSeq, suspendedTaxRegNo, asycudaCode, dateJoined, creditLimit, creditPeriod, exportLicenceNo, nominee, tqbCode, iataCode, bankSeq, bankAccountNo, beneficiaryName, beneficiaryBranchName, salesPersonSeq, stakeholderCashTypeSeq, actualOutStandingAmount, actualCreditAmount, creditTypeSeq, creditAmount, status, stakeholderTypeMappings, addressBook);
    }
}
