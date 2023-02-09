package com.example.webappsouvenirs.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "manufacturers")
public class Manufacturers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}