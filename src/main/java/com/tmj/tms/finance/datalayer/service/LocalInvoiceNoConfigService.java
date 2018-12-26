package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.LocalInvoiceNoConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalInvoiceNoConfigService extends JpaRepository<LocalInvoiceNoConfig, Integer> {

    LocalInvoiceNoConfig findByCompanyProfileSeqAndDepartmentSeqAndLetterCode(Integer companyProfileSeq, Integer departmentSeq, String letterCode);


}
