package com.tmj.tms.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ConvertToJSON {


    public ResponseEntity<String> createJsonResponse(Object o) {
        HttpHeaders headers = new HttpHeaders();
        Gson gson = new Gson();
        headers.set("Content-Type", "application/json");
        String json = gson.toJson(o);
        return new ResponseEntity<String>(json, headers, HttpStatus.CREATED);
    }

    public String convertToBasicJson(Object o) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(o);
    }
}
