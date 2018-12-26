package com.tmj.tms.fleet.integration;

import com.tmj.tms.fleet.integration.auxiliary.GeoIdAccessToken;
import com.tmj.tms.fleet.integration.auxiliary.GeoIdAuthenticationResponse;
import org.springframework.web.client.RestTemplate;

public class GeoIdAuthenticationToken {

    public static void main(String[] args) {
        GeoIdAuthenticationToken geoIdAuthenticationToken = new GeoIdAuthenticationToken();
        GeoIdAccessToken geoIdAccessToken = new GeoIdAccessToken();
        geoIdAccessToken.setClientId("Road_Runner");
        geoIdAccessToken.setAccessType("0x100");
        geoIdAccessToken.setActivationTime(0);
        geoIdAccessToken.setDuration(2592000);
        geoIdAccessToken.setLang("en");
        geoIdAccessToken.setFlags("0");
        geoIdAccessToken.setUser("amila85perera@gmail.com");
        geoIdAccessToken.setRedirectUri("login.html");
        geoIdAccessToken.setResponseType("token");
        geoIdAccessToken.setCssUrl("");
        System.out.println(geoIdAuthenticationToken.getAuthenticationToken(geoIdAccessToken));
    }

    public GeoIdAuthenticationResponse getAuthenticationToken(GeoIdAccessToken accessToken) {
        //http://{host}/login.html?client_id=...&access_type=...&activation_time=...&duration=...&lang=...&flags=...&user=...&redirect_uri=...&response_type=...&css_url=...
        GeoIdAuthenticationResponse authenticationResponse = null;
        String resourceUrl = "http://smartgps.geoidit.com/login.html?" +
                "client_id=" + accessToken.getClientId() +
                "&access_type=" + accessToken.getAccessType() +
                "&activation_time=" + accessToken.getActivationTime() +
                "&duration=" + accessToken.getDuration() +
                "&lang=" + accessToken.getLang() +
                "&flags=" + accessToken.getFlags() +
                "&user=" + accessToken.getUser() +
                "&redirect_uri=" + accessToken.getRedirectUri() +
                "&response_type=" + accessToken.getResponseType() +
                "&css_url=" + accessToken.getCssUrl();
        System.out.println(resourceUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            authenticationResponse = restTemplate.getForObject(resourceUrl, GeoIdAuthenticationResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authenticationResponse;
    }
}
