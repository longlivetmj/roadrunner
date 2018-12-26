package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.LocationManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Location;
import com.tmj.tms.master.datalayer.service.LocationService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationManagementControllerManagerImpl implements LocationManagementControllerManager {

    private final LocationService locationService;

    @Autowired
    public LocationManagementControllerManagerImpl(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public ResponseObject createLocation(Location location) {
        location = this.locationService.save(location);
        ResponseObject responseObject = new ResponseObject("Location Saved Successfully", true);
        responseObject.setObject(location);
        return responseObject;
    }

    @Override
    public ResponseObject updateLocation(Location location) {
        ResponseObject responseObject;
        Location dbLocation = this.locationService.findOne(location.getLocationSeq());
        if (dbLocation != null) {
            if (!dbLocation.equals(location)) {
                location = this.locationService.save(location);
                responseObject = new ResponseObject("Location Saved Successfully", true);
                responseObject.setObject(location);
            } else {
                responseObject = new ResponseObject("No Amendments Found !!", false);
            }
        } else {
            responseObject = new ResponseObject("Location Not found !!", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteLocation(Integer locationSeq) {
        Location dbLocation = this.locationService.findOne(locationSeq);
        dbLocation.setStatus(0);
        dbLocation = this.locationService.save(dbLocation);
        ResponseObject responseObject = new ResponseObject("Location Deleted Successfully", true);
        responseObject.setObject(dbLocation);
        return responseObject;
    }

    @Override
    public List<Location> searchLocation(String locationName, Integer countrySeq) {
        List<Location> locationList = new ArrayList<>();
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@LocationManagement_VIEW-DELETE");
            if (locationName.equals("") && countrySeq == null) {
                locationList = this.locationService.findByStatusIn(statusSeqList);
            } else if (countrySeq == null) {
                locationList = this.locationService.findByLocationNameContainingIgnoreCaseAndStatusIn(locationName, statusSeqList);
            } else {
                locationList = this.locationService.findByLocationNameContainingIgnoreCaseAndCountrySeqAndStatusIn(locationName, countrySeq, statusSeqList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationList;
    }

}
