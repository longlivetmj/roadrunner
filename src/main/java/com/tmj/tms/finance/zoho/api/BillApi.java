package com.tmj.tms.finance.zoho.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.zoho.model.Bill;
import com.tmj.tms.finance.zoho.model.auxiliary.BillList;
import com.tmj.tms.finance.zoho.model.auxiliary.BillResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class BillApi extends API {

    private static String url = baseURL + "/bills";


    public BillList getBills(ZohoIntegration zohoIntegration) throws Exception {
        String resourceUrl = url + "?organization_id=" + zohoIntegration.getOrganizationId();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), BillList.class);
    }

    public Bill create(Bill bill, ZohoIntegration zohoIntegration) throws Exception {
        Bill createdBill = null;
        try {
            String resourceUrl = url + "?organization_id=" + zohoIntegration.getOrganizationId();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String payload = objectMapper.writeValueAsString(bill);
            System.out.println(payload);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("JSONString", payload);
            HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

            BillResponse billResponse = restTemplate.postForObject(resourceUrl, httpEntity, BillResponse.class);
            createdBill = billResponse.getBill();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createdBill;
    }

    public void update(Bill bill, ZohoIntegration zohoIntegration, Long financeIntegrationKey) throws Exception {
        String resourceUrl = url + "/" + financeIntegrationKey + "?organization_id=" + zohoIntegration.getOrganizationId();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String payload = objectMapper.writeValueAsString(bill);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("JSONString", payload);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

        BillResponse billResponse = restTemplate.postForObject(resourceUrl, httpEntity, BillResponse.class);
    }
}

