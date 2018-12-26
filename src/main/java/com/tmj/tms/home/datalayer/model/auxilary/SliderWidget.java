package com.tmj.tms.home.datalayer.model.auxilary;

import java.util.List;
import java.util.Objects;

public class SliderWidget {

    private List<ContentWidget> contentWidgetList;

    public List<ContentWidget> getContentWidgetList() {
        return contentWidgetList;
    }

    public void setContentWidgetList(List<ContentWidget> contentWidgetList) {
        this.contentWidgetList = contentWidgetList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SliderWidget that = (SliderWidget) o;
        return Objects.equals(contentWidgetList, that.contentWidgetList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentWidgetList);
    }
}
