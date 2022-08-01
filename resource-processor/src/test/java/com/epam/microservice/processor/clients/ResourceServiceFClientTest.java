package com.epam.microservice.processor.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        ids = "com.epam.microservice:resource-service:0.0.1-SNAPSHOT:stubs:8086",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ResourceServiceFClientTest {

    @Autowired
    private ResourceServiceFClient resourceClient;

    @Test
    public void findResourceById() {
        ResponseEntity<ByteArrayResource> response = resourceClient.getById(1L);
        assertNotEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}