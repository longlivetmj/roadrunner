package com.tmj.tms.finance.zoho.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.zoho.model.Invoice;
import com.tmj.tms.finance.zoho.model.auxiliary.InvoiceList;
import com.tmj.tms.finance.zoho.model.auxiliary.InvoiceResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class InvoiceApi extends API {

    private static String url = baseURL + "/invoices";


    public InvoiceList getInvoices(ZohoIntegration zohoIntegration) throws Exception {
        String resourceUrl = url + "?organization_id=" + zohoIntegration.getOrganizationId();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), InvoiceList.class);
    }

    public Invoice create(Invoice invoice, ZohoIntegration zohoIntegration) throws Exception {
        Invoice createdInvoice = null;
        try {
            String resourceUrl = url + "?organization_id=" + zohoIntegration.getOrganizationId();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String payload = objectMapper.writeValueAsString(invoice);
            System.out.println(payload);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("JSONString", payload);
            HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

            InvoiceResponse invoiceResponse = restTemplate.postForObject(resourceUrl, httpEntity, InvoiceResponse.class);
            createdInvoice = invoiceResponse.getInvoice();
        } catch (Exception e) {
            System.out.println("Invoice Integration Error" + invoice.getInvoiceNumber());
        }
        return createdInvoice;
    }

    public void update(Invoice invoice, ZohoIntegration zohoIntegration, Long financeIntegrationKey) throws Exception {
        String resourceUrl = url + "/" + financeIntegrationKey + "?organization_id=" + zohoIntegration.getOrganizationId();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String payload = objectMapper.writeValueAsString(invoice);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("JSONString", payload);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

        InvoiceResponse invoiceResponse = restTemplate.postForObject(resourceUrl, httpEntity, InvoiceResponse.class);
    }
}

