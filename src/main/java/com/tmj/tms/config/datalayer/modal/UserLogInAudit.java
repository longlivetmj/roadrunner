package com.tmj.tms.config.datalayer.modal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_login_audit")
public class UserLogInAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "user_login_audit_seq", allocationSize = 1)
    @Column(name = "user_login_audit_seq", unique = true)
    private Integer userLogInAuditSeq;

    @Column(name = "username")
    private String username;

    @Column(name = "login_date")
    private Date loginDate;

    @Column(name = "logout_date")
    private Date logoutDate;

    @Column(name = "remote_address")
    private String remoteAddress;

    public Integer getUserLogInAuditSeq() {
        return userLogInAuditSeq;
    }

    public void setUserLogInAuditSeq(Integer userLogInAuditSeq) {
        this.userLogInAuditSeq = userLogInAuditSeq;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
