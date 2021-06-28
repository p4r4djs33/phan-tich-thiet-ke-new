package com.example.doanphantichthietke.service.client.impl;

import com.example.doanphantichthietke.exception.DuplicateAccountClient;
import com.example.doanphantichthietke.model.Client;
import com.example.doanphantichthietke.repository.ClientRepository;
import com.example.doanphantichthietke.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public void save(Client client) throws DuplicateAccountClient {
        try {
            clientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateAccountClient();
        }
    }

    @Override
    public void remove(Long id) {
        clientRepository.deleteById(id);
    }
}
