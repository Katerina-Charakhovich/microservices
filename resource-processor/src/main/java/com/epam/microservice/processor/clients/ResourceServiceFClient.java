package com.epam.microservice.processor.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resourceServiceClient", url = "${resource.service.url}")
public interface ResourceServiceFClient {
    @GetMapping(value = "/resources/{id}")
    byte[] getById(@PathVariable("id") Long id);
}
