package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.utility.ExchangeRateSourceType;
import com.tmj.tms.master.datalayer.modal.Bank;
import com.tmj.tms.master.datalayer.modal.Currency;
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
        @NamedEntityGraph(name = "ExchangeRate.default", attributeNodes = {
                @NamedAttributeNode(value = "exchangeRateDetails", subgraph = "exchangeRateDetails")
        }, subgraphs = @NamedSubgraph(name = "exchangeRateDetails", attributeNodes = @NamedAttributeNode("currency"))),
        @NamedEntityGraph(name = "ExchangeRate.All", attributeNodes = {
                @NamedAttributeNode(value = "exchangeRateDetails", subgraph = "exchangeRateDetails")
        }, subgraphs = @NamedSubgraph(name = "exchangeRateDetails", attributeNodes = @NamedAttributeNode("currency"))),
        @NamedEntityGraph(name = "ExchangeRate.search", attributeNodes = {
                @NamedAttributeNode(value = "exchangeRateDetails", subgraph = "exchangeRateDetails")
        }, subgraphs = {@NamedSubgraph(name = "exchangeRateDetails", attributeNodes = @NamedAttributeNode("currency"))}),
})
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "exchange_rate")
public class ExchangeRate {
    private Integer exchangeRateSeq;
    private Integer baseCurrencySeq;
    private Integer exchangeRateSourceType;
    private Integer bankSeq;
    private Date effectiveFrom;
    private Date effectiveTo;
    private Integer companyProfileSeq;
    private Integer status;


    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private Integer moduleSeq;

    private List<ExchangeRateDetail> exchangeRateDetails;

    private Currency currency;
    private Bank sourceBank;
    private Module module;

    private String statusDescription;
    private String sourceDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "exchange_rate_seq", allocationSize = 1)
    @Column(name = "exchange_rate_seq", unique = true)
    public Integer getExchangeRateSeq() {
        return exchangeRateSeq;
    }

    public void setExchangeRateSeq(Integer exchangeRateSeq) {
        this.exchangeRateSeq = exchangeRateSeq;
    }

    @Basic
    @Column(name = "base_currency_seq")
    public Integer getBaseCurrencySeq() {
        return baseCurrencySeq;
    }

    public void setBaseCurrencySeq(Integer baseCurrencySeq) {
        this.baseCurrencySeq = baseCurrencySeq;
    }

    @Basic
    @Column(name = "exchange_rate_source_type")
    public Integer getExchangeRateSourceType() {
        return exchangeRateSourceType;
    }

    public void setExchangeRateSourceType(Integer exchangeRateSourceType) {
        this.exchangeRateSourceType = exchangeRateSourceType;
        if (exchangeRateSourceType != null) {
            this.setSourceDescription(ExchangeRateSourceType.findOne(exchangeRateSourceType).getExchangeRateSourceTypeDescription());
        }
    }

    @Basic
    @Column(name = "effective_from", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }


    @Basic
    @Column(name = "effective_to", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }


    @Basic
    @Column(name = "company_profile_seq")
    public Integer getCompanyProfileSeq() {
        return companyProfileSeq;
    }

    public void setCompanyProfileSeq(Integer companyProfileSeq) {
        this.companyProfileSeq = companyProfileSeq;
    }

    @Basic
    @Column(name = "bank_seq")
    public Integer getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(Integer bankSeq) {
        this.bankSeq = bankSeq;
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
    @Column(name = "module_seq")
    public Integer getModuleSeq() {
        return moduleSeq;
    }

    public void setModuleSeq(Integer moduleSeq) {
        this.moduleSeq = moduleSeq;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_rate_seq", nullable = false)
    public List<ExchangeRateDetail> getExchangeRateDetails() {
        return exchangeRateDetails;
    }

    public void setExchangeRateDetails(List<ExchangeRateDetail> exchangeRateDetails) {
        this.exchangeRateDetails = exchangeRateDetails;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_currency_seq", insertable = false, updatable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_seq", insertable = false, updatable = false)
    public Bank getSourceBank() {
        return sourceBank;
    }

    public void setSourceBank(Bank sourceBank) {
        this.sourceBank = sourceBank;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_seq", insertable = false, updatable = false)
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Transient
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Transient
    public String getSourceDescription() {
        return sourceDescription;
    }

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRate)) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(getExchangeRateSeq(), that.getExchangeRateSeq()) &&
                Objects.equals(getBaseCurrencySeq(), that.getBaseCurrencySeq()) &&
                Objects.equals(getExchangeRateSourceType(), that.getExchangeRateSourceType()) &&
                Objects.equals(getBankSeq(), that.getBankSeq()) &&
                Objects.equals(getEffectiveFrom(), that.getEffectiveFrom()) &&
                Objects.equals(getEffectiveTo(), that.getEffectiveTo()) &&
                Objects.equals(getCompanyProfileSeq(), that.getCompanyProfileSeq()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getModuleSeq(), that.getModuleSeq()) &&
                Objects.equals(getExchangeRateDetails(), that.getExchangeRateDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExchangeRateSeq(), getBaseCurrencySeq(), getExchangeRateSourceType(), getBankSeq(), getEffectiveFrom(), getEffectiveTo(), getCompanyProfileSeq(), getStatus(), getModuleSeq(), getExchangeRateDetails());
    }
}
