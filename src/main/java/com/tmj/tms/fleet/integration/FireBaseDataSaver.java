package com.tmj.tms.fleet.integration;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.VehicleLocation;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.fleet.integration.auxiliary.VehicleLocationFireBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireBaseDataSaver {

    private final VehicleService vehicleService;

    @Autowired
    public FireBaseDataSaver(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void save(VehicleLocation vehicleLocation) {
        try {
            Vehicle vehicle = this.vehicleService.findByGpsTerminalKey(vehicleLocation.getGpsTerminalKey());
            if (vehicle != null) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("/");
                DatabaseReference usersRef = ref.child("vehicles/" + vehicleLocation.getGpsTerminalKey());
                VehicleLocationFireBase vehicleLocationFireBase = new VehicleLocationFireBase(vehicleLocation.getGpsTerminalKey(), vehicleLocation.getLatitude(), vehicleLocation.getLongitude(), vehicleLocation.getDirection(), vehicleLocation.getSpeed(), vehicle.getVehicleNo());
                usersRef.setValueAsync(vehicleLocationFireBase);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
