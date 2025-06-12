package com.java.service;

import java.util.List;

import com.java.model.modeloConsejo;

public interface ConsejoService {
    List<modeloConsejo> findAll();
    modeloConsejo findById(int id);
    modeloConsejo save(modeloConsejo consejo);
    void deleteById(int id);
    modeloConsejo findByConsejo(String texto);
}
