package com.example.doanphantichthietke.service;

import com.example.doanphantichthietke.exception.DuplicateAccountClient;

import java.util.Optional;

public interface GeneralService<T> {
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    void save(T t);

    void save(Optional<T> t);

    void remove(Long id);
}
