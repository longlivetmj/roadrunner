package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.UserModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserModuleService extends JpaRepository<UserModule, Integer> {

    UserModule getUserModuleByUserModuleSeq(Integer userModuleSeq);

    UserModule findByUserSeqAndCompanyModuleSeq(Integer userSeq, Integer companyModuleSeq);

    @Query("select " +
            "   distinct um " +
            "from " +
            "   UserModule um, " +
            "   CompanyModule cm " +
            "where " +
            "   cm.moduleSeq=:MODULE_SEQ " +
            "   and cm.companyProfileSeq = :COMPANY_SEQ " +
            "   and um.userSeq = :USER_SEQ " +
            "   and um.companyModuleSeq = cm.companyModuleSeq " +
            "   and um.status = 1 " +
            "   and cm.status = 1 ")
    UserModule findByUserSeqCompanyProfileSeqAndModuleSeq(@Param("USER_SEQ") Integer userSeq,
                                                          @Param("COMPANY_SEQ") Integer companyProfileSeq,
                                                          @Param("MODULE_SEQ") Integer moduleSeq);
}
