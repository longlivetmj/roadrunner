package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportService extends JpaRepository<Report, Integer> {

    List<Report> findByDocumentLinkSeqAndModuleSeqAndCompanyProfileSeqAndStatusOrderByReportName(Integer documentLinkSeq, Integer moduleSeq,
                                                                                                 Integer companyProfileSeq, Integer statusSeq);

    List<Report> findByStatusAndDocumentLinkSeqAndCompanyProfileSeq(Integer statusSeq, Integer documentLinkSeq, Integer companyProfileSeq);
}
