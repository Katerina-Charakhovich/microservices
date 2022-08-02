package com.epam.microservice.processor.parser;


import org.apache.tika.metadata.Metadata;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MP3ParserUnitTest {

    private   MP3Parser mp3Parser;
    @BeforeEach
    void setUp() {
        mp3Parser = new MP3Parser();
    }
    @Test
    void getProperty() {
        Metadata metadata = new Metadata();
        final String nameAlbum = "Test Album";
        metadata.set(MP3Parser.ALBUM, nameAlbum);
        Assert.assertEquals(mp3Parser.getProperty(metadata,MP3Parser.ALBUM), nameAlbum);
    }
}