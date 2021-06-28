package com.example.doanphantichthietke.service.cart.impl;

import com.example.doanphantichthietke.model.Cart;
import com.example.doanphantichthietke.repository.CartRepository;
import com.example.doanphantichthietke.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Override
    public Iterable<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void save(Optional<Cart> cart) {
        cartRepository.save(cart.get());
    }

    @Override
    public void remove(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Page<Cart> findAllOrderByDate(Pageable pageable) {
        return cartRepository.findAllOrderByDate(pageable);
    }
}
