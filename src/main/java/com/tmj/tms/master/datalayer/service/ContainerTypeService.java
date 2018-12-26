package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.ContainerType;
import com.tmj.tms.master.datalayer.modal.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerTypeService extends JpaRepository<ContainerType, Integer> {

    List<ContainerType> findByContainerTypeNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer statusSeq);

}
