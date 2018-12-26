package com.tmj.tms.master.datalayer.service;

import com.cosium.spring.data.jpa.entity.graph.repository.JpaEntityGraphRepository;
import com.tmj.tms.master.datalayer.modal.Branch;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BranchService extends JpaEntityGraphRepository<Branch, Integer> {

    Branch findByBranchCodeIgnoreCaseAndBranchNameIgnoreCaseAndBankBankSeq(String branchCode, String branchName, Integer bankSeq);

    List<Branch> findByBranchCodeContainingIgnoreCaseAndStatusIn(String branchCode, Collection<Integer> statusSeqList);

    List<Branch> findByBranchNameContainingIgnoreCaseAndStatusIn(String branchName, Collection<Integer> statusSeqList);

    List<Branch> findByBankBankNameContainingIgnoreCaseAndStatusIn(String bankName, Collection<Integer> statusSeqList);

    List<Branch> findByBranchCodeContainingIgnoreCaseAndBranchNameContainingIgnoreCaseAndStatusIn(String branchCode, String branchName, Collection<Integer> statusSeqList);

    List<Branch> findByBranchCodeContainingIgnoreCaseAndBankBankNameContainingIgnoreCaseAndStatusIn(String branchCode, String bankName, Collection<Integer> statusSeqList);

    List<Branch> findByBranchNameContainingIgnoreCaseAndBankBankNameContainingIgnoreCaseAndStatusIn(String branchName, String bankName, Collection<Integer> statusSeqList);

    List<Branch> findByBranchCodeContainingIgnoreCaseAndBranchNameContainingIgnoreCaseAndBankBankNameContainingIgnoreCaseAndStatusIn(String branchCode, String branchName, String bankName, Collection<Integer> statusSeqList);

    List<Branch> findByStatus(Integer status);

    List<Branch> findByStatusIn(Collection<Integer> statusSeqList);

    List<Branch> findByBranchNameStartingWithIgnoreCaseAndStatus(String branchCode, Integer status);

    List<Branch> findByBankBankSeqAndStatus(Integer bankSeq, Integer stataus);

}
