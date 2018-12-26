package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.utility.MasterDataStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "Branch.default", attributeNodes = {
                @NamedAttributeNode("bank"),
                @NamedAttributeNode(value = "addressBook", subgraph = "addressBook")
        }, subgraphs = @NamedSubgraph(name = "addressBook", attributeNodes = @NamedAttributeNode("country"))),

        @NamedEntityGraph(name = "Branch.AuxSearch", attributeNodes = {})
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "branch")
public class Branch {
    private Integer branchSeq;
    private String branchCode;
    private String branchName;
    private Integer status;
    private Integer bankSeq;
    private Integer addressBookSeq;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private AddressBook addressBook;
    private Bank bank;

    private String statusDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "branch_seq", allocationSize = 1)
    @Column(name = "branch_seq", unique = true)
    public Integer getBranchSeq() {
        return branchSeq;
    }

    public void setBranchSeq(Integer branchSeq) {
        this.branchSeq = branchSeq;
    }

    @Basic
    @Column(name = "branch_code", length = 5, nullable = false)
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    @Basic
    @Column(name = "branch_name", length = 30, nullable = false)
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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
    @Column(name = "created_by", length = 50, nullable = true, updatable = false)
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
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Basic
    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50, nullable = true)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_book_seq", nullable = false)
    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_seq", insertable = false, updatable = false)
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
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
    @Column(name = "bank_seq", nullable = false)
    public Integer getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(Integer bankSeq) {
        this.bankSeq = bankSeq;
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
        if (!(o instanceof Branch)) return false;
        Branch branch = (Branch) o;
        return Objects.equals(getBranchSeq(), branch.getBranchSeq()) &&
                Objects.equals(getBranchCode(), branch.getBranchCode()) &&
                Objects.equals(getBranchName(), branch.getBranchName()) &&
                Objects.equals(getStatus(), branch.getStatus()) &&
                Objects.equals(getBankSeq(), branch.getBankSeq()) &&
                Objects.equals(getAddressBookSeq(), branch.getAddressBookSeq()) &&
                Objects.equals(getAddressBook(), branch.getAddressBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBranchSeq(), getBranchCode(), getBranchName(), getStatus(), getBankSeq(), getAddressBookSeq(), getAddressBook());
    }
}
