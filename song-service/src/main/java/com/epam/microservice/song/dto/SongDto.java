package com.epam.microservice.song.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SongDto {
    private Long id;
    private String name;
    private String artist;
    private String album;
    private int length;
    private int year;
    private Long resourceId;
}
