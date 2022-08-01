package com.epam.microservice.resource.e2eTests.steps;

import com.epam.microservice.resource.e2eTests.Client;
import com.epam.microservice.resource.e2eTests.dto.SongDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class StepDefinitions {

  @Autowired  private Client client;

  private File audioFile;
  private HttpEntity<Map> response;

  @Given("new file")
  public void generateNewAudioFile() {
    audioFile = new File("src/test/resources/cassette-tell-me-why.mp3");
  }

  @When("user uploads file")
  public void uploadFile() throws IOException {
    response = client.uploadFile(audioFile);
  }

  @Then("returned resource id")
  public void returnResourceId() {
    assertNotNull(response.getBody().get("id"));
  }

  @Then("user can get file by resource id")
  public void getFileById() {
    Long id = Long.parseLong(String.valueOf(response.getBody().get("id")));
    ResponseEntity<ByteArrayResource> file = client.geResourceById(id);
    assertNotNull(file);
  }

  @Then("user can get file's metadata by resource id")
  public void getMetadataByResourceId() {
    Long id = Long.parseLong(String.valueOf(response.getBody().get("id")));
    ResponseEntity<SongDto> metadata = client.getMetadataSongByResourceId(id);
    assertNotNull(metadata.getBody());
  }
}
