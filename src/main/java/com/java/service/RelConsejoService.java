package com.java.service;

import java.util.List;

import com.java.model.RelConsejoId;
import com.java.model.modeloConsejo;
import com.java.model.modeloRelConsejo;

public interface RelConsejoService {
    List<modeloRelConsejo> findAll();
    modeloRelConsejo findById(RelConsejoId id);
    modeloRelConsejo save(modeloRelConsejo relConsejo);
    void deleteById(RelConsejoId id);
    modeloConsejo findByConsejo(String texto);
    void eliminarRelacionesPorProducto(int idProducto);
    List<modeloRelConsejo> findByProductoId(int idProducto);
}
