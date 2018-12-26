package com.tmj.tms.fleet.bussinesslayer.manager;

import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.VehicleSearch;
import com.tmj.tms.utility.ResponseObject;

import java.security.Principal;
import java.util.List;

public interface VehicleManagementControllerManager {

    ResponseObject saveVehicle(Vehicle vehicle, Principal principal);

    ResponseObject updateVehicle(Vehicle vehicle);

    List<Vehicle> searchVehicle(VehicleSearch vehicleSearch);
}
