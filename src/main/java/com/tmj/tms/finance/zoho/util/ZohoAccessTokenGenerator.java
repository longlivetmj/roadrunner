package com.tmj.tms.finance.zoho.util;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.datalayer.service.ZohoIntegrationService;
import com.tmj.tms.finance.zoho.model.AuthenticationResponse;
import com.tmj.tms.utility.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class ZohoAccessTokenGenerator {

    private final ZohoIntegrationService zohoIntegrationService;

    @Autowired
    public ZohoAccessTokenGenerator(ZohoIntegrationService zohoIntegrationService) {
        this.zohoIntegrationService = zohoIntegrationService;
    }

    public AuthenticationResponse getAuthenticationToken(ZohoIntegration accessToken) {
        AuthenticationResponse authenticationResponse = null;
        String resourceUrl = "https://accounts.zoho.com/oauth/v2/token?" +
                "code=" + accessToken.getCode() +
                "&client_id=" + accessToken.getClientId() +
                "&client_secret=" + accessToken.getClientSecret() +
                "&redirect_uri=" + accessToken.getRedirectUri() +
                "&grant_type=" + accessToken.getGrantType();
        try {
            RestTemplate restTemplate = new RestTemplate();
            authenticationResponse = restTemplate.postForObject(resourceUrl, null, AuthenticationResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authenticationResponse;
    }

    public AuthenticationResponse refreshAuthenticationToken(ZohoIntegration accessToken) {
        AuthenticationResponse authenticationResponse = null;
        String resourceUrl = "https://accounts.zoho.com/oauth/v2/token?" +
                "refresh_token=" + accessToken.getRefreshToken() +
                "&client_id=" + accessToken.getClientId() +
                "&client_secret=" + accessToken.getClientSecret() +
                "&redirect_uri=" + accessToken.getRedirectUri() +
                "&grant_type=refresh_token";
        try {
            RestTemplate restTemplate = new RestTemplate();
            authenticationResponse = restTemplate.postForObject(resourceUrl, null, AuthenticationResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authenticationResponse;
    }

    public ZohoIntegration tokenUpdater(Integer companyProfileSeq) {
        ZohoIntegration zohoIntegration = this.zohoIntegrationService.findByCompanyProfileSeqAndStatus(companyProfileSeq, MasterDataStatus.APPROVED.getStatusSeq());
        if (zohoIntegration != null && zohoIntegration.getExpiresOn().before(new Date())) {
            DateFormatter dateFormatter = new DateFormatter();
            ZohoAccessTokenGenerator zohoAccessTokenGenerator = new ZohoAccessTokenGenerator(zohoIntegrationService);
            AuthenticationResponse authenticationResponse = zohoAccessTokenGenerator.refreshAuthenticationToken(zohoIntegration);
            zohoIntegration.setAccessToken(authenticationResponse.getAccess_token());
            zohoIntegration.setExpiresOn(dateFormatter.addMinutesToDate(new Date(), authenticationResponse.getExpires_in_sec() / 60));
            zohoIntegration = this.zohoIntegrationService.save(zohoIntegration);
        }
        return zohoIntegration;
    }
}
