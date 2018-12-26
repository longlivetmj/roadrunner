package com.tmj.tms.finance.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
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
@Table(name = "rate_master_stakeholder")
public class RateMasterStakeholder {

    private Integer rateMasterStakeholderSeq;
    private Integer rateMasterSeq;
    private Integer stakeholderTypeSeq;
    private Integer stakeholderSeq;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private Stakeholder stakeholder;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "rate_master_stakeholder_seq", allocationSize = 1)
    @Column(name = "rate_master_stakeholder_seq", unique = true)
    public Integer getRateMasterStakeholderSeq() {
        return rateMasterStakeholderSeq;
    }

    public void setRateMasterStakeholderSeq(Integer rateMasterStakeholderSeq) {
        this.rateMasterStakeholderSeq = rateMasterStakeholderSeq;
    }

    @Basic
    @Column(name = "rate_master_seq", insertable = false, updatable = false)
    public Integer getRateMasterSeq() {
        return rateMasterSeq;
    }

    public void setRateMasterSeq(Integer rateMasterSeq) {
        this.rateMasterSeq = rateMasterSeq;
    }

    @Basic
    @Column(name = "stakeholder_type_seq", nullable = false)
    public Integer getStakeholderTypeSeq() {
        return stakeholderTypeSeq;
    }

    public void setStakeholderTypeSeq(Integer stakeholderTypeSeq) {
        this.stakeholderTypeSeq = stakeholderTypeSeq;
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

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeholder_seq", insertable = false, updatable = false)
    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(Stakeholder stakeholder) {
        this.stakeholder = stakeholder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateMasterStakeholder that = (RateMasterStakeholder) o;
        return Objects.equals(rateMasterStakeholderSeq, that.rateMasterStakeholderSeq) &&
                Objects.equals(rateMasterSeq, that.rateMasterSeq) &&
                Objects.equals(stakeholderTypeSeq, that.stakeholderTypeSeq) &&
                Objects.equals(stakeholderSeq, that.stakeholderSeq) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rateMasterStakeholderSeq, rateMasterSeq, stakeholderTypeSeq, stakeholderSeq, status);
    }
}
