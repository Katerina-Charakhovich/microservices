package com.epam.microservice.resource.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class S3Service {
    private AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    @Value("${3.bucket.fileSize}")
    private Long maxFileSize;

    @Autowired
    public S3Service(AmazonS3 s3client) {
        this.s3Client = s3client;
    }

    public boolean putFile(MultipartFile multipartFile) {
        boolean isUpload = false;
        String fileName = multipartFile.getOriginalFilename();
        if ((!isExistFile(fileName))) {
            try {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(multipartFile.getSize());

                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                        multipartFile.getInputStream(), objectMetadata);
                s3Client.putObject(putObjectRequest);
                isUpload = true;

            } catch (AmazonServiceException | IOException e) {
                new Exception("File " + fileName + " is not uploaded to S3");
            }
        }
        return isUpload;
    }

    public boolean isExistFile(String fileName) {
        return s3Client.doesObjectExist(bucketName, fileName);
    }
}
