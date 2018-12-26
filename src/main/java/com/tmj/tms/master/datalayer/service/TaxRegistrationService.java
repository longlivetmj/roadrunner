package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TaxRegistrationService extends JpaRepository<TaxRegistration, Integer> {

    TaxRegistration findByTaxNameAndCompanyProfileSeq(String taxName, Integer companyProfileSeq);

    List<TaxRegistration> findByStatusIn(Collection<Integer> statusSeqList);

    List<TaxRegistration> findByTaxNameContainingIgnoreCaseAndRemarksContainingIgnoreCaseAndCompanyProfileSeqAndStatusIn(String taxName, String remarks, Integer customerProfileSeq, Collection<Integer> statusSeqList);

    List<TaxRegistration> findByTaxNameContainingIgnoreCaseAndRemarksContainingIgnoreCaseAndCompanyProfileSeqAndCountrySeqAndAndStatusIn(String taxName, String remarks, Integer customerProfileSeq, Integer countrySeq, Collection<Integer> statusSeqList);

    List<TaxRegistration> findByCompanyProfileSeqAndTaxTypeSeqAndStatus(Integer companyProfileSeq, Integer taxTypeSeq, Integer status);

    TaxRegistration findByTaxTypeSeq(Integer vatTaxType);

    List<TaxRegistration> findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer status);

    TaxRegistration findByTaxTypeSeqAndStatusAndCompanyProfileSeq(Integer taxType, Integer statusSeq, Integer companyProfileSeq);
}
