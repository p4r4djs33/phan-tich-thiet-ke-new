package com.example.doanphantichthietke.service.cart;

import com.example.doanphantichthietke.model.Cart;
import com.example.doanphantichthietke.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CartService extends GeneralService<Cart> {
    Page<Cart> findAllOrderByDate(Pageable pageable);
}
