package com.epam.microservice.song.contract;

import com.epam.microservice.song.SongServiceApplication;
import com.epam.microservice.song.controller.SongController;
import com.epam.microservice.song.dto.SongDto;
import com.epam.microservice.song.service.SongService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = SongServiceApplication.class)
public class ContractTest {

    @Autowired
    SongController songController;

    @MockBean
    SongService songService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(songController);
        SongDto songDto = new SongDto("test_name", "test_artist", "test_album", 10, 10, 1L);
        SongDto songDtoFromBase = new SongDto(1L, "test_name", "test_artist", "test_album", 10, 10, 1L);
        ResponseEntity<SongDto> responseEntity = new ResponseEntity<>(songDtoFromBase, HttpStatus.CREATED);
        Mockito.when(songService.add(songDto)).thenReturn(responseEntity);
    }
}
