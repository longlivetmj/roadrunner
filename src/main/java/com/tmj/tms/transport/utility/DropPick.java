package com.tmj.tms.transport.utility;

import java.util.Arrays;

public enum DropPick {

    DROP("Drop", 0),
    PICK("Pick", 1),
    DROP_AND_PICK("Drop & Pick", 2);

    private String dropPickStatusDescription;
    private Integer dropPickStatus;

    DropPick(String dropPickStatusDescription, Integer dropPickStatus) {
        this.dropPickStatusDescription = dropPickStatusDescription;
        this.dropPickStatus = dropPickStatus;
    }

    public static DropPick findOne(Integer dropPickStatus) {
        return Arrays.stream(DropPick.values())
                .filter(x -> x.dropPickStatus.equals(dropPickStatus))
                .findFirst()
                .orElse(null);
    }

    public Integer getDropPickStatus() {
        return dropPickStatus;
    }

    public void setDropPickStatus(Integer dropPickStatus) {
        this.dropPickStatus = dropPickStatus;
    }

    public String getDropPickStatusDescription() {
        return dropPickStatusDescription;
    }

    public void setDropPickStatusDescription(String dropPickStatusDescription) {
        this.dropPickStatusDescription = dropPickStatusDescription;
    }

}
