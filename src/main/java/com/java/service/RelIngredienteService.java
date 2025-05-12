package com.java.service;

import java.util.List;

import com.java.model.RelIngredienteId;
import com.java.model.modeloRelIngrediente;

public interface RelIngredienteService {
    List<modeloRelIngrediente> findAll();
    modeloRelIngrediente findById(RelIngredienteId id);
    modeloRelIngrediente save(modeloRelIngrediente relIngrediente);
    void deleteById(RelIngredienteId id);
}
