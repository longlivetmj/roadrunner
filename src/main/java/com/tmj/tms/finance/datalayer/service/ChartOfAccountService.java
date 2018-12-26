package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.ChartOfAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartOfAccountService extends JpaRepository<ChartOfAccount, Integer> {

    ChartOfAccount findByCompanyProfileSeqAndAccountTypeAndStatus(Integer companyProfileSeq, String accountType, Integer statusSeq);

}
