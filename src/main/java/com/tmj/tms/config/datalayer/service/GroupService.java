package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupService extends JpaRepository<Group, Integer> {

    List<Group> findByModuleSeq(Integer moduleSeq);

    @Query("select " +
            "   g " +
            "from " +
            "   Group g, " +
            "   UserGroup ug," +
            "   UserModule um, " +
            "   CompanyModule cm " +
            "where " +
            "   ug.userSeq = :USER_SEQ " +
            "   and um.userSeq = ug.userSeq " +
            "   and um.companyModuleSeq = cm.companyModuleSeq " +
            "   and cm.moduleSeq = :MODULE_SEQ " +
            "   and cm.companyProfileSeq = :COMPANY_SEQ " +
            "   and ug.userModuleSeq = um.userModuleSeq " +
            "   and ug.groupSeq = g.groupSeq " +
            "   and ug.status = 1 " +
            "   and um.status = 1 " +
            "   and cm.status = 1 ")
    List<Group> getGroupListByUserSeqModuleSeqAndCompanyProfileSeq(@Param("USER_SEQ") Integer userSeq,
                                                                   @Param("MODULE_SEQ") Integer moduleSeq,
                                                                   @Param("COMPANY_SEQ") Integer companyProfileSeq);
}
