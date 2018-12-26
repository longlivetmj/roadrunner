package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.LocalCreditNoteTaxRateMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalCreditNoteTaxRateMappingService extends JpaRepository<LocalCreditNoteTaxRateMapping, Integer> {

    LocalCreditNoteTaxRateMapping findByLocalCreditNoteHeaderSeqAndFinancialChargeDetailSeq(Integer localCreditNoteHeaderSeq, Integer financialChargeDetailSeq);

    List<LocalCreditNoteTaxRateMapping> findByLocalCreditNoteHeaderSeq(Integer localCreditNoteHeaderSeq);

}
