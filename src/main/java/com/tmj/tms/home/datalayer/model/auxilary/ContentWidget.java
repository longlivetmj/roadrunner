package com.tmj.tms.home.datalayer.model.auxilary;

import java.util.Objects;

public class ContentWidget {
    private String title;
    private String subTitle;
    private String message;
    private String icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentWidget that = (ContentWidget) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(subTitle, that.subTitle) &&
                Objects.equals(message, that.message) &&
                Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, subTitle, message, icon);
    }
}
