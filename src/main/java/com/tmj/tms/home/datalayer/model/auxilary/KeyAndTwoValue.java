package com.tmj.tms.home.datalayer.model.auxilary;

public class KeyAndTwoValue {

    private String key;
    private Double firstValue;
    private Double secondValue;

    public KeyAndTwoValue(String key, Double firstValue, Double secondValue) {
        this.key = key;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(Double firstValue) {
        this.firstValue = firstValue;
    }

    public Double getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Double secondValue) {
        this.secondValue = secondValue;
    }
}
