package com.epam.microservice.song.dao;

import com.epam.microservice.song.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {
        List<SongEntity> findByName(String name);
        Optional<SongEntity> findById(Long id);
}