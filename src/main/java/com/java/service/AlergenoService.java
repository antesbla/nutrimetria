package com.java.service;

import java.util.List;

import com.java.model.modeloAlergeno;

public interface AlergenoService {
    List<modeloAlergeno> findAll();
    modeloAlergeno findById(int id);
    modeloAlergeno save(modeloAlergeno alergeno);
    void deleteById(int id);
	modeloAlergeno findByNombre(String nombre);
}
