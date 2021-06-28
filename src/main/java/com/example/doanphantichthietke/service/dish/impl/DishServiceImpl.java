package com.example.doanphantichthietke.service.dish.impl;

import com.example.doanphantichthietke.model.Cart;
import com.example.doanphantichthietke.model.Dish;
import com.example.doanphantichthietke.repository.DishRepository;
import com.example.doanphantichthietke.service.dish.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishRepository dishRepository;

    @Override
    public Iterable<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Optional<Dish> findById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public void save(Dish dish) {
        dishRepository.save(dish);
    }

    @Override
    public void remove(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public Iterable<Dish> findAllByCart(Cart cart) {
        return dishRepository.findAllByCart(cart);
    }
}
