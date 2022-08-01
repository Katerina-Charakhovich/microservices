package com.epam.microservice.resource.mapper;

import com.epam.microservice.resource.dto.ResourceDto;
import com.epam.microservice.resource.entity.ResourceEntity;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {
    public ResourceDto toResourceDto(ResourceEntity entity) {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(entity.getId());
        resourceDto.setFileName(entity.getFileName());
        return resourceDto;
    }

    public ResourceEntity toResourceEntity(ResourceDto resourceDto) {
        ResourceEntity resourceEntity = new ResourceEntity();
        if (resourceDto.getId() != null) {
            resourceEntity.setId(resourceDto.getId());
        }
        resourceEntity.setFileName(resourceDto.getFileName());
        return resourceEntity;
    }
}
