package com.tmj.tms.fleet.integration.auxiliary;

public class GeoIdAuthenticationResponse {

    private String access_token;
    private String user_name;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "GeoIdAuthenticationResponse{" +
                "access_token='" + access_token + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
