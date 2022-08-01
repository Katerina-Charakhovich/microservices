package com.epam.microservice.processor.clients;


import com.epam.microservice.processor.dto.SongDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
        ids = "com.epam.microservice:song-service:0.0.1-SNAPSHOT:stubs:8081",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class SongServiceFClientTest {
    @Autowired
    private SongServiceFClient songClient;

    @Test
    public void addFile() {
        SongDto songDto = new SongDto("test_name", "test_artist", "test_album", 10, 10, 1L);
        SongDto songDtoFromBase = new SongDto(1L, "test_name", "test_artist", "test_album", 10, 10, 1L);

        ResponseEntity<Map<String, Long>> response = songClient.addSong(songDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(java.util.Optional.of(1L), response.getBody().get("id"));
    }
}
