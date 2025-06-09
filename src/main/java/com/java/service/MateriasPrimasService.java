package com.java.service;

import java.util.List;

import com.java.model.modeloMateriasPrimas;

public interface MateriasPrimasService {
    List<modeloMateriasPrimas> findAll();
    modeloMateriasPrimas findById(int id);
    modeloMateriasPrimas save(modeloMateriasPrimas materiaPrima);
    void deleteById(int id);
    modeloMateriasPrimas findByNombre(String nombre);
}
