package com.tmj.tms.config.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.config.datalayer.modal.CompanyProperty;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyPropertyService extends JpaEntityGraphRepository<CompanyProperty, Integer> {

    CompanyProperty findByPropertyName(String draft_stakeholder_approver);

}
