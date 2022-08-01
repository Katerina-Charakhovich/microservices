package com.epam.microservice.song.component;

import com.epam.microservice.song.dto.SongDto;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;


@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class Client {

    private final String SONG_ENDPOINT= "http://localhost:8001/songs";
    private final RestTemplate restTemplate = new RestTemplate();


    public ResponseEntity<Map> post(final SongDto songDto) {
        return restTemplate.postForEntity(SONG_ENDPOINT, songDto, Map.class);
    }

    public ResponseEntity<SongDto> getById(Object id) {
        return restTemplate.getForEntity(SONG_ENDPOINT + "/" + id, SongDto.class);
    }

    public void delete(List<Long> ids) {
        String param = ids.stream().map(Object::toString).collect(Collectors.joining(","));
        UriComponents builder = UriComponentsBuilder
                .fromUriString(SONG_ENDPOINT)
                .queryParam("ids", param)
                .build();
        restTemplate.delete(builder.toUriString(), Map.of("ids", param));
    }
}

