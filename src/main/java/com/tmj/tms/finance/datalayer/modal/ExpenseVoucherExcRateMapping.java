package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "expense_voucher_exc_rate_mapping")
public class ExpenseVoucherExcRateMapping {
    private Integer expenseVoucherExcRateMapSeq;
    private Integer expenseVoucherSeq;
    private Integer exchangeRateDetailSeq;
    private Double rate;
    private Integer status;
    private Integer currencySeq;

    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private ExchangeRateDetail exchangeRateDetail;
    private Currency currency;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "expense_voucher_exc_rate_map_seq", allocationSize = 1)
    @Column(name = "expense_voucher_exc_rate_map_seq", unique = true)
    public Integer getExpenseVoucherExcRateMapSeq() {
        return expenseVoucherExcRateMapSeq;
    }

    public void setExpenseVoucherExcRateMapSeq(Integer expenseVoucherExcRateMapSeq) {
        this.expenseVoucherExcRateMapSeq = expenseVoucherExcRateMapSeq;
    }

    @Basic
    @Column(name = "expense_voucher_seq", nullable = false, insertable = false, updatable = false)
    public Integer getExpenseVoucherSeq() {
        return expenseVoucherSeq;
    }

    public void setExpenseVoucherSeq(Integer expenseVoucherSeq) {
        this.expenseVoucherSeq = expenseVoucherSeq;
    }

    @Basic
    @Column(name = "exchange_rate_detail_seq")
    public Integer getExchangeRateDetailSeq() {
        return exchangeRateDetailSeq;
    }

    public void setExchangeRateDetailSeq(Integer exchangeRateDetailSeq) {
        this.exchangeRateDetailSeq = exchangeRateDetailSeq;
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
    @Column(name = "currency_seq")
    public Integer getCurrencySeq() {
        return currencySeq;
    }

    public void setCurrencySeq(Integer currencySeq) {
        this.currencySeq = currencySeq;
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

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_rate_detail_seq", insertable = false, updatable = false)
    public ExchangeRateDetail getExchangeRateDetail() {
        return exchangeRateDetail;
    }

    public void setExchangeRateDetail(ExchangeRateDetail exchangeRateDetail) {
        this.exchangeRateDetail = exchangeRateDetail;
    }

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_seq", insertable = false, updatable = false)
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseVoucherExcRateMapping that = (ExpenseVoucherExcRateMapping) o;
        return Objects.equals(expenseVoucherExcRateMapSeq, that.expenseVoucherExcRateMapSeq) &&
                Objects.equals(expenseVoucherSeq, that.expenseVoucherSeq) &&
                Objects.equals(exchangeRateDetailSeq, that.exchangeRateDetailSeq) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(currencySeq, that.currencySeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseVoucherExcRateMapSeq, expenseVoucherSeq, exchangeRateDetailSeq, rate, status, currencySeq);
    }
}
