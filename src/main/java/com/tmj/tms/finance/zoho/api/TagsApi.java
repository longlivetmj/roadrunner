package com.tmj.tms.finance.zoho.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.zoho.model.Tag;
import com.tmj.tms.finance.zoho.model.auxiliary.TagResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TagsApi extends API {

    private static String url = baseURL + "/settings/tags";

    public static void main(String[] args) {
        try {
            TagsApi tagsApi = new TagsApi();
            ZohoIntegration zohoIntegration = new ZohoIntegration();
            zohoIntegration.setAccessToken("1000.abe9ba33c044673bc532106e1e063bd1.0f479307ffa8be22f2cd9809660096c4");
            zohoIntegration.setOrganizationId("665510119");
            tagsApi.getTags(zohoIntegration, "1281355000000000333");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Tag getTags(ZohoIntegration zohoIntegration, String tagId) throws Exception {
        String resourceUrl = url + "/" + tagId + "?organization_id=" + zohoIntegration.getOrganizationId();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        TagResponse tagResponse = mapper.readValue(response.getBody(), TagResponse.class);
        System.out.println(tagResponse);
        return tagResponse.getReporting_tag();
    }

    public Tag create(Tag tag, ZohoIntegration zohoIntegration) throws Exception {
        Tag createdTag = null;
        String resourceUrl = url + "/" + tag.getTagId() + "?organization_id=" + zohoIntegration.getOrganizationId();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String payload = objectMapper.writeValueAsString(tag);
        System.out.println(payload);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpAuthenticationHeaders(zohoIntegration.getAccessToken());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("JSONString", payload);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

        TagResponse tagResponse = restTemplate.postForObject(resourceUrl, httpEntity, TagResponse.class);
        createdTag = tagResponse.getReporting_tag();
        return createdTag;
    }

}

