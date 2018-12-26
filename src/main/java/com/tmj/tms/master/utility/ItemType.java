package com.tmj.tms.master.utility;

import java.util.Arrays;

public enum ItemType {

    ITEM(1, "Item"),
    SERVICE(2, "Service");

    private Integer itemType;
    private String itemTypeDescription;

    ItemType(Integer itemType, String itemTypeDescription) {
        this.itemType = itemType;
        this.itemTypeDescription = itemTypeDescription;
    }

    public static ItemType findOne(Integer itemType) {
        return Arrays.stream(ItemType.values())
                .filter(x -> x.itemType.equals(itemType))
                .findFirst()
                .orElse(null);
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getItemTypeDescription() {
        return itemTypeDescription;
    }

    public void setItemTypeDescription(String itemTypeDescription) {
        this.itemTypeDescription = itemTypeDescription;
    }
}
