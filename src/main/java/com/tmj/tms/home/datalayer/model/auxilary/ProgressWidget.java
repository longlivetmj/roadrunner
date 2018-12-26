package com.tmj.tms.home.datalayer.model.auxilary;

import java.util.List;

public class ProgressWidget {

    private Integer widgetSeq;
    private String heading;
    private String subHeading;
    private String description;
    private String count;
    private String comparison;
    private Integer maxCount;
    private List<KeyValuePairCustom> keyValuePairCustomList;

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

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }
}
