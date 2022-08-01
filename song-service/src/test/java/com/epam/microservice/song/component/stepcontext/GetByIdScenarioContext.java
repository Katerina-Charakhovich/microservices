package com.epam.microservice.song.component.stepcontext;

import com.epam.microservice.song.dto.SongDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GetByIdScenarioContext {
  SongDto existedSong;
  ResponseEntity<SongDto> response;

  public void setExistedSong(SongDto existedSong) {
    this.existedSong = existedSong;
  }

  public SongDto getExistedSong() {
    return existedSong;
  }

  public void setResponse(ResponseEntity<SongDto> response) {
    this.response = response;
  }

  public ResponseEntity<SongDto> getResponse() {
    return response;
  }
}
