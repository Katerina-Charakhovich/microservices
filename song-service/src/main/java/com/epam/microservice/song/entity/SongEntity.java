package com.epam.microservice.song.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "table_songs")
@Data
public class  SongEntity<T> {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column(name = "name")
        private String name;
        @Column(name = "artist")
        private String artist;
        @Column(name = "album")
        private String album;
        @Column(name = "length")
        private int length;
        @Column(name = "year")
        private int year;
        @Column(name = "resourceId")
        private long resourceId;
    }

