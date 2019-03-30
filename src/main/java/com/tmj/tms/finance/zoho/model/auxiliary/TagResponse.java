package com.tmj.tms.finance.zoho.model.auxiliary;

import com.tmj.tms.finance.zoho.model.Item;
import com.tmj.tms.finance.zoho.model.Tag;

public class TagResponse {
    private Integer code;
    private String message;
    private Tag reporting_tag;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Tag getReporting_tag() {
        return reporting_tag;
    }

    public void setReporting_tag(Tag reporting_tag) {
        this.reporting_tag = reporting_tag;
    }

    @Override
    public String toString() {
        return "TagResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", reporting_tag=" + reporting_tag +
                '}';
    }
}
