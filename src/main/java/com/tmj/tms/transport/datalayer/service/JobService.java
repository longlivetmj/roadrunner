package com.tmj.tms.transport.datalayer.service;

import com.tmj.tms.transport.datalayer.modal.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobService extends JpaRepository<Job, Integer> {

    List<Job> findTop100ByCompanyProfileSeqAndModuleSeqAndUpdateFlag(Integer companyProfileSeq, Integer moduleSeq, Integer yesOrNoSeq);

    Job findByModuleSeqAndCompanyProfileSeqAndReferenceSeq(Integer moduleSeq, Integer companyProfileSeq, Integer referenceSeq);
}
