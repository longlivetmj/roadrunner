package com.tmj.tms.home.datalayer.model.auxilary;

import java.util.ArrayList;
import java.util.List;

public class BarChartWidget {

    private Integer widgetSeq;
    private String heading;
    private String subHeading;
    private String xLabels;
    private String xKeys;
    private String yKeys;
    private String yLabels;
    private String data;

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

    public String getxLabels() {
        return xLabels;
    }

    public void setxLabels(String xLabels) {
        this.xLabels = xLabels;
    }

    public String getxKeys() {
        return xKeys;
    }

    public void setxKeys(String xKeys) {
        this.xKeys = xKeys;
    }

    public String getyKeys() {
        return yKeys;
    }

    public void setyKeys(String yKeys) {
        this.yKeys = yKeys;
    }

    public String getyLabels() {
        return yLabels;
    }

    public void setyLabels(String yLabels) {
        this.yLabels = yLabels;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
}
