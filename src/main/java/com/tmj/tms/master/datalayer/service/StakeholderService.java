package com.tmj.tms.master.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StakeholderService extends JpaEntityGraphRepository<Stakeholder, Integer>, JpaEntityGraphQueryDslPredicateExecutor<Stakeholder> {
    List<Stakeholder> findByStatus(Integer status);

    @EntityGraph(value = "Stakeholder.filtering", type = EntityGraph.EntityGraphType.FETCH)
    List<Stakeholder> findByStakeholderNameStartsWithIgnoreCaseAndStatusAndCompanyProfileSeq(String searchParam, Integer status, Integer companyProfileSeq);

    List<Stakeholder> findByStakeholderNameStartsWithIgnoreCaseAndCompanyProfileSeqAndStatus(String searchParam, Integer companyProfileSeq, Integer status);

    Stakeholder findByStakeholderSeq(Integer stakeholderSeq);

    @Query("select\n" +
            "     sh \n" +
            " from \n" +
            "    Stakeholder  sh,\n" +
            "    StakeholderTypeMapping shtm\n" +
            " where\n" +
            "    shtm.stakeholderTypeSeq =:STAKEHOLDER_TYPE_SEQ\n" +
            "    and sh.status =:STATUS\n" +
            "    and sh.companyProfileSeq =:COMPANY_PROFILE_SEQ\n" +
            "    and sh.stakeholderSeq = shtm.stakeholderSeq" +
            "    and sh.financeIntegration = 0 ")
    List<Stakeholder> getStakeholdersForFinanceIntegration(@Param("STAKEHOLDER_TYPE_SEQ") Integer stakeholderTypeSeq,
                                                        @Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                                        @Param("STATUS") Integer status);

    @EntityGraph(value = "Stakeholder.filtering", type = EntityGraph.EntityGraphType.FETCH)
    @Query("select\n" +
            "     sh \n" +
            " from \n" +
            "    Stakeholder  sh,\n" +
            "    StakeholderTypeMapping shtm\n" +
            " where\n" +
            "    shtm.stakeholderTypeSeq =:STAKEHOLDER_TYPE_SEQ\n" +
            "    and sh.status =:STATUS\n" +
            "    and sh.companyProfileSeq =:COMPANY_PROFILE_SEQ\n" +
            "    and sh.stakeholderSeq = shtm.stakeholderSeq\n" +
            "    and upper(sh.stakeholderName) LIKE  upper(:STAKEHOLDER_NAME || '%')")
    List<Stakeholder> getStakeholderDetails(@Param("STAKEHOLDER_NAME") String stakeholderName,
                                            @Param("STAKEHOLDER_TYPE_SEQ") Integer stakeholderTypeSeq,
                                            @Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                            @Param("STATUS") Integer status);

    @EntityGraph(value = "Stakeholder.filtering", type = EntityGraph.EntityGraphType.FETCH)
    @Query("select\n" +
            "     sh \n" +
            " from \n" +
            "    Stakeholder  sh,\n" +
            "    StakeholderTypeMapping shtm\n" +
            " where\n" +
            "    shtm.stakeholderTypeSeq IN :STAKEHOLDER_TYPE_SEQ \n" +
            "    and sh.status =:STATUS\n" +
            "    and sh.companyProfileSeq =:COMPANY_PROFILE_SEQ\n" +
            "    and sh.stakeholderSeq = shtm.stakeholderSeq\n" +
            "    and upper(sh.stakeholderName) LIKE  upper(:STAKEHOLDER_NAME || '%')")
    List<Stakeholder> getStakeholderDetails(@Param("STAKEHOLDER_NAME") String stakeholderName,
                                            @Param("STAKEHOLDER_TYPE_SEQ") List<Integer> stakeholderTypeSeq,
                                            @Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                            @Param("STATUS") Integer status);

    List<Stakeholder> findByStakeholderSeqIn(Collection<Integer> stakeholderSeqList);

    @Query("SELECT\n" +
            "  SH \n" +
            "FROM\n" +
            "  Stakeholder SH,\n" +
            "  StakeholderTypeMapping SHTP\n" +
            "WHERE\n" +
            "  SHTP.stakeholderTypeSeq = :STAKEHOLDER_TYPE_SEQ\n" +
            "  AND SHTP.stakeholderSeq = SH.stakeholderSeq\n" +
            "  AND SHTP.status = 1 ")
    List<Stakeholder> getStakeholderDetails(@Param("STAKEHOLDER_TYPE_SEQ") Integer stakeholderTypeSeq, Pageable limit);

    @Query("SELECT\n" +
            "  SH \n" +
            "FROM\n" +
            "  Stakeholder SH,\n" +
            "  StakeholderTypeMapping SHTP\n" +
            "WHERE\n" +
            "  SHTP.stakeholderTypeSeq = :STAKEHOLDER_TYPE_SEQ\n" +
            "  AND SHTP.stakeholderSeq = SH.stakeholderSeq\n" +
            "  AND SHTP.status = 1 ")
    List<Stakeholder> getStakeholderDetails(@Param("STAKEHOLDER_TYPE_SEQ") Integer stakeholderTypeSeq);

    @Query("SELECT\n" +
            "  SH \n" +
            "FROM\n" +
            "  Stakeholder SH\n" +
            "WHERE\n" +
            "  SH.status = :STATUS\n" +
            "  AND SH.companyProfileSeq =:COMPANY_PROFILE_SEQ" +
            "  AND upper(SH.stakeholderName) LIKE  upper(:STAKEHOLDER_NAME || '%')")
    List<Stakeholder> getStakeholderDetailsByStatus(@Param("STAKEHOLDER_NAME") String stakeholderName,
                                                    @Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                                    @Param("STATUS") Integer status);

    List<Stakeholder> findByStakeholderCode(String stakeholderCode);

    List<Stakeholder> findByStakeholderCodeAndStakeholderSeqNot(String stakeholderCode, Integer stakeholderSeq);
}