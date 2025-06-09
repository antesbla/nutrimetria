package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.model.modeloIngredientes;
import com.java.repository.IngredientesRepository;
import com.java.service.IngredientesService;

import java.util.List;

@Service
public class IngredientesServiceImpl implements IngredientesService {

    @Autowired
    private IngredientesRepository repository;

    @Override
    public List<modeloIngredientes> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloIngredientes findById(int id) {
        return repository.findById(id).orElse(null);
    }
    
    @Override
    public modeloIngredientes findByNombre(String nombre) {
        return repository.findByNombre(nombre).orElse(null);
    }

    @Override
    public modeloIngredientes save(modeloIngredientes ingrediente) {
        return repository.save(ingrediente);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
