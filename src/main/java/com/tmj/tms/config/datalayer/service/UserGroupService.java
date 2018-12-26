package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserGroupService extends JpaRepository<UserGroup, Integer> {

    List<UserGroup> findByUserSeq(Integer userSeq);

    UserGroup findByUserSeqAndGroupSeq(Integer userSeq, Integer groupSeq);

    List<UserGroup> findByCreatedDateBetween(Date startDate, Date endDate);

    @Query("select " +
            "   ug " +
            "from " +
            "   UserGroup ug," +
            "   UserModule um, " +
            "   CompanyModule cm " +
            "where " +
            "   ug.userModuleSeq = um.userModuleSeq " +
            "   and um.companyModuleSeq = cm.companyModuleSeq " +
            "   and cm.companyProfileSeq = :COMPANY_SEQ " +
            "   and cm.moduleSeq = :MODULE_SEQ " +
            "   and ug.userSeq = :USER_SEQ " +
            "   and ug.status = 1 " +
            "   and um.status = 1 " +
            "   and cm.status = 1 ")
    List<UserGroup> findAllByUserSeqCompanyProfileSeqAndModuleSeq(@Param("USER_SEQ") Integer userSeq,
                                                                  @Param("COMPANY_SEQ") Integer companyProfileSeq,
                                                                  @Param("MODULE_SEQ") Integer moduleSeq);
}
