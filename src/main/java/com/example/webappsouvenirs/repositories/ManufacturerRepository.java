package com.example.webappsouvenirs.repositories;

import com.example.webappsouvenirs.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    @Query("select m from Manufacturer m where m.name = ?1")
    List<Manufacturer> findByName(String name);
}