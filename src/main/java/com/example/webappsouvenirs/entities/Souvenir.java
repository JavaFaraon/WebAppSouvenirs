package com.example.webappsouvenirs.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "souvenirs")
public class Souvenirs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "souvenir_name", nullable = false)
    private String souvenirName;

    @Column(name = "souvenir_price")
    private Double souvenirPrice;

    @Column(name = "date_of_manufacturing")
    private LocalDateTime dateOfManufacturing;

    public LocalDateTime getDateOfManufacturing() {
        return dateOfManufacturing;
    }

    public void setDateOfManufacturing(LocalDateTime dateOfManufacturing) {
        this.dateOfManufacturing = dateOfManufacturing;
    }

    public Double getSouvenirPrice() {
        return souvenirPrice;
    }

    public void setSouvenirPrice(Double souvenirPrice) {
        this.souvenirPrice = souvenirPrice;
    }

    public String getSouvenirName() {
        return souvenirName;
    }

    public void setSouvenirName(String souvenirName) {
        this.souvenirName = souvenirName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}