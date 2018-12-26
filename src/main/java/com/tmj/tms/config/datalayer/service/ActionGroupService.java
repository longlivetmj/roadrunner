package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.ActionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionGroupService extends JpaRepository<ActionGroup, Integer>{

    List<ActionGroup> findBySubModuleSeq(Integer subModuleSeq);
}
