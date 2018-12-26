package com.tmj.tms.finance.zoho.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.zoho.model.Employee;
import com.tmj.tms.finance.zoho.model.auxiliary.EmployeeList;
import com.tmj.tms.finance.zoho.model.auxiliary.EmployeeResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeApi extends API {

    private static String url = baseURL + "/employees";

    public EmployeeList getEmployees(ZohoIntegration zohoIntegration) throws Exception {
        String resourceUrl = url + "?organization_id=" + zohoIntegration.getOrganizationId();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), EmployeeList.class);
    }

    public Employee create(Employee employee, ZohoIntegration zohoIntegration) throws Exception {
        Employee createdEmployee;
        String resourceUrl = url + "?organization_id=" + zohoIntegration.getOrganizationId();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String payload = objectMapper.writeValueAsString(employee);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        System.out.println(payload);
        body.add("JSONString", payload);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

        EmployeeResponse employeeResponse = restTemplate.postForObject(resourceUrl, httpEntity, EmployeeResponse.class);
        createdEmployee = employeeResponse.getEmployee();
        return createdEmployee;
    }

    public void update(Employee employee, ZohoIntegration zohoIntegration, Long financeIntegrationKey) throws Exception {
        String resourceUrl = url + "/" + financeIntegrationKey + "?organization_id=" + zohoIntegration.getOrganizationId();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String payload = objectMapper.writeValueAsString(employee);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("JSONString", payload);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

        restTemplate.postForObject(resourceUrl, httpEntity, EmployeeResponse.class);

    }
}

