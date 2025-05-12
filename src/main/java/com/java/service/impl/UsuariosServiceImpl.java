package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.model.modeloUsuarios;
import com.java.repository.UsuariosRepository;
import com.java.service.UsuariosService;

import java.util.List;

@Service
public class UsuariosServiceImpl implements UsuariosService {

    @Autowired
    private UsuariosRepository repository;

    @Override
    public List<modeloUsuarios> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloUsuarios findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloUsuarios save(modeloUsuarios usuario) {
        return repository.save(usuario);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
