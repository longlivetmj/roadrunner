package com.tmj.tms.master.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "StakeholderTypeMapping.default", attributeNodes = {
                @NamedAttributeNode("stakeholder"),
                @NamedAttributeNode("stakeholderType")
        })
})
@Entity
@Table(name = "stakeholder_type_mapping")
public class StakeholderTypeMapping {
    private Integer stakeholderTypeMappingSeq;
    private Integer status;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer stakeholderTypeSeq;
    private Integer stakeholderSeq;

    private Stakeholder stakeholder;
    private StakeholderType stakeholderType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "stakeholder_type_mapping_seq", allocationSize = 1)
    @Column(name = "stakeholder_type_mapping_seq", unique = true)
    public Integer getStakeholderTypeMappingSeq() {
        return stakeholderTypeMappingSeq;
    }

    public void setStakeholderTypeMappingSeq(Integer stakeholderTypeMappingSeq) {
        this.stakeholderTypeMappingSeq = stakeholderTypeMappingSeq;
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
    @Column(name = "created_by")
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
    @Column(name = "last_modified_by")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "stakeholder_seq", insertable = false, updatable = false, nullable = false)
    public Integer getStakeholderSeq() {
        return stakeholderSeq;
    }

    public void setStakeholderSeq(Integer stakeholderSeq) {
        this.stakeholderSeq = stakeholderSeq;
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
    @Column(name = "stakeholder_type_seq")
    public Integer getStakeholderTypeSeq() {
        return stakeholderTypeSeq;
    }

    public void setStakeholderTypeSeq(Integer stakeholderTypeSeq) {
        this.stakeholderTypeSeq = stakeholderTypeSeq;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeholder_seq", insertable = false, updatable = false)
    @JsonIgnore
    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(Stakeholder stakeholder) {
        this.stakeholder = stakeholder;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stakeholder_type_seq", insertable = false, updatable = false)
    public StakeholderType getStakeholderType() {
        return stakeholderType;
    }

    public void setStakeholderType(StakeholderType stakeholderType) {
        this.stakeholderType = stakeholderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StakeholderTypeMapping that = (StakeholderTypeMapping) o;
        return Objects.equals(stakeholderTypeMappingSeq, that.stakeholderTypeMappingSeq) &&
                Objects.equals(status, that.status) &&
                Objects.equals(stakeholderTypeSeq, that.stakeholderTypeSeq) &&
                Objects.equals(stakeholderSeq, that.stakeholderSeq);
    }
}
