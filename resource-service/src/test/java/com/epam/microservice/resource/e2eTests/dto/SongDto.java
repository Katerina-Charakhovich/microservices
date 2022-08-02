package com.epam.microservice.resource.e2eTests.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonSerialize
public class SongDto {
    private Long id;
    private String name;
    private String artist;
    private String album;
    private int length;
    private int year;
    private Long resourceId;

    public SongDto(String name, String artist, String album, int length, int year, Long resourceId) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.length = length;
        this.year = year;
        this.resourceId = resourceId;
    }
}
