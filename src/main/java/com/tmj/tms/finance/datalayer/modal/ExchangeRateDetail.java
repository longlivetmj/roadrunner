package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmj.tms.master.datalayer.modal.Currency;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "exchange_rate_detail")
public class ExchangeRateDetail {
    private Integer exchangeRateDetailSeq;
    private Integer exchangeRateSeq;
    private Integer currencySeq;
    private Double rate;
    private Integer status;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private Currency currency;
    private ExchangeRate exchangeRate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "exchange_rate_detail_seq", allocationSize = 1)
    @Column(name = "exchange_rate_detail_seq", unique = true)
    public Integer getExchangeRateDetailSeq() {
        return exchangeRateDetailSeq;
    }

    public void setExchangeRateDetailSeq(Integer exchangeRateDetailSeq) {
        this.exchangeRateDetailSeq = exchangeRateDetailSeq;
    }

    @Basic
    @Column(name = "exchange_rate_seq", nullable = false, insertable = false, updatable = false)
    public Integer getExchangeRateSeq() {
        return exchangeRateSeq;
    }

    public void setExchangeRateSeq(Integer exchangeRateSeq) {
        this.exchangeRateSeq = exchangeRateSeq;
    }

    @Basic
    @Column(name = "currency_seq")
    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
    }

    @Basic
    @Column(name = "rate")
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_seq", insertable = false, updatable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_rate_seq", insertable = false, updatable = false)
    @JsonIgnore
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExchangeRateDetail)) return false;
        ExchangeRateDetail that = (ExchangeRateDetail) o;
        return Objects.equals(getExchangeRateDetailSeq(), that.getExchangeRateDetailSeq()) &&
                Objects.equals(getExchangeRateSeq(), that.getExchangeRateSeq()) &&
                Objects.equals(getCurrencySeq(), that.getCurrencySeq()) &&
                Objects.equals(getRate(), that.getRate()) &&
                Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExchangeRateDetailSeq(), getExchangeRateSeq(), getCurrencySeq(), getRate(), getStatus());
    }
}
