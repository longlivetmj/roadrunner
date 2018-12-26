package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.SalesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesTypeService extends JpaRepository<SalesType,Integer> {
    List<SalesType> findByStatus(Integer status);

    SalesType findBySalesTypeNameAndStatus(String salesTypeName, Integer status);
}
