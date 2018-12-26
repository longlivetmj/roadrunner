package com.tmj.tms.home.businesslayer.manager;

import com.tmj.tms.fleet.datalayer.modal.auxiliary.VehicleTrackingSearch;
import com.tmj.tms.fleet.integration.auxiliary.VehicleTrackingResponse;

import java.util.List;

public interface VehicleTrackingControllerManager {

    List<VehicleTrackingResponse> searchVehicle(VehicleTrackingSearch vehicleTrackingSearch);

}
