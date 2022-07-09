package com.epam.microservice.resource.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.microservice.resource.dao.ResourceRepository;
import com.epam.microservice.resource.entity.ResourceEntity;
import com.epam.microservice.resource.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private ResourceMapper resourceMapper;

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
        return new ResponseEntity<>(HttpStatus.CREATED);
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
    public byte[] getFile(Long id) {
        Optional<ResourceEntity> file = resourceRepository.findById(id);
        if (file.isPresent()) {
            try {
                S3Object s3File = s3Client.getObject(BUCKET, file.get().getFileName());
                byte[] fileContent = s3File.getObjectContent().readAllBytes();

                return fileContent;
            } catch (Exception e) {
                return null;
            }
        }
        else return null;
    }
}
