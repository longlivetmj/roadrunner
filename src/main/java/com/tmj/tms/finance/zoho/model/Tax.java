
package com.tmj.tms.finance.zoho.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tax_name",
    "tax_amount"
})
public class Tax {

    @JsonProperty("tax_name")
    private String taxName;
    @JsonProperty("tax_amount")
    private Double taxAmount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("tax_name")
    public String getTaxName() {
        return taxName;
    }

    @JsonProperty("tax_name")
    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    @JsonProperty("tax_amount")
    public Double getTaxAmount() {
        return taxAmount;
    }

    @JsonProperty("tax_amount")
    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
