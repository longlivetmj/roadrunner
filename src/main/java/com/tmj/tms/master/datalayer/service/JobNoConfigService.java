package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.JobNoConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobNoConfigService extends JpaRepository<JobNoConfig, Integer> {

    JobNoConfig findByCompanyProfileSeqAndDepartmentSeqAndFinancialYear(Integer companyProfileSeq, Integer departmentSeq, String financialYear);

}
