package com.epam.microservice.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProcessorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessorServiceApplication.class, args);
    }
}
