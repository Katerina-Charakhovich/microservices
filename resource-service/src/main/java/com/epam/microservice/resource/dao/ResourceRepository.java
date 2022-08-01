package com.epam.microservice.resource.dao;

import com.epam.microservice.resource.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {
        List<ResourceEntity> findByFileName(String name);
        Optional<ResourceEntity> findById(Long id);
}