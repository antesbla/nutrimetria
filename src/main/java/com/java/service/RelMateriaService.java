package com.java.service;

import java.util.List;

import com.java.model.RelMateriaId;
import com.java.model.modeloRelMateria;

public interface RelMateriaService {
    List<modeloRelMateria> findAll();
    modeloRelMateria findById(RelMateriaId id);
    modeloRelMateria save(modeloRelMateria relMateria);
    void deleteById(RelMateriaId id);
}
