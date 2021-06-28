package com.example.doanphantichthietke.service.mainDish.impl;

import com.example.doanphantichthietke.model.MainDish;
import com.example.doanphantichthietke.repository.MainDishRepository;
import com.example.doanphantichthietke.service.mainDish.MainDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainDishImpl implements MainDishService {
    @Autowired
    MainDishRepository mainDishRepository;
    @Override
    public Iterable<MainDish> findAll() {
        return mainDishRepository.findAll();
    }

    @Override
    public Optional<MainDish> findById(Long id) {
        return mainDishRepository.findById(id);
    }

    @Override
    public void save(MainDish mainDish) {
        mainDishRepository.save(mainDish);
    }

    @Override
    public void save(Optional<MainDish> mainDish) {

    }

    @Override
    public void remove(Long id) {
        mainDishRepository.deleteById(id);
    }

    @Override
    public Page<MainDish> findAll(Pageable pageable) {
        return mainDishRepository.findAll(pageable);
    }
}
