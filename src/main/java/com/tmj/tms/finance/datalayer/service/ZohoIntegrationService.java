package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZohoIntegrationService extends JpaRepository<ZohoIntegration, Integer> {

    ZohoIntegration findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer status);
}
