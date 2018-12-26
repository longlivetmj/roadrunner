package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.TableConstraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableConstraintService extends JpaRepository<TableConstraint, String> {
}
