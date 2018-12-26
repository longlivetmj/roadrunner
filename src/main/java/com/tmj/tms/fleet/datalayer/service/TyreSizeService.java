package com.tmj.tms.fleet.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.fleet.datalayer.modal.TyreSize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TyreSizeService extends JpaEntityGraphRepository<TyreSize, Integer>, JpaEntityGraphQueryDslPredicateExecutor<TyreSize> {

    List<TyreSize> findByStatus(Integer statusSeq);
}
