package com.example.doanphantichthietke.service.client;


import com.example.doanphantichthietke.exception.DuplicateAccountClient;
import com.example.doanphantichthietke.model.Client;

import java.util.Optional;

public interface ClientService {
    Iterable<Client> findAll();

    Optional<Client> findById(Long id);

    void save(Client client) throws DuplicateAccountClient;

    void remove(Long id);
}
