package com.epam.microservice.song.controller;

import com.epam.microservice.song.dto.SongDto;
import com.epam.microservice.song.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/songs", produces = APPLICATION_JSON_VALUE)
public class SongController {
    @Autowired
    private SongService songService;

    @PostMapping()
    public ResponseEntity<?> addSong(@RequestBody SongDto songDto) {
        return songService.add(songDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSong(@PathVariable Long id) {
        return songService.getSong(id);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam("ids") @NotNull List<Long> ids) {
        return songService.delete(ids);
    }

}
