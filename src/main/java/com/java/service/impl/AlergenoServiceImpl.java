package com.java.service.impl;

import com.java.model.modeloAlergeno;
import com.java.repository.AlergenoRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.service.AlergenoService;

import java.util.List;

@Service
public class AlergenoServiceImpl implements AlergenoService {

    @Autowired
    private AlergenoRepository repository;

    @Override
    public List<modeloAlergeno> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloAlergeno findById(int id) {
        return repository.findById(id).orElse(null);
    }
    
    @Override
    public modeloAlergeno findByNombre(String nombre) {
        return repository.findByNombre(nombre).orElse(null);
    }

    @Override
    public modeloAlergeno save(modeloAlergeno alergeno) {
        return repository.save(alergeno);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
