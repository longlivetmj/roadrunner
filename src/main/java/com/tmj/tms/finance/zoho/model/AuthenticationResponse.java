package com.tmj.tms.finance.zoho.model;

public class AuthenticationResponse {

    private String access_token;
    private String refresh_token;
    private Integer expires_in_sec;
    private String api_domain;
    private String token_type;
    private Integer expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Integer getExpires_in_sec() {
        return expires_in_sec;
    }

    public void setExpires_in_sec(Integer expires_in_sec) {
        this.expires_in_sec = expires_in_sec;
    }

    public String getApi_domain() {
        return api_domain;
    }

    public void setApi_domain(String api_domain) {
        this.api_domain = api_domain;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in_sec=" + expires_in_sec +
                ", api_domain='" + api_domain + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                '}';
    }
}
