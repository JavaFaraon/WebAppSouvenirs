package com.example.webappsouvenirs.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    //@JoinColumn(nullable = false)
    private Country country;

    @OneToMany(mappedBy = "manufacturer")
    private Collection<Souvenir> souvenirs;


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

    public Collection<Souvenir> getSouvenirs() {
        return souvenirs;
    }

    public void setSouvenirs(Collection<Souvenir> souvenirs) {
        this.souvenirs = souvenirs;
    }


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}