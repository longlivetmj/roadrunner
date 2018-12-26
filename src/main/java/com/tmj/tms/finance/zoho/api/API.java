package com.tmj.tms.finance.zoho.api;

import org.springframework.http.HttpHeaders;

public class API {

    static String baseURL = "https://books.zoho.com/api/v3";

    HttpHeaders getHttpAuthenticationHeaders(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Zoho-oauthtoken " + authToken);
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }

}