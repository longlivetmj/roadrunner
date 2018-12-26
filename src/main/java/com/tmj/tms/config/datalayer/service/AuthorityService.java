package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorityService extends JpaRepository<Authority, Integer> {

    @Query("select " +
            "  distinct au " +
            "from " +
            "   Authority au," +
            "   DocumentLink dl," +
            "   CompanyModule cm, " +
            "   UserModule um," +
            "   UserGroup ug," +
            "   GroupAuthority ga," +
            "   User u " +
            "where " +
            "   u.username = :USERNAME " +
            "   and u.userSeq = um.userSeq " +
            "   and u.userSeq = ug.userSeq " +
            "   and cm.companyModuleSeq = um.companyModuleSeq " +
            "   and cm.companyProfileSeq = :COMPANY " +
            "   and au.documentLinkSeq = dl.documentLinkSeq " +
            "   and au.authoritySeq = ga.authoritySeq " +
            "   and dl.moduleSeq = cm.moduleSeq " +
            "   and um.status = 1 " +
            "   and ug.status = 1 " +
            "   and cm.status = 1 ")
    List<Authority> getAuthorityListByUsernameAndCompanyProfileSeq(@Param("USERNAME") String username,
                                                                   @Param("COMPANY") Integer companyProfileSeq);

    @Query("select " +
            "   distinct au " +
            "from " +
            "   Authority au," +
            "   DocumentLink dl, " +
            "   GroupAuthority ga " +
            "where " +
            "   au.documentLinkSeq = dl.documentLinkSeq " +
            "   and au.authoritySeq = ga.authoritySeq " +
            "   and dl.moduleSeq = :MODULE_SEQ " +
            "   and ga.groupSeq = :GROUP_SEQ ")
    List<Authority> findByGroupSeqAndModuleSeq(@Param("GROUP_SEQ") Integer groupSeq,
                                               @Param("MODULE_SEQ") Integer moduleSeq);
}
