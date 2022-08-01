package com.epam.microservice.resource.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.microservice.resource.dao.ResourceRepository;
import com.epam.microservice.resource.entity.ResourceEntity;
import com.epam.microservice.resource.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RabbitMQSendService rabbitMQSendService;

    //@Value("{$aws.bucket}")
    private String BUCKET = "awstask8";

    @Transactional
    public ResponseEntity<?> upload(MultipartFile file) {
        try {
            s3Client.putObject(BUCKET, file.getOriginalFilename(), file.getInputStream(), generateMetaDate(file));
        } catch (Exception e) {
            System.out.println("Upload error: " + e.getMessage());
        }
        ResourceEntity entityModel = new ResourceEntity();
        entityModel.setFileName(file.getOriginalFilename());
        resourceRepository.save(entityModel);
        long id = entityModel.getId();
        rabbitMQSendService.sendMessage(id);
        return new ResponseEntity<>(Map.of("id", id),HttpStatus.CREATED);

    }

    private ObjectMetadata generateMetaDate(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setLastModified(Date.from(Instant.now()));
        metadata.setContentType(file.getContentType());
        return metadata;
    }

    @Transactional
    public ResponseEntity<?> delete(List<Long> ids) {
        List<ResourceEntity> files = new ArrayList<>();
        for (long i:ids
             ) {
            Optional<ResourceEntity> file = resourceRepository.findById(i);
            file.ifPresent(files::add);
        }
        files.stream()
                .map(ResourceEntity::getFileName)
                .forEach(s -> s3Client.deleteObject(BUCKET, s));
        resourceRepository.deleteAll(files);
        return new ResponseEntity<>(files.stream().map(ResourceEntity::getId),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ByteArrayResource> getFile(Long id) {
        Optional<ResourceEntity> file = resourceRepository.findById(id);
        if (file.isPresent()) {
            try {
                S3Object s3File = s3Client.getObject(BUCKET, file.get().getFileName());
                ByteArrayResource downloadFile = new ByteArrayResource(s3File.getObjectContent().readAllBytes());
                return ResponseEntity.ok()
                        // Content-Disposition
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + id)
                        // Content-Type
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(downloadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else return null;
        return null;
    }
}
