package com.java.service;

import java.util.List;

import com.java.model.modeloProducto;

public interface ProductoService {
    List<modeloProducto> findAll();
    modeloProducto findById(int id);
    modeloProducto save(modeloProducto producto);
    void deleteById(int id);
}
