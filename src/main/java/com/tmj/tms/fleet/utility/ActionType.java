package com.tmj.tms.fleet.utility;

import java.util.Arrays;

public enum ActionType {

    SERVICE(1, "Service"),
    MAINTENANCE(2, "Maintenance");

    private Integer actionType;
    private String actionTypeDescription;

    ActionType(Integer actionType, String actionTypeDescription) {
        this.actionType = actionType;
        this.actionTypeDescription = actionTypeDescription;
    }

    public static ActionType findOne(Integer actionType) {
        return Arrays.stream(ActionType.values())
                .filter(x -> x.actionType.equals(actionType))
                .findFirst()
                .orElse(null);
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getActionTypeDescription() {
        return actionTypeDescription;
    }

    public void setActionTypeDescription(String actionTypeDescription) {
        this.actionTypeDescription = actionTypeDescription;
    }
}
