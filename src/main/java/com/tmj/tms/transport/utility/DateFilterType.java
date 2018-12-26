package com.tmj.tms.transport.utility;

import java.util.Arrays;

public enum DateFilterType {

    CREATED_DATE("Created", 1),
    ARRIVING_DATE("Arriving", 2),
    DELIVERING_DATE("Delivering", 3),
    ARRIVED_DATE("Arrived", 4),
    DELIVERED_DATE("Delivered", 5),
    DOCUMENT_COLLECTED_DATE("Document Collected Date", 6);

    private String dateFilterTypeDescription;
    private Integer dateFilterType;

    DateFilterType(String dateFilterTypeDescription, Integer dateFilterType) {
        this.dateFilterTypeDescription = dateFilterTypeDescription;
        this.dateFilterType = dateFilterType;
    }

    public static DateFilterType findOne(Integer dateFilterType) {
        return Arrays.stream(DateFilterType.values())
                .filter(x -> x.dateFilterType.equals(dateFilterType))
                .findFirst()
                .orElse(null);
    }

    public String getDateFilterTypeDescription() {
        return dateFilterTypeDescription;
    }

    public void setDateFilterTypeDescription(String dateFilterTypeDescription) {
        this.dateFilterTypeDescription = dateFilterTypeDescription;
    }

    public Integer getDateFilterType() {
        return dateFilterType;
    }

    public void setDateFilterType(Integer dateFilterType) {
        this.dateFilterType = dateFilterType;
    }
}
