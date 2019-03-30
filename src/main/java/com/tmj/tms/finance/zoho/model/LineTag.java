package com.tmj.tms.finance.zoho.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tag_id",
        "tag_option_id"
})
public class LineTag {
    @JsonProperty("tag_id")
    private String tagId;
    @JsonProperty("tag_option_id")
    private String tagOptionId;

    @JsonProperty("tag_id")
    public String getTagId() {
        return tagId;
    }

    @JsonProperty("tag_id")
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @JsonProperty("tag_option_id")
    public String getTagOptionId() {
        return tagOptionId;
    }

    @JsonProperty("tag_option_id")
    public void setTagOptionId(String tagOptionId) {
        this.tagOptionId = tagOptionId;
    }
}
