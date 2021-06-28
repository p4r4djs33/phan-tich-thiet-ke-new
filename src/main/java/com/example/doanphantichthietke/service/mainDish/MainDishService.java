package com.example.doanphantichthietke.service.mainDish;

import com.example.doanphantichthietke.model.MainDish;
import com.example.doanphantichthietke.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MainDishService extends GeneralService<MainDish> {
    Page<MainDish> findAll(Pageable pageable);
}
