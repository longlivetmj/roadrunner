package com.tmj.tms.home.datalayer.model.auxilary;

import com.tmj.tms.home.datalayer.model.Widget;

import java.util.List;

public class ProgressWidgetWithLocations {

    private Integer widgetSeq;
    private String heading;
    private String subHeading;
    private String description;
    private String count;
    private String comparison;
    private Integer maxCount;
    private List<KeyValuePairCustom> keyValuePairCustomList;
    private String mapHeading;
    private String mapSubHeading;
    private String latLongData;
    private Widget widget;

    public Integer getWidgetSeq() {
        return widgetSeq;
    }

    public void setWidgetSeq(Integer widgetSeq) {
        this.widgetSeq = widgetSeq;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public List<KeyValuePairCustom> getKeyValuePairCustomList() {
        return keyValuePairCustomList;
    }

    public void setKeyValuePairCustomList(List<KeyValuePairCustom> keyValuePairCustomList) {
        this.keyValuePairCustomList = keyValuePairCustomList;
    }

    public String getLatLongData() {
        return latLongData;
    }

    public void setLatLongData(String latLongData) {
        this.latLongData = latLongData;
    }

    public String getMapHeading() {
        return mapHeading;
    }

    public void setMapHeading(String mapHeading) {
        this.mapHeading = mapHeading;
    }

    public String getMapSubHeading() {
        return mapSubHeading;
    }

    public void setMapSubHeading(String mapSubHeading) {
        this.mapSubHeading = mapSubHeading;
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }
}
