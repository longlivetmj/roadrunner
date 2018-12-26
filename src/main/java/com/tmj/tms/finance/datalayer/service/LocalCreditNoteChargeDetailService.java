package com.tmj.tms.finance.datalayer.service;

import com.tmj.tms.finance.datalayer.modal.LocalCreditNoteChargeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalCreditNoteChargeDetailService extends JpaRepository<LocalCreditNoteChargeDetail, Integer> {

    List<LocalCreditNoteChargeDetail> findByLocalCreditNoteHeaderSeq(Integer localCreditNoteHeaderSeq);

    LocalCreditNoteChargeDetail findByFinancialChargeDetailSeqAndStatusNotIn(Integer intFinancialChargeDetailSeq, Integer statusSeq);
}
