package com.tmj.tms.finance.datalayer.modal;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "chart_of_account")
public class ChartOfAccount {

    private Integer chartOfAccountSeq;
    private Integer companyProfileSeq;
    private String accountName;
    private String accountType;
    private Long accountId;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "chart_of_account_seq", allocationSize = 1)
    @Column(name = "chart_of_account_seq", unique = true)
    public Integer getChartOfAccountSeq() {
        return chartOfAccountSeq;
    }

    public void setChartOfAccountSeq(Integer chartOfAccountSeq) {
        this.chartOfAccountSeq = chartOfAccountSeq;
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
    @Column(name = "account_name", nullable = false)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Basic
    @Column(name = "account_type", nullable = false)
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Basic
    @Column(name = "account_id", nullable = false)
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChartOfAccount that = (ChartOfAccount) o;
        return Objects.equals(chartOfAccountSeq, that.chartOfAccountSeq) &&
                Objects.equals(companyProfileSeq, that.companyProfileSeq) &&
                Objects.equals(accountName, that.accountName) &&
                Objects.equals(accountType, that.accountType) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chartOfAccountSeq, companyProfileSeq, accountName, accountType, accountId, status);
    }
}
