package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Location;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface LocationManagementControllerManager {

    ResponseObject createLocation(Location location);

    ResponseObject updateLocation(Location location);

    ResponseObject deleteLocation(Integer locationSeq);

    List<Location> searchLocation(String locationName, Integer countrySeq);

}
