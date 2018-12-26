package com.tmj.tms.config.datalayer.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "user_department")
public class UserDepartment {
    private Integer userDepartmentSeq;
    private Integer userSeq;
    private Integer departmentSeq;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer status;

    private Department department;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "user_department_seq", allocationSize = 1)
    @Column(name = "user_department_seq", nullable = false)
    public Integer getUserDepartmentSeq() {
        return userDepartmentSeq;
    }

    public void setUserDepartmentSeq(Integer userDepartmentSeq) {
        this.userDepartmentSeq = userDepartmentSeq;
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
    @Column(name = "user_seq", nullable = false)
    public Integer getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Integer userSeq) {
        this.userSeq = userSeq;
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_seq",insertable = false,updatable = false,nullable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department= department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDepartment)) return false;
        UserDepartment that = (UserDepartment) o;
        return Objects.equals(getUserSeq(), that.getUserSeq()) &&
                Objects.equals(getDepartmentSeq(), that.getDepartmentSeq()) &&
                Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserSeq(), getDepartmentSeq(), getStatus());
    }
}
