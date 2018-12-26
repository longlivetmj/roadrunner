package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.CompanyModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyModuleService extends JpaRepository<CompanyModule, Integer> {

    List<CompanyModule> findByCompanyProfileSeq(Integer companyProfileSeq);

    CompanyModule findByCompanyProfileSeqAndModuleSeq(Integer companyProfileSeq, Integer moduleSeq);
}
