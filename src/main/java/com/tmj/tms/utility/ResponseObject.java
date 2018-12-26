package com.tmj.tms.utility;

public class ResponseObject {

    private String message;

    private boolean status = false;

    private Object object;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ResponseObject(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public ResponseObject(String message, boolean status, Object object) {
        this.message = message;
        this.status = status;
        this.object = object;
    }

    public ResponseObject(boolean status, Object object) {
        this.status = status;
        this.object = object;
    }

    public ResponseObject() {

    }


}
