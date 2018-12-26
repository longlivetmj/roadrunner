package com.tmj.tms.fleet.bussinesslayer.manager;

import com.tmj.tms.fleet.datalayer.modal.VehicleFuel;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.FuelSearch;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.VehicleSearch;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface FuelManagementControllerManager {

    ResponseObject saveVehicleFuel(VehicleFuel vehicleFuel);

    ResponseObject updateVehicleFuel(VehicleFuel vehicleFuel);

    List<VehicleFuel> searchFuelData(FuelSearch fuelSearch);

    ResponseObject delete(Integer vehicleFuelSeq);
}
