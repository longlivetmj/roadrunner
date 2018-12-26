package com.tmj.tms.fleet.datalayer.service;

import com.tmj.tms.fleet.datalayer.modal.auxiliary.JobAssignedVehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAssignedVehiclesService extends JpaRepository<JobAssignedVehicles, Integer>, QueryDslPredicateExecutor<JobAssignedVehicles> {

    JobAssignedVehicles findByVehicleSeq(Integer vehicleSeq);

}
