package com.java.service;

import java.util.List;

import com.java.model.modeloIngredientes;

public interface IngredientesService {
    List<modeloIngredientes> findAll();
    modeloIngredientes findById(int id);
    modeloIngredientes save(modeloIngredientes ingrediente);
    void deleteById(int id);
}
