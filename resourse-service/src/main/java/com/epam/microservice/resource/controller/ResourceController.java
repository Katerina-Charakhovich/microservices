package com.epam.microservice.resource.controller;

import com.epam.microservice.resource.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/resources", produces = APPLICATION_JSON_VALUE)
public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public ResponseEntity<?> uploadAudio(@RequestParam("file")  @NotNull MultipartFile file) {
        return resourceService.upload(file);
         //return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MULTIPART_FORM_DATA)
    public byte[] download(@PathVariable Long id) {
        return resourceService.getFile(id);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam("ids") @NotNull List<Long> ids) {
        return resourceService.delete(ids);
    }

}
