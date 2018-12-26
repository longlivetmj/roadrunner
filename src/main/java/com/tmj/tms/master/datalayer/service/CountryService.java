package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CountryService extends JpaRepository<Country, Integer> {

    Country findByCountryName(String countryName);

    List<Country> findByCountryNameContainingIgnoreCaseAndCountryCodeContainingIgnoreCaseAndStatusIn(String countryName, String countryCode, Collection<Integer> status);

    List<Country> findByStatus(Integer status);

    List<Country> findByStatusIn(Collection<Integer> status);

    List<Country> findByCountryNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer status);

    List<Country> findByCountryNameContainingIgnoreCaseAndStatus(String searchParam, Integer statusSeq);

//    List<Country> findByCountryNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer status, EntityGraph entityGraph);

}
