package com.epam.microservice.processor.parser;

import com.epam.microservice.processor.dto.SongDto;
import lombok.SneakyThrows;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MP3ParserTest {

    @SneakyThrows
    @Test
    @Ignore
    void parse() {
        MP3Parser mp3Parser= new MP3Parser();
        String fileLocation = "c:\\1\\cassette-tell-me-why.mp3";
        InputStream input = new FileInputStream(fileLocation);
        input.readAllBytes();
        SongDto songDto = mp3Parser.parse(input.readAllBytes());
        int i=0;
    }
}