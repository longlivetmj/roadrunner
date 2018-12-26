package com.tmj.tms.finance.zoho.model.auxiliary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tmj.tms.finance.zoho.model.Contact;
import com.tmj.tms.finance.zoho.model.Employee;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeList {

    private String code;
    private String message;
    private List<Employee> employees;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
