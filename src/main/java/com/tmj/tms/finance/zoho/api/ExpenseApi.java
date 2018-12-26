package com.tmj.tms.finance.zoho.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.zoho.model.Expense;
import com.tmj.tms.finance.zoho.model.auxiliary.ExpenseList;
import com.tmj.tms.finance.zoho.model.auxiliary.ExpenseResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ExpenseApi extends API {

    private static String url = baseURL + "/expenses";

    public ExpenseList getExpenses(ZohoIntegration zohoIntegration) throws Exception {
        String resourceUrl = url + "?organization_id=" + zohoIntegration.getOrganizationId();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), ExpenseList.class);
    }

    public Expense create(Expense expense, ZohoIntegration zohoIntegration) throws Exception {
        Expense createdExpense = null;
        try {
            String resourceUrl = url + "?organization_id=" + zohoIntegration.getOrganizationId();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String payload = objectMapper.writeValueAsString(expense);
            System.out.println(payload);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("JSONString", payload);
            HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

            ExpenseResponse expenseResponse = restTemplate.postForObject(resourceUrl, httpEntity, ExpenseResponse.class);
            createdExpense = expenseResponse.getExpense();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createdExpense;
    }

    public void update(Expense expense, ZohoIntegration zohoIntegration, Long financeIntegrationKey) throws Exception {
        String resourceUrl = url + "/" + financeIntegrationKey + "?organization_id=" + zohoIntegration.getOrganizationId();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String payload = objectMapper.writeValueAsString(expense);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("JSONString", payload);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

        ExpenseResponse expenseResponse = restTemplate.postForObject(resourceUrl, httpEntity, ExpenseResponse.class);
    }
}

