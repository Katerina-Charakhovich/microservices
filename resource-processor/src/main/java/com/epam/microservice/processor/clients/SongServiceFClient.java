package com.epam.microservice.processor.clients;

import com.epam.microservice.processor.dto.SongDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "songService", url = "${song.service.url}")
public interface SongServiceFClient {
    @PostMapping(value = "/songs")
    ResponseEntity<Map<String, Long>> addSong(@RequestBody SongDto songDto);
}
