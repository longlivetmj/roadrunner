package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.SystemProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemPropertyService extends JpaRepository<SystemProperty, Integer> {
    SystemProperty findByPropertyName(String propertyName);
}
