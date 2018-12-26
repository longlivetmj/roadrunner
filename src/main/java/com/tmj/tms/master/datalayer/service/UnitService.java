package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UnitService extends JpaRepository<Unit, Integer> {

    Unit findByUnitName(String unitName);

    List<Unit> findByUnitNameContainingIgnoreCaseAndStatusIn(String unitName, Collection<Integer> statusSeqList);

    List<Unit> findByUnitCodeContainingIgnoreCaseAndStatusIn(String unitCode, Collection<Integer> statusSeqList);

    List<Unit> findByUsedForContainingIgnoreCaseAndStatusIn(String usedFor, Collection<Integer> statusSeqList);

    List<Unit> findByUnitCodeContainingIgnoreCaseAndUsedForContainingIgnoreCaseAndStatusIn(String unitCode, String usedFor, Collection<Integer> statusSeqList);

    List<Unit> findByUnitNameContainingIgnoreCaseAndUsedForContainingIgnoreCaseAndStatusIn(String unitName, String usedFor, Collection<Integer> statusSeqList);

    List<Unit> findByUnitNameContainingIgnoreCaseAndUnitCodeContainingIgnoreCaseAndStatusIn(String unitName, String unitCode, Collection<Integer> statusSeqList);

    List<Unit> findByUnitNameContainingIgnoreCaseAndUnitCodeContainingIgnoreCaseAndUsedForContainingIgnoreCaseAndStatusIn(String unitName, String unitCode, String usedFor, Collection<Integer> statusSeqList);

    List<Unit> findByStatusIn(Collection<Integer> statusSeqList);

    List<Unit> findByStatus(Integer status);

    List<Unit> findByUsedForAndStatus(String usedFor, Integer status);

    List<Unit> findByUsedForAndStatusOrderByUnitCodeAsc(String usedFor, Integer status);

    Unit findByUnitCode(String unitCode);

    List<Unit> findByUnitNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer status);

    List<Unit> findByUnitNameStartsWithIgnoreCaseAndUsedForAndStatus(String unitName, String usedFor, Integer status);
}
