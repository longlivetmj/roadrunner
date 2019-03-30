package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tag_id",
        "tag_name",
        "associated_with",
        "is_active",
        "status",
        "tag_options"
})
public class Tag {

    @JsonProperty("tag_id")
    private String tagId;

    @JsonProperty("tag_name")
    private String tagName;

    @JsonProperty("associated_with")
    private String associatedWith = "item";

    @JsonProperty("is_active")
    private boolean isActive = true;

    @JsonProperty("status")
    private String status = "active";

    @JsonProperty("tag_options")
    private List<TagOption> tagOptions = null;

    @JsonProperty("tag_id")
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @JsonProperty("tag_options")
    public List<TagOption> getTagOptions() {
        return tagOptions;
    }

    public void setTagOptions(List<TagOption> tagOptions) {
        this.tagOptions = tagOptions;
    }

    @JsonProperty("tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonProperty("associated_with")
    public String getAssociatedWith() {
        return associatedWith;
    }

    public void setAssociatedWith(String associatedWith) {
        this.associatedWith = associatedWith;
    }

    @JsonProperty("is_active")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @JsonProperty("status")
    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId='" + tagId + '\'' +
                ", tagName='" + tagName + '\'' +
                ", associatedWith='" + associatedWith + '\'' +
                ", isActive=" + isActive +
                ", status='" + status + '\'' +
                ", tagOptions=" + tagOptions +
                '}';
    }
}
