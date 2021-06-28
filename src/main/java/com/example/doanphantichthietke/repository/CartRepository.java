package com.example.doanphantichthietke.repository;

import com.example.doanphantichthietke.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select e from Cart e order by e.dateCreated desc")
    Page<Cart> findAllOrderByDate(Pageable pageable);
}
