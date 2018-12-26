package com.tmj.tms.transport.utility;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.service.FinalDestinationService;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingViaLocation;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.utility.service.DistanceCalculatorService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.NumberFormatter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DistanceCalculator implements DistanceCalculatorService {

    private static final String API_KEY = "AIzaSyA9PP0PZRdd1n5Tk_uZSjRUvU3_27E6gx8"; //roadrunner
//    private static final String API_KEY = "AIzaSyCIebKHg-MRTQUe_PnuYeDOJOtGgiDSo9c"; //longlivetmj

    private final TransportBookingService transportBookingService;
    private final FinalDestinationService finalDestinationService;

    @Autowired
    public DistanceCalculator(TransportBookingService transportBookingService,
                              FinalDestinationService finalDestinationService) {
        this.transportBookingService = transportBookingService;
        this.finalDestinationService = finalDestinationService;
    }

    @Override
    @Transactional
    public DistanceAndDuration calculateDistance(Date date, Boolean isForCalculateArrivalTime,
                                                 DirectionsApi.RouteRestriction routeRestriction,
                                                 Integer origin, List<Integer> wayPoints, Integer destination) {
        DistanceAndDuration distanceAndDuration = new DistanceAndDuration();
        try {
            GeoApiContext context = new GeoApiContext.Builder().apiKey(API_KEY).build();
            if (date.before(new Date())) {
                date = new Date();
            }
            if (routeRestriction == null) {
                routeRestriction = DirectionsApi.RouteRestriction.TOLLS;
            }
            FinalDestination originLocation = this.finalDestinationService.findOne(origin);
            FinalDestination destinationLocation = this.finalDestinationService.findOne(destination);
            List<FinalDestination> wayPointLocationList = null;
            if (wayPoints != null) {
                wayPointLocationList = this.finalDestinationService.findByFinalDestinationSeqIn(wayPoints);
            }

            LatLng pick = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());
            LatLng[] viaList = null;
            if (wayPointLocationList != null && wayPointLocationList.size() > 0) {
                viaList = new LatLng[wayPointLocationList.size()];
                for (int index = 0; index < wayPointLocationList.size(); index++) {
                    LatLng via = new LatLng(wayPointLocationList.get(index).getLatitude(), wayPointLocationList.get(index).getLongitude());
                    viaList[index] = via;
                }
            }
            LatLng delivery = new LatLng(destinationLocation.getLatitude(), destinationLocation.getLongitude());
            DirectionsApiRequest req = DirectionsApi.newRequest(context);
            DateTime dateTime = new DateTime(date);
            if (isForCalculateArrivalTime) {
                req.departureTime(dateTime);
            } else {
                req.arrivalTime(dateTime);
            }
            DirectionsResult directionsResult;
            if (viaList != null && viaList.length > 0) {
                directionsResult = req.origin(pick)
                        .waypoints(viaList)
                        .destination(delivery)
                        .mode(TravelMode.DRIVING)
                        .avoid(routeRestriction)
                        .optimizeWaypoints(true)
                        .trafficModel(TrafficModel.BEST_GUESS)
                        .await();
            } else {
                directionsResult = req.origin(pick)
                        .destination(delivery)
                        .mode(TravelMode.DRIVING)
                        .avoid(routeRestriction)
                        .optimizeWaypoints(true)
                        .trafficModel(TrafficModel.BEST_GUESS)
                        .await();
            }

            Double distance = 0.0;
            Double minutes = 0.0;
            DirectionsRoute route = directionsResult.routes[0];
            for (DirectionsLeg leg : route.legs) {
                distance = distance + (leg.distance.inMeters / 1000);
                minutes = minutes + leg.duration.inSeconds / 60;
            }
            distanceAndDuration.setDistance(NumberFormatter.round(distance, 0));
            distanceAndDuration.setDurationInMinutes(minutes);
            distanceAndDuration.setDuration(DateFormatter.convertMinutesToHumanReadable(minutes.intValue()));
        } catch (NullPointerException e) {
            distanceAndDuration.setDistance(0.0);
            System.out.println("DistanceMatrix NullPointerException ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distanceAndDuration;
    }


    @Override
    public Double calculateChargeableDistance(Integer transportBookingSeq) {
        Double chargeableKm = 0.0;
        try {
            TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSeq);
            List<Integer> viaLocations = new ArrayList<>();
            if (transportBooking.getTransportBookingViaLocationList() != null && transportBooking.getTransportBookingViaLocationList().size() > 0) {
                for (TransportBookingViaLocation transportBookingViaLocation : transportBooking.getTransportBookingViaLocationList()) {
                    viaLocations.add(transportBookingViaLocation.getViaLocationSeq());
                }
            }
            if (viaLocations.size() == 0) {
                DistanceAndDuration distanceAndDuration = this.calculateDistance(new Date(), true, null, transportBooking.getPickupLocationSeq(), null, transportBooking.getDeliveryLocationSeq());
                chargeableKm = distanceAndDuration.getDistance() * 2;
            } else {
                DistanceAndDuration distanceAndDurationJob = this.calculateDistance(new Date(), true, null, transportBooking.getPickupLocationSeq(), viaLocations, transportBooking.getDeliveryLocationSeq());
                DistanceAndDuration distanceAndDurationReturn = this.calculateDistance(new Date(), true, null, transportBooking.getPickupLocationSeq(), null, transportBooking.getDeliveryLocationSeq());
                chargeableKm = distanceAndDurationJob.getDistance() + distanceAndDurationReturn.getDistance();
            }
            chargeableKm = NumberFormatter.round(chargeableKm * 102 / 100, 0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Distance Error ChargeableDistance In " + transportBookingSeq);
        }
        return chargeableKm;
    }


    @Override
    public LatLng calculateCentroid(List<FinalDestination> finalDestinationList) {
        double x = 0.;
        double y = 0.;
        int pointCount = finalDestinationList.size();
        for (FinalDestination finalDestination : finalDestinationList) {
            x = x + finalDestination.getLatitude();
            y = y + finalDestination.getLongitude();
        }
        x = x / pointCount;
        y = y / pointCount;

        return new LatLng(x, y);
    }

    @Override
    public Double calculatePlacementDistance(Integer transportBookingSeq) {
        Double placementKm = 0.0;
        try {
            TransportBooking transportBooking = this.transportBookingService.findOne(transportBookingSeq);
            if (transportBooking.getActualStartLocationSeq() != null) {
                DistanceAndDuration distanceAndDuration = this.calculateDistance(new Date(), true, null, transportBooking.getActualStartLocationSeq(), null, transportBooking.getPickupLocationSeq());
                if (distanceAndDuration != null && distanceAndDuration.getDistance() > 0) {
                    placementKm = distanceAndDuration.getDistance();
                }
            }

            if (transportBooking.getActualEndLocationSeq() != null) {
                DistanceAndDuration distanceAndDuration = this.calculateDistance(new Date(), true, null, transportBooking.getDeliveryLocationSeq(), null, transportBooking.getActualEndLocationSeq());
                if (distanceAndDuration != null && distanceAndDuration.getDistance() != null && distanceAndDuration.getDistance() > 0) {
                    placementKm = placementKm + distanceAndDuration.getDistance();
                }
            }
            if (placementKm != null) {
                placementKm = NumberFormatter.round(placementKm, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Distance Error PlacementDistance In " + transportBookingSeq);
        }
        return placementKm;
    }
}
