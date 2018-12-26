package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.LocalCreditNoteRefNoConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalCreditNoteRefNoConfigService extends JpaRepository<LocalCreditNoteRefNoConfig, Integer> {
    LocalCreditNoteRefNoConfig findByCompanyProfileSeqAndDepartmentSeqAndCreditDebitStatus(Integer companyProfileSeq, Integer departmentSeq, String creditOrDebitStatus);
}
