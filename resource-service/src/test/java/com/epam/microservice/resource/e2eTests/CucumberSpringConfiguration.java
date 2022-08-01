package com.epam.microservice.resource.e2eTests;

import com.epam.microservice.resource.ResourceServiceApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = ResourceServiceApplication.class)
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = {"com.epam.microservice.resource"})
public class CucumberSpringConfiguration {
}