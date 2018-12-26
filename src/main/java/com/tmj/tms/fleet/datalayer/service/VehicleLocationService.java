package com.tmj.tms.fleet.datalayer.service;

import com.tmj.tms.fleet.datalayer.modal.VehicleLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleLocationService extends JpaRepository<VehicleLocation, String> {
}
