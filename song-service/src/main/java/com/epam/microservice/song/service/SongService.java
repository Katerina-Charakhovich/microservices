package com.epam.microservice.song.service;

import com.epam.microservice.song.dao.SongRepository;
import com.epam.microservice.song.dto.SongDto;
import com.epam.microservice.song.entity.SongEntity;
import com.epam.microservice.song.mapper.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongMapper songMapper;

    @Transactional
    public ResponseEntity<?> add(SongDto song) {
        SongEntity entityModel = songMapper.toSongEntity(song);
        songRepository.save(entityModel);
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> delete(List<Long> ids) {
        List<SongEntity> songEntities = new ArrayList<>();
        for (long i : ids
        ) {
            Optional<SongEntity> songEntity = songRepository.findById(i);
            songEntity.ifPresent(songEntities::add);
        }
        songRepository.deleteAll(songEntities);
        return new ResponseEntity<>(songEntities.stream().map(SongEntity::getId), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getSong(Long id) {
        Optional<SongEntity> songEntity = songRepository.findById(id);
        if (songEntity.isPresent()) {
            return new ResponseEntity<>(songEntity.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private List<Long> getIds(String stringIds) {
        return Arrays.stream(stringIds.split(",")).map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
