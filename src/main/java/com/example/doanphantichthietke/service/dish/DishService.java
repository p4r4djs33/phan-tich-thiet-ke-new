package com.example.doanphantichthietke.service.dish;

import com.example.doanphantichthietke.model.Cart;
import com.example.doanphantichthietke.model.Dish;

import java.util.Optional;

public interface DishService {
    Iterable<Dish> findAll();

    Optional<Dish> findById(Long id);

    void save(Dish t);

    void remove(Long id);

    Iterable<Dish> findAllByCart(Cart cart);
}
