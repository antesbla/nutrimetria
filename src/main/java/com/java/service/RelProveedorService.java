package com.java.service;

import java.util.List;

import com.java.model.modeloRelProveedor;

public interface RelProveedorService {
    List<modeloRelProveedor> findAll();
    modeloRelProveedor findById(int id);
    modeloRelProveedor save(modeloRelProveedor relProveedor);
    void deleteById(int id);
}
