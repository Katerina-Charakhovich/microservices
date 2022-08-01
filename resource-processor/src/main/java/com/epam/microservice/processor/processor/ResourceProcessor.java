package com.epam.microservice.processor.processor;

import com.epam.microservice.processor.clients.ResourceServiceFClient;
import com.epam.microservice.processor.clients.SongServiceFClient;
import com.epam.microservice.processor.dto.SongDto;
import com.epam.microservice.processor.parser.MP3Parser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@EnableFeignClients
public class ResourceProcessor implements DefaultProcessor{

    private MP3Parser mp3Parser;
    private SongServiceFClient songServiceClient;
    private ResourceServiceFClient resourceServiceClient;
    private static final Logger LOGGER = LogManager.getLogger(ResourceProcessor.class);

    @Autowired
    public ResourceProcessor(MP3Parser mp3Parser, ResourceServiceFClient resourceServiceClient, SongServiceFClient songServiceClient) {
        this.mp3Parser = mp3Parser;
        this.resourceServiceClient = resourceServiceClient;
        this.songServiceClient = songServiceClient;
    }

    public void process(Long resourceId) {
        LOGGER.log(Level.INFO,"ResourceProcessor.process for id:"+resourceId);
        ResponseEntity<ByteArrayResource> file  = resourceServiceClient.getById(resourceId);
        LOGGER.log(Level.INFO,"Get result:"+resourceId);
        SongDto songDto =mp3Parser.parse(file.getBody().getByteArray());
        songDto.setResourceId(resourceId);
        songServiceClient.addSong(songDto);
    }

    @Override
    public void process(Object eventData) {

    }
}
