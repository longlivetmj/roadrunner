package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.StakeholderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StakeholderTypeService extends JpaRepository<StakeholderType, Integer> {

    List<StakeholderType> findByStakeholderTypeNameInAndStatus(Collection<String> stakeholderTypeNameList, Integer status);

    List<StakeholderType> findByStakeholderTypeNameStartsWithIgnoreCaseAndStatus(String stakeholderTypeName, Integer status);

    List<StakeholderType> findByStatus(Integer status);

    StakeholderType findByStakeholderTypeCodeAndStatus(String stakeholderTypeCode, Integer status);

    List<StakeholderType> findByStakeholderTypeCodeInAndStatus(List<String> stakeholderTypeCode, Integer status);
}
