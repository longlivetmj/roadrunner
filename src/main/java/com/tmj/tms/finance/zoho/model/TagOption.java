package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tag_option_id",
        "tag_option_name",
        "is_active",
        "status"
})
public class TagOption {

    @JsonProperty("tag_option_id")
    private String tagOptionId;

    @JsonProperty("tag_option_name")
    private String tagOptionName;

    @JsonProperty("is_active")
    private boolean isActive = true;

    @JsonProperty("status")
    private String status = "active";

    @JsonProperty("tag_option_id")
    public String getTagOptionId() {
        return tagOptionId;
    }

    public void setTagOptionId(String tagOptionId) {
        this.tagOptionId = tagOptionId;
    }

    @JsonProperty("tag_option_name")
    public String getTagOptionName() {
        return tagOptionName;
    }

    public void setTagOptionName(String tagOptionName) {
        this.tagOptionName = tagOptionName;
    }

    @JsonProperty("is_active")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TagOption{" +
                "tagOptionId='" + tagOptionId + '\'' +
                ", tagOptionName='" + tagOptionName + '\'' +
                ", isActive=" + isActive +
                ", status='" + status + '\'' +
                '}';
    }
}
