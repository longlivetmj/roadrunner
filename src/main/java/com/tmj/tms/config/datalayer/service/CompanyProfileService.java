package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyProfileService extends JpaRepository<CompanyProfile, Integer> {

    List<CompanyProfile> findByCompanyName(String companyName);

    @Query("select " +
            "   distinct c " +
            "from " +
            "   CompanyProfile c, " +
            "   UserModule um, " +
            "   CompanyModule cm," +
            "   User u " +
            "where " +
            "   c.companyProfileSeq = cm.companyProfileSeq " +
            "   and cm.companyModuleSeq = um.companyModuleSeq " +
            "   and u.userSeq = um.userSeq " +
            "   and u.username = :username " +
            "   and um.status = 1 " +
            "   and c.status = 1 " +
            "   and cm.status = 1")
    List<CompanyProfile> getActiveCompanyListByUsername(@Param("username") String username);

    @Query("select " +
            "   distinct c " +
            "from " +
            "   CompanyProfile c, " +
            "   UserModule um, " +
            "   CompanyModule cm " +
            "where " +
            "   c.companyProfileSeq = cm.companyProfileSeq " +
            "   and cm.companyModuleSeq = um.companyModuleSeq " +
            "   and um.userSeq = :USER_SEQ " +
            "   and um.status = 1 " +
            "   and c.status = 1 " +
            "   and cm.status = 1")
    List<CompanyProfile> getActiveCompanyListByUserSeq(@Param("USER_SEQ") Integer userSeq);


    List<CompanyProfile> findByCompanyNameContainingIgnoreCase(String companyName);

    CompanyProfile findByCompanyProfileSeq(Integer companyProfileSeq);

    List<CompanyProfile> findByStatusNot(Integer status);
}
