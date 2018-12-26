package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface FinalDestinationManagementControllerManager {

    ResponseObject saveDestination(FinalDestination finalDestination);

    ResponseObject updateDestination(FinalDestination finalDestination);

    ResponseObject deleteDestination(Integer finalDestinationSeq);

    List<FinalDestination> searchDestination(String finalDestinationCode, Integer countrySeq, String city, String state, String zip);
}
