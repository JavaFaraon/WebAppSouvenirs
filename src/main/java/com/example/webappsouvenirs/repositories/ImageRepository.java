package com.example.webappsouvenirs.repositories;

import com.example.webappsouvenirs.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}