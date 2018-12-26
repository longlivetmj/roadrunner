package com.tmj.tms.home.datalayer.service;

import com.tmj.tms.home.datalayer.model.TempEmployeeStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface TempEmployeeStatisticsService extends JpaRepository<TempEmployeeStatistics, Integer> {

    TempEmployeeStatistics findByEmployeeSeqAndYearAndMonthAndModuleSeq(Integer employeeSeq, Integer year, Integer month, Integer moduleSeq);

    List<TempEmployeeStatistics> findByCompanyProfileSeqAndEmployeeDesignationSeqAndYearAndMonthOrderByScore(Integer companyProfileSeq, Integer designationSeq, Integer year, Integer month);
}
