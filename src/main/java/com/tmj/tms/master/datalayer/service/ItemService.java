package com.tmj.tms.master.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphQueryDslPredicateExecutor;
import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.master.datalayer.modal.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemService extends JpaEntityGraphRepository<Item, Integer>, JpaEntityGraphQueryDslPredicateExecutor<Item> {

    List<Item> findByItemNameContainingIgnoreCaseAndStatusAndCompanyProfileSeq(String searchString, Integer status, Integer companyProfileSeq);
}
