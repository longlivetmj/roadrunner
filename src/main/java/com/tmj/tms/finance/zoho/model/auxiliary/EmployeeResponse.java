package com.tmj.tms.finance.zoho.model.auxiliary;

import com.tmj.tms.finance.zoho.model.Employee;

public class EmployeeResponse {
    private Integer code;
    private String message;
    private Employee employee;

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
