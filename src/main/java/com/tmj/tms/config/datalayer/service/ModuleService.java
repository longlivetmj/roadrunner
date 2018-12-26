package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ModuleService extends JpaRepository<Module, Integer> {

    @Query("select m from Module m, CompanyModule cm where cm.companyProfileSeq = :companySeq and cm.moduleSeq = m.moduleSeq")
    List<Module> getModuleListByCompanySeq(@Param("companySeq") Integer companySeq);

    @Query("select " +
            "  distinct m " +
            "from " +
            "   Module m, " +
            "   CompanyModule cm," +
            "   User u, " +
            "   UserModule um " +
            "where " +
            "   u.username = :USERNAME " +
            "   and u.userSeq = um.userSeq " +
            "   and um.companyModuleSeq = cm.companyModuleSeq " +
            "   and cm.companyProfileSeq = :COMPANY " +
            "   and cm.moduleSeq = m.moduleSeq " +
            "order by " +
            "   m.moduleSeq ")
    List<Module> getModuleListByCompanySeqAndUsername(@Param("COMPANY") Integer companyProfileSeq,
                                                      @Param("USERNAME") String username);

    @Query("select " +
            "  distinct m " +
            "from " +
            "   Module m, " +
            "   CompanyModule cm," +
            "   UserModule um " +
            "where " +
            "   um.userSeq = :USER " +
            "   and um.companyModuleSeq = cm.companyModuleSeq " +
            "   and cm.companyProfileSeq = :COMPANY " +
            "   and cm.moduleSeq = m.moduleSeq " +
            "order by " +
            "   m.moduleSeq ")
    List<Module> getModuleListByCompanySeqAndUserSeq(@Param("COMPANY") Integer companySeq,
                                                     @Param("USER") Integer userSeq);

    List<Module> findByModuleNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer status);

    @Query("select " +
            "   distinct m " +
            "from " +
            "   Module m, " +
            "   UserModule um," +
            "   CompanyModule cm " +
            "where " +
            "   m.moduleSeq = cm.moduleSeq " +
            "   and um.companyModuleSeq = cm.companyModuleSeq " +
            "   and cm.companyProfileSeq = :COMPANY " +
            "   and um.userSeq = :USER_SEQ " +
            "   and um.status = 1 " +
            "   and m.status = 1 " +
            "   and cm.status = 1")
    List<Module> getActiveModuleListByUserSeqAndCompanyProfileSeq(@Param("USER_SEQ") Integer userSeq,
                                                                  @Param("COMPANY") Integer companyProfileSeq);

    @Query("select " +
            "  distinct m " +
            "from " +
            "   Module m, " +
            "   CompanyModule cm," +
            "   User u, " +
            "   UserModule um " +
            "where " +
            "   u.username = :USERNAME " +
            "   and u.userSeq = um.userSeq " +
            "   and um.companyModuleSeq = cm.companyModuleSeq " +
            "   and cm.companyProfileSeq = :COMPANY " +
            "   and m.moduleSeq IN (:moduleSeqList) " +
            "order by " +
            "   m.moduleSeq ")
    List<Module> getModuleListByCompanySeqAndUsernameAndModuleSeqIn(@Param("COMPANY") Integer companyProfileSeq,
                                                                    @Param("USERNAME") String username,
                                                                    @Param("moduleSeqList") Collection<Integer> moduleSeqList);

    Module findByModuleCode(String moduleCode);

    @Query("select " +
            "  distinct m " +
            "from " +
            "   Module m, " +
            "   CompanyModule cm," +
            "   User u, " +
            "   UserModule um " +
            "where " +
            "   u.username = :USERNAME " +
            "   and u.userSeq = um.userSeq " +
            "   and um.companyModuleSeq = cm.companyModuleSeq " +
            "   and cm.companyProfileSeq = :COMPANY " +
            "   and cm.moduleSeq = m.moduleSeq " +
            "   and m.financeEnabled = :FINANCE_FLAG " +
            "order by " +
            "   m.moduleSeq ")
    List<Module> getModuleListByCompanySeqAndUsernameAndFinanceEnabled(@Param("COMPANY") Integer companyProfileSeq,
                                                                       @Param("USERNAME") String name,
                                                                       @Param("FINANCE_FLAG") Integer financeEnabled);

    @Query("select " +
            "    distinct m " +
            "from " +
            "    Module m " +
            "where" +
            "    m.financeEnabled=1 ")
    List<Module> getFinanceEnabledModuleList();


    @Query("select " +
            "  distinct m " +
            "from " +
            "   Module m, " +
            "   CompanyModule cm," +
            "   User u, " +
            "   UserModule um " +
            "where " +
            "   u.username = :USERNAME " +
            "   and u.userSeq = um.userSeq " +
            "   and um.companyModuleSeq = cm.companyModuleSeq " +
            "   and cm.companyProfileSeq = :COMPANY " +
            "   and cm.moduleSeq = m.moduleSeq " +
            "   and m.financeEnabled = :FINANCE_FLAG " +
            "and m.moduleSeq IN (:moduleSeqList)" +
            "order by " +
            "   m.moduleSeq ")
    List<Module> getModuleListByCompanySeqAndUsernameAndFinanceEnabledAndModuleSeqIn(@Param("COMPANY") Integer companyProfileSeq,
                                                                                     @Param("USERNAME") String name,
                                                                                     @Param("FINANCE_FLAG") Integer financeEnabled,
                                                                                     @Param("moduleSeqList") Collection<Integer> moduleSeqList);
}
