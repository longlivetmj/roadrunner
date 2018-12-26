package com.tmj.tms.master.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.master.datalayer.modal.Charge;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ChargeService extends JpaEntityGraphRepository<Charge, Integer> {
    Charge findByChargeNameAndCompanyProfileSeqAndStatus(String chargeName, Integer companyProfileSeq, Integer status);

    List<Charge> findByChargeNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndStatusIn(String chargeName, String description, Collection<Integer> statusSeqList);

    List<Charge> findByStatusIn(Collection<Integer> statusSeqList);

    List<Charge> findByStatus(Integer status);

    List<Charge> findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer status);

    /*@Query(" SELECT " +
            "CHA " +
            "FROM " +
            "Charge CHA, " +
            "DepartmentCharge DC " +
            "WHERE " +
            "CHA.chargeSeq=DC.chargeSeq AND " +
            "DC.moduleSeq=:MODULE_SEQ")
    List<Charge> findDefaultChargesByModuleSeq(@Param("MODULE_SEQ") Integer userSeq);*/

    List<Charge> findByChargeNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer status);

    Charge findByChargeSeq(Integer chargeSeq);


    List<Charge> findByCompanyProfileSeq(Integer companyProfileSeq);

    List<Charge> findByCompanyProfileSeqAndFinanceIntegrationIsNull(Integer companyProfileSeq);
}
