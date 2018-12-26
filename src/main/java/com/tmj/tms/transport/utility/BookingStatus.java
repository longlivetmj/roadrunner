package com.tmj.tms.transport.utility;

import java.util.Arrays;

public enum BookingStatus {

    PENDING_FOR_APPROVAL("Pending for Approval", 1),
    APPROVED("Approved", 2),
    ACCEPTED("Accepted", 3),
    ATTENDED("Attended", 4),
    ARRIVED_AT_PICKUP("Arrived At Pickup", 5),
    DEPARTED_FROM_PICKUP("Departed From Pickup", 6),
    ARRIVED_AT_DELIVERY("Arrived at Delivery", 7),
    JOB_COMPLETED("Job Completed", 8),
    KM_CONFIRMED("Km Confirmed", 9),
    CHARGES_SUBMITTED("Charged Submitted", 10),
    JOB_CONFIRMED("Job Confirmed", 11),
    FINANCE_CONFIRMED("Finance Confirmed", 12),
    JOB_CLOSED("Job Closed", 13),
    CANCELLED("Cancelled", 101),
    REJECTED("Rejected", 102),
    JOB_DISPUTE("Job Dispute", 103),
    FINANCE_DISPUTE("Finance Dispute", 104);

    private String currentStatusDescription;
    private Integer currentStatus;

    BookingStatus(String currentStatusDescription, Integer currentStatus) {
        this.currentStatusDescription = currentStatusDescription;
        this.currentStatus = currentStatus;
    }

    public static BookingStatus findOne(Integer currentStatus) {
        return Arrays.stream(BookingStatus.values())
                .filter(x -> x.currentStatus.equals(currentStatus))
                .findFirst()
                .orElse(null);
    }

    public String getCurrentStatusDescription() {
        return currentStatusDescription;
    }

    public void setCurrentStatusDescription(String currentStatusDescription) {
        this.currentStatusDescription = currentStatusDescription;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }
}
