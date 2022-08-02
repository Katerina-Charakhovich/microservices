package com.epam.microservice.song.component.steps;

import com.epam.microservice.song.component.Client;
import com.epam.microservice.song.component.stepcontext.DeleteScenarioContext;
import com.epam.microservice.song.component.stepcontext.GetByIdScenarioContext;
import com.epam.microservice.song.component.stepcontext.PostScenarioContext;
import com.epam.microservice.song.dto.SongDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

public class StepDefinitions {
    @Autowired
    private Client songsHttpClient;
    @Autowired
    private PostScenarioContext postScenarioContext;
    @Autowired
    private GetByIdScenarioContext getByIdScenarioContext;
    @Autowired
    private DeleteScenarioContext deleteScenarioContext;

    @Given("new song")
    public void generateSong() {
        SongDto songDto = new SongDto("test_name", "test_artist", "test_album", 10, 10, 1L);
        postScenarioContext.setRequest(songDto);
    }

    @When("song is uploaded")
    public void uploadSong() {
        ResponseEntity<Map> response = songsHttpClient.post(postScenarioContext.getRequest());
        postScenarioContext.setResponse(response);
    }

    @Then("save song")
    public void isSongSaved() {
        Object id = postScenarioContext.getResponse().getBody().get("id");
        assertNotNull(songsHttpClient.getById(id).getBody().getId());
    }

    @Then("return song id")
    public void getSongId() {
        assertEquals(HttpStatus.CREATED, postScenarioContext.getResponse().getStatusCode());
        assertNotNull(postScenarioContext.getResponse().getBody().get("id"));
    }

    @Given("existed song")
    public void getSavedSong() {
        SongDto songDto = new SongDto("test_name", "test_artist", "test_album", 10, 10, 1L);
        ResponseEntity<Map> response = songsHttpClient.post(songDto);
        Long id = Long.valueOf((Integer) response.getBody().get("id"));
        songDto.setId(id);
        getByIdScenarioContext.setExistedSong(songDto);
    }

    @When("user requests song by id")
    public void requestSongById() {
        Long id = getByIdScenarioContext.getExistedSong().getId();
        ResponseEntity<SongDto> res = songsHttpClient.getById(id);
        getByIdScenarioContext.setResponse(songsHttpClient.getById(id));
    }

    @Then("return song")
    public void getSong() {
        assertEquals(HttpStatus.OK, getByIdScenarioContext.getResponse().getStatusCode());
        assertEquals(
                getByIdScenarioContext.getExistedSong(), getByIdScenarioContext.getResponse().getBody());
    }

    @Given("existed songs")
    public void getSavedSongs() {
        SongDto songDto = new SongDto("test_name", "test_artist", "test_album", 10, 10, 1L);
        SongDto songDto2 = new SongDto( "test_name", "test_artist", "test_album", 10, 10, 2L);
        ResponseEntity<Map> response = songsHttpClient.post(songDto);
        long id = Long.valueOf((Integer) response.getBody().get("id"));
        ResponseEntity<Map> response2 = songsHttpClient.post(songDto2);
        long id2 = Long.valueOf((Integer) response2.getBody().get("id"));
        deleteScenarioContext.setExistedSongIds(List.of(id, id2));
    }

    @When("user requests delete songs")
    public void deleteSongs() {
        List<Long> ids = deleteScenarioContext.getExistedSongIds();
        try {
            songsHttpClient.delete(ids);
            ResponseEntity<Map> response = new ResponseEntity<>(Map.of("ids", ids), HttpStatus.NO_CONTENT);
            deleteScenarioContext.setResponse(response);
        } catch (HttpClientErrorException ex) {
            String message = ex.getResponseBodyAsString();
            ResponseEntity<Map> response = new ResponseEntity<>(Map.of("message", message), HttpStatus.NO_CONTENT);
            deleteScenarioContext.setResponse(response);
        }
    }

    @Then("return deleted songs ids")
    public void getDeletedSongIds() {
        assertEquals(HttpStatus.NO_CONTENT, deleteScenarioContext.getResponse().getStatusCode());
        assertNotNull(deleteScenarioContext.getResponse().getBody().get("ids"));
    }
}
