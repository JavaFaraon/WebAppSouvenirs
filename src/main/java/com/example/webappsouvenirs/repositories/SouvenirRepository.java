package com.example.webappsouvenirs.repositories;

import com.example.webappsouvenirs.entities.Country;
import com.example.webappsouvenirs.entities.Manufacturer;
import com.example.webappsouvenirs.entities.Souvenir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SouvenirRepository extends JpaRepository<Souvenir, Long> {
    @Query("select s from Souvenir s where s.souvenirName = ?1")
    List<Souvenir> findBySouvenirName(String name);
    @Query("select s from Souvenir s where s.manufacturer = ?1")
    List<Souvenir> findAllByManufacturer(Manufacturer manufacturer);

    @Query("select s from Souvenir s where s.dateOfManufacturing between ?1 and ?2")
    List<Souvenir> findAllByPeriod(LocalDate start, LocalDate finish);

    @Query("select s from Souvenir s where s.manufacturer.country = :country")
    List<Souvenir> findAllByManufacturerCountry(@Param("country") Country country);
}