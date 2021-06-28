package com.example.doanphantichthietke.repository;


import com.example.doanphantichthietke.model.Cart;
import com.example.doanphantichthietke.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Iterable<Dish> findAllByCart(Cart cart);
}
