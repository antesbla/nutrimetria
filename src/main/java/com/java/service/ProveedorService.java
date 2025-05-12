package com.java.service;

import java.util.List;

import com.java.model.modeloProveedor;

public interface ProveedorService {
    List<modeloProveedor> findAll();
    modeloProveedor findById(int id);
    modeloProveedor save(modeloProveedor proveedor);
    void deleteById(int id);
}
