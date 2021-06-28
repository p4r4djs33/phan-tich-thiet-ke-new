package com.example.doanphantichthietke.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateCreated;
    private String nameClientCreated;
    private String numberContactClientCreated;
    private String addressClientCreated;
    private String status;
    @OneToMany(targetEntity = Dish.class)
    private List<Dish> dishes;

    public Cart() {
    }

    public Cart(Long id, LocalDateTime dateCreated, String nameClientCreated, String numberContactClientCreated,
                String addressClientCreated, String status, List<Dish> dishes) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.nameClientCreated = nameClientCreated;
        this.numberContactClientCreated = numberContactClientCreated;
        this.addressClientCreated = addressClientCreated;
        this.status = status;
        this.dishes = dishes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getNameClientCreated() {
        return nameClientCreated;
    }

    public void setNameClientCreated(String nameClientCreated) {
        this.nameClientCreated = nameClientCreated;
    }

    public String getNumberContactClientCreated() {
        return numberContactClientCreated;
    }

    public void setNumberContactClientCreated(String numberContactClientCreated) {
        this.numberContactClientCreated = numberContactClientCreated;
    }

    public String getAddressClientCreated() {
        return addressClientCreated;
    }

    public void setAddressClientCreated(String addressClientCreated) {
        this.addressClientCreated = addressClientCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
