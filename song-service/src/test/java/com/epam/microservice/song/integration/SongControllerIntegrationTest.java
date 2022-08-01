package com.epam.microservice.song.integration;

import com.epam.microservice.song.controller.SongController;
import com.epam.microservice.song.dto.SongDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SongControllerIntegrationTest {

    private static final String SONG_GET_BY_ID = "/songs/{id}";
    private static final String SONG_POST = "/songs";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @MockBean
    private SongController songController;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void addSong() throws Exception {
        SongDto songDto = new SongDto("test_name", "test_artist", "test_album", 10, 10, 1L);
        SongDto songDtoFromBase = new SongDto(1L, "test_name", "test_artist", "test_album", 10, 10, 1L);
        ResponseEntity responseEntity = new ResponseEntity<>(songDtoFromBase, HttpStatus.CREATED);
        when(songController.addSong(songDto)).thenReturn(responseEntity);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(SONG_POST)
                        .content(objectMapper.writeValueAsString(songDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void getSongById() throws Exception {
        SongDto songDto = new SongDto(1L, "test_name", "test_artist", "test_album", 10, 10, 1L);
        ResponseEntity responseEntity = new ResponseEntity<>(songDto, HttpStatus.OK);
        when(songController.getSong(songDto.getId())).thenReturn(responseEntity);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(SONG_GET_BY_ID, 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void getSongById_NotFound() throws Exception {
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(songController.getSong(1L)).thenReturn(responseEntity);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(SONG_GET_BY_ID, 1).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() {
    }
}