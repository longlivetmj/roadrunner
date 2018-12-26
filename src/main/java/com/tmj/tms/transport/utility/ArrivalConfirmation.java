package com.tmj.tms.transport.utility;

import java.util.Arrays;

public enum ArrivalConfirmation {

    CONFIRMED("Confirmed", 1),
    LATE("Late", 2),
    DECLINED("Declined", 3),
    UNREACHABLE("Unreachable", 4);

    private String arrivalConfirmationDescription;
    private Integer arrivalConfirmation;

    ArrivalConfirmation(String arrivalConfirmationDescription, Integer arrivalConfirmation) {
        this.arrivalConfirmationDescription = arrivalConfirmationDescription;
        this.arrivalConfirmation = arrivalConfirmation;
    }

    public static ArrivalConfirmation findOne(Integer arrivalConfirmation) {
        return Arrays.stream(ArrivalConfirmation.values())
                .filter(x -> x.arrivalConfirmation.equals(arrivalConfirmation))
                .findFirst()
                .orElse(null);
    }

    public static String valuesForJEditable() {
        String arrivalConfirmationString = "";
        ArrivalConfirmation[] arrivalConfirmations = ArrivalConfirmation.values();
        for (int x = 0; x < arrivalConfirmations.length; x++) {
            String arrivalConfirmationDescription = arrivalConfirmations[x].getArrivalConfirmationDescription().replaceAll("'", "");
            if (arrivalConfirmationString.length() == 0) {
                arrivalConfirmationString = "{";
            }
            if (x != arrivalConfirmations.length - 1) {
                arrivalConfirmationString = arrivalConfirmationString + "'" + arrivalConfirmations[x].getArrivalConfirmation() + "':'" + arrivalConfirmationDescription + "',";
            } else {
                arrivalConfirmationString = arrivalConfirmationString + "'" + arrivalConfirmations[x].getArrivalConfirmation() + "':'" + arrivalConfirmationDescription + "'}";
            }
        }
        return arrivalConfirmationString;
    }

    public String getArrivalConfirmationDescription() {
        return arrivalConfirmationDescription;
    }

    public void setArrivalConfirmationDescription(String arrivalConfirmationDescription) {
        this.arrivalConfirmationDescription = arrivalConfirmationDescription;
    }

    public Integer getArrivalConfirmation() {
        return arrivalConfirmation;
    }

    public void setArrivalConfirmation(Integer arrivalConfirmation) {
        this.arrivalConfirmation = arrivalConfirmation;
    }
}
