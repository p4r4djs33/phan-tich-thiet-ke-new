package com.example.doanphantichthietke.service.admin.impl;

import com.example.doanphantichthietke.model.Admin;
import com.example.doanphantichthietke.model.Cart;
import com.example.doanphantichthietke.repository.AdminRepository;
import com.example.doanphantichthietke.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Iterable<Admin> findAll() {return adminRepository.findAll();    }

    @Override
    public Optional<Admin> findById(Long id) { return adminRepository.findById(id);}

    @Override
    public void save(Admin admin) { adminRepository.save(admin);}

    @Override
    public void save(Optional<Admin> admin) {

    }

    @Override
    public void remove(Long id) { adminRepository.deleteById(id); }
}