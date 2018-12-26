package com.tmj.tms.home.datalayer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "widget")
public class Widget {
    private Integer widgetSeq;
    private String widgetName;
    private Integer widgetSize;
    private String controllerUrl;
    private Integer status;
    private Integer rowNo;
    private String heading;
    private String subHeading;
    private String widgetText1;
    private String widgetText2;
    private String widgetText3;
    private String widgetText4;
    private String widgetText5;
    private String widgetText6;
    private String widgetText7;
    private String icon1;
    private String icon2;
    private String icon3;
    private Integer displayOrder;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
    @SequenceGenerator(name = "G1", sequenceName = "widget_seq", allocationSize = 1)
    @Column(name = "widget_seq", nullable = false, unique = true)
    public Integer getWidgetSeq() {
        return widgetSeq;
    }

    public void setWidgetSeq(Integer widgetSeq) {
        this.widgetSeq = widgetSeq;
    }

    @Basic
    @Column(name = "widget_name")
    public String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    @Basic
    @Column(name = "widget_size")
    public Integer getWidgetSize() {
        return widgetSize;
    }

    public void setWidgetSize(Integer widgetSize) {
        this.widgetSize = widgetSize;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "controller_url")
    public String getControllerUrl() {
        return controllerUrl;
    }

    public void setControllerUrl(String controllerUrl) {
        this.controllerUrl = controllerUrl;
    }

    @Basic
    @Column(name = "row_no")
    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    @Basic
    @Column(name = "heading")
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Basic
    @Column(name = "sub_heading")
    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    @Basic
    @Column(name = "widget_text1")
    public String getWidgetText1() {
        return widgetText1;
    }

    public void setWidgetText1(String widgetText1) {
        this.widgetText1 = widgetText1;
    }

    @Basic
    @Column(name = "widget_text2")
    public String getWidgetText2() {
        return widgetText2;
    }

    public void setWidgetText2(String widgetText2) {
        this.widgetText2 = widgetText2;
    }

    @Basic
    @Column(name = "widget_text3")
    public String getWidgetText3() {
        return widgetText3;
    }

    public void setWidgetText3(String widgetText3) {
        this.widgetText3 = widgetText3;
    }

    @Basic
    @Column(name = "widget_text4")
    public String getWidgetText4() {
        return widgetText4;
    }

    public void setWidgetText4(String widgetText4) {
        this.widgetText4 = widgetText4;
    }

    @Basic
    @Column(name = "widget_text5")
    public String getWidgetText5() {
        return widgetText5;
    }

    public void setWidgetText5(String widgetText5) {
        this.widgetText5 = widgetText5;
    }

    @Basic
    @Column(name = "widget_text6")
    public String getWidgetText6() {
        return widgetText6;
    }

    public void setWidgetText6(String widgetText6) {
        this.widgetText6 = widgetText6;
    }

    @Basic
    @Column(name = "widget_text7")
    public String getWidgetText7() {
        return widgetText7;
    }

    public void setWidgetText7(String widgetText7) {
        this.widgetText7 = widgetText7;
    }

    @Basic
    @Column(name = "icon1")
    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    @Basic
    @Column(name = "icon2")
    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }

    @Basic
    @Column(name = "icon3")
    public String getIcon3() {
        return icon3;
    }

    public void setIcon3(String icon3) {
        this.icon3 = icon3;
    }

    @Basic
    @Column(name = "display_order")
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Widget widget = (Widget) o;
        return Objects.equals(widgetSeq, widget.widgetSeq) &&
                Objects.equals(widgetName, widget.widgetName) &&
                Objects.equals(widgetSize, widget.widgetSize) &&
                Objects.equals(controllerUrl, widget.controllerUrl) &&
                Objects.equals(status, widget.status) &&
                Objects.equals(rowNo, widget.rowNo) &&
                Objects.equals(heading, widget.heading) &&
                Objects.equals(subHeading, widget.subHeading) &&
                Objects.equals(widgetText1, widget.widgetText1) &&
                Objects.equals(widgetText2, widget.widgetText2) &&
                Objects.equals(widgetText3, widget.widgetText3) &&
                Objects.equals(widgetText4, widget.widgetText4) &&
                Objects.equals(widgetText5, widget.widgetText5) &&
                Objects.equals(widgetText6, widget.widgetText6) &&
                Objects.equals(widgetText7, widget.widgetText7) &&
                Objects.equals(icon1, widget.icon1) &&
                Objects.equals(icon2, widget.icon2) &&
                Objects.equals(icon3, widget.icon3) &&
                Objects.equals(displayOrder, widget.displayOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(widgetSeq, widgetName, widgetSize, controllerUrl, status, rowNo, heading, subHeading, widgetText1, widgetText2, widgetText3, widgetText4, widgetText5, widgetText6, widgetText7, icon1, icon2, icon3, displayOrder);
    }
}
