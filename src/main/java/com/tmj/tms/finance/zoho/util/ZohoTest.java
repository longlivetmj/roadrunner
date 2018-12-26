package com.tmj.tms.finance.zoho.util;

import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.zoho.api.ContactsApi;
import com.tmj.tms.finance.zoho.model.ContactList;

public class ZohoTest {

    public static void main(String[] args) {
        try {
            ZohoIntegration zohoIntegration = new ZohoIntegration();
            zohoIntegration.setAccessToken("1000.1e36fa1093a4de6a8b0c1a7b4cd828d9.20bc78f35b0793edc7b140b615c349fa");
            zohoIntegration.setOrganizationId("665510119");
            ContactsApi contactsApi = new ContactsApi();
            ContactList contactList = contactsApi.getContacts(zohoIntegration);
            System.out.println(contactList.getContacts().get(0).getCompany_name());
            System.out.println(contactList.getContacts().get(0).getContact_id());
           /* ZohoAccessTokenGenerator zohoAccessTokenGenerator = new ZohoAccessTokenGenerator();
            *//**//*AccessToken accessToken = new AccessToken();
            accessToken.setClientId("1000.RX374G2ML1WE823958SAM9PDJPCEZI");
            accessToken.setCode("1000.d88d99f2baa9b99d893cdb5e9ea70d7b.253132100cb5d31436162fecfbcd8e45");
            accessToken.setClientSecret("be0102eff1fc3af3bb4c7fba2b5e44bdacba8446d9");
            accessToken.setRedirectUri("http://www.zoho.com/invoice");
            accessToken.setGrantType("authorization_code");
            AuthenticationResponse authenticationResponse = zohoAccessTokenGenerator.getAuthenticationToken(accessToken);
            System.out.println(authenticationResponse);*//*

            AccessToken accessToken = new AccessToken();
            accessToken.setClientId("1000.RX374G2ML1WE823958SAM9PDJPCEZI");
            accessToken.setCode("1000.14c66da937e54d59ea19082e49a28ca0.3de16283842f02c41f2d6dd0f93e2286");
            accessToken.setClientSecret("be0102eff1fc3af3bb4c7fba2b5e44bdacba8446d9");
            accessToken.setRedirectUri("http://www.zoho.com/invoice");
            accessToken.setGrantType("refresh_token");
            AuthenticationResponse refresh = zohoAccessTokenGenerator.refreshAuthenticationToken(accessToken);
            System.out.println(refresh);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
