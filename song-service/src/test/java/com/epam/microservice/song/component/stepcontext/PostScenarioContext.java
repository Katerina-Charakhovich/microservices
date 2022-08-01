package com.epam.microservice.song.component.stepcontext;

import com.epam.microservice.song.dto.SongDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PostScenarioContext {
  private SongDto request;
  private ResponseEntity<Map> response;

  public SongDto getRequest() {
    return request;
  }

  public void setRequest(SongDto request) {
    this.request = request;
  }

  public ResponseEntity<Map> getResponse() {
    return response;
  }

  public void setResponse(ResponseEntity<Map> response) {
    this.response = response;
  }
}
