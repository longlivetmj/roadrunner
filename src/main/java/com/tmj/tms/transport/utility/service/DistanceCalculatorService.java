package com.tmj.tms.transport.utility.service;

import com.google.maps.DirectionsApi;
import com.google.maps.model.LatLng;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.transport.utility.DistanceAndDuration;

import java.util.Date;
import java.util.List;

public interface DistanceCalculatorService {

    DistanceAndDuration calculateDistance(Date date, Boolean isForCalculateArrivalTime,
                                          DirectionsApi.RouteRestriction routeRestriction,
                                          Integer origin, List<Integer> wayPoints, Integer destination);

    Double calculateChargeableDistance(Integer transportBookingSeq);

    LatLng calculateCentroid(List<FinalDestination> finalDestinationList);

    Double calculatePlacementDistance(Integer transportBookingSeq);

}
