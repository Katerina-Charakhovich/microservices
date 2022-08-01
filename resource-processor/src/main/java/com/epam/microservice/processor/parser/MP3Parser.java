package com.epam.microservice.processor.parser;

import com.epam.microservice.processor.dto.SongDto;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class MP3Parser {
    static final String TITLE = "title";
    static final String ARTIST = "xmpDM:artist";
    static final String ALBUM = "xmpDM:album";
    static final String DURATION = "xmpDM:duration";
    static final String YEAR = "xmpDM:releaseDate";
    static final String UNKNOWN = "unknown";

    public SongDto parse(byte[] file) {
        InputStream input = new ByteArrayInputStream(file);
        Metadata metadata = getMetadata(input);
        return getSongDtoByMetadata(metadata);
    }

    Metadata getMetadata(InputStream input) {
        try {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new org.apache.tika.parser.mp3.Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();
            return metadata;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    SongDto getSongDtoByMetadata(Metadata metadata) {
        return
                SongDto.builder()
                        .name(getProperty(metadata, TITLE))
                        .artist(getProperty(metadata, ARTIST))
                        .album(getProperty(metadata, ALBUM))
                        .length(10)
                        .year(10)
//                        .length(Integer.parseInt(getProperty(metadata, DURATION)))
//                        .year(Integer.parseInt(getProperty(metadata, YEAR)))
                        .build();
    }


     String getProperty(Metadata metadata, String property) {
        String propertyValue = metadata!=null? metadata.get(property):UNKNOWN;
        switch (property) {
            case DURATION:
            case YEAR:
                return propertyValue != null && !propertyValue.equals(UNKNOWN)  ? !propertyValue.isBlank() ?  "-1" : "-1" : "-1";
            default:
                return propertyValue != null && !propertyValue.equals(UNKNOWN) ? !propertyValue.isBlank() ? propertyValue : UNKNOWN : UNKNOWN;
        }
    }
}

