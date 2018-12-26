package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LocationService extends JpaRepository<Location, Integer> {

    Location findByLocationName(String locationName);

    List<Location> findByLocationNameContainingIgnoreCaseAndStatusIn(String locationName, Collection<Integer> statusSeqList);

    List<Location> findByLocationNameContainingIgnoreCaseAndCountrySeqAndStatusIn(String locationName, Integer countrySeq, Collection<Integer> statusSeqList);

    List<Location> findByCountrySeq(Integer countrySeq);

    List<Location> findByLocationNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer status);

    List<Location> findByStatus(Integer status);

    List<Location> findByStatusIn(Collection<Integer> statusSeqList);

    List<Location> findByLocationNameContainingIgnoreCaseAndStatus(String searchParam, Integer statusSeq);

    Location findByLocationNameContainingIgnoreCase(String location);

    Location findByLocationNameIgnoreCase(String location);
}
