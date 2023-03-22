package com.example.webappsouvenirs.entities;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "souvenir")
public class Souvenir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "souvenir_name", nullable = false)
    private String souvenirName;

    @Column(name = "souvenir_price")
    private Double souvenirPrice;

    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "date_of_manufacturing")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfManufacturing;

    @ManyToOne(optional = false)
    private Manufacturer manufacturer;

   @OneToOne
   @JoinColumn(name = "image_id")
   private Image image;

    public Souvenir() {

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Souvenir(Image image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDateOfManufacturing() {
        return dateOfManufacturing;
    }

    public void setDateOfManufacturing(LocalDate dateOfManufacturing) {
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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}