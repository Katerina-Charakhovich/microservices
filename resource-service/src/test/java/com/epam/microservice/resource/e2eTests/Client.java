package com.epam.microservice.resource.e2eTests;

import com.epam.microservice.resource.e2eTests.dto.SongDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class Client {
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    ObjectMapper objectMapper;
    Gson gson = new Gson();
    private String RESOURCE_ENDPOINT = "http://localhost:8002/resources";
    private final String SONG_ENDPOINT= "http://localhost:8001/songs";

    public HttpEntity<Map> uploadFile(File audioFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(audioFile));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        HttpEntity<Map> result = restTemplate.postForEntity(RESOURCE_ENDPOINT, requestEntity, Map.class);
        return restTemplate.postForEntity(RESOURCE_ENDPOINT, requestEntity, Map.class);
    }

    public ResponseEntity<ByteArrayResource>  geResourceById(long id) {
        ResponseEntity<ByteArrayResource> downloadFile  = restTemplate.getForEntity(RESOURCE_ENDPOINT + "/" + id,  ByteArrayResource.class);
        return downloadFile;
    }

    public ResponseEntity<SongDto> getMetadataSongByResourceId(long id) {
        return restTemplate.getForEntity(SONG_ENDPOINT + "/" + id, SongDto.class);
    }
}

