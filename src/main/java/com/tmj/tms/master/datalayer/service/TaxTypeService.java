package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.TaxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxTypeService extends JpaRepository<TaxType, Integer> {

    List<TaxType> findByStatus(Integer status);

    List<TaxType> findByTaxTypeNameStartsWithIgnoreCaseAndStatus(String taxTypeName, Integer Status);

    TaxType findByTaxTypeNameAndStatus(String taxTypeName, Integer statusSeq);
}
