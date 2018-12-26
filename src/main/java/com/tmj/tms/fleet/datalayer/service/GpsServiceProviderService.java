package com.tmj.tms.fleet.datalayer.service;

import com.tmj.tms.fleet.datalayer.modal.GpsServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpsServiceProviderService extends JpaRepository<GpsServiceProvider, Integer> {

    List<GpsServiceProvider> findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer status);

}
