package com.epam.microservice.song.mapper;

import com.epam.microservice.song.dto.SongDto;
import com.epam.microservice.song.entity.SongEntity;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {
    public SongDto toSongDto(SongEntity entity) {
        SongDto songDto = new SongDto();
        songDto.setId(entity.getId());
        songDto.setAlbum(entity.getAlbum());
        songDto.setName(entity.getName());
        songDto.setArtist(entity.getArtist());
        songDto.setLength(entity.getLength());
        songDto.setYear(entity.getYear());
        songDto.setResourceId(entity.getResourceId());
        return songDto;
    }
    public SongEntity  toSongEntity (SongDto  songDto) {
        SongEntity songEntity = new SongEntity();
        if (songDto.getId()!=null){
            songEntity.setId(songDto.getId());
        }
        songEntity.setAlbum(songDto.getAlbum());
        songEntity.setName(songDto.getName());
        songEntity.setArtist(songDto.getArtist());
        songEntity.setLength(songDto.getLength());
        songEntity.setYear(songDto.getYear());
        songEntity.setResourceId(songDto.getResourceId());
        return songEntity;
    }
}
