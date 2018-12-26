package com.tmj.tms.transport.utility;

import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.transport.datalayer.modal.TransportBookingViaLocation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViaLocationFormatter {

    public static String getViaLocationAsStringTLD(List<TransportBookingViaLocation> transportBookingViaLocationList) {
        String viaLocationString = "";
        try {
            if (transportBookingViaLocationList != null && transportBookingViaLocationList.size() > 0) {
                List<FinalDestination> finalDestinationList = transportBookingViaLocationList.stream().map(TransportBookingViaLocation::getViaLocation).collect(Collectors.toList());
                viaLocationString = finalDestinationList.stream().map(FinalDestination::getDestination).collect(Collectors.joining(" VIA "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viaLocationString;
    }

    public String getViaLocationAsString(List<TransportBookingViaLocation> transportBookingViaLocationList) {
        String viaLocationString = "";
        try {
            if (transportBookingViaLocationList != null && transportBookingViaLocationList.size() > 0) {
                List<FinalDestination> finalDestinationList = transportBookingViaLocationList.stream().map(TransportBookingViaLocation::getViaLocation).collect(Collectors.toList());
                viaLocationString = finalDestinationList.stream().map(FinalDestination::getDestination).collect(Collectors.joining(" VIA "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viaLocationString;
    }
}
