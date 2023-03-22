package com.example.webappsouvenirs.entities;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "country")
    private Collection<Manufacturer> manufacturers;

    public Collection<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Collection<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }
}