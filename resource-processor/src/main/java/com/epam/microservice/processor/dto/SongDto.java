package com.epam.microservice.processor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
public class SongDto {
    private String name;
    private String artist;
    private String album;
    private int length;
    private int year;
    private Long resourceId;
}
