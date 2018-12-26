package com.tmj.tms.transport.utility.service;

import com.tmj.tms.transport.datalayer.modal.auxiliary.TransportBookingStatusUpdateAux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportBookingStatusUpdateAuxService extends JpaRepository<TransportBookingStatusUpdateAux, Integer> {

    List<TransportBookingStatusUpdateAux> findByCompanyProfileSeqAndCurrentStatus(Integer companyProfileSeq, Integer currentStatus);

    List<TransportBookingStatusUpdateAux> findByCompanyProfileSeqAndCurrentStatusNotInAndTransportBookingSeqIn(Integer companyProfileSeq, List<Integer> currentStatus, List<Integer> referenceSeqList);

}
