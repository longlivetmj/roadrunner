package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.UserLogInAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLogInAuditService extends JpaRepository<UserLogInAudit, Integer> {

    @Query("select u from UserLogInAudit u where u.userLogInAuditSeq=(select max(userLogInAuditSeq) from UserLogInAudit where username=:USERNAME)")
    UserLogInAudit lastLogInAudit(@Param("USERNAME") String username);
}
