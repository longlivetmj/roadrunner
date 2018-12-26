package com.tmj.tms.fleet.datalayer.service;

import com.tmj.tms.fleet.datalayer.modal.FuelTypeVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelTypeVariantService extends JpaRepository<FuelTypeVariant, Integer> {

    List<FuelTypeVariant> findByCountrySeqAndFuelTypeAndStatus(Integer countrySeq, Integer fuelType, Integer status);
}
