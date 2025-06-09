package com.java.service;

import java.util.List;

import com.java.model.RelProveedorId;
import com.java.model.modeloRelProveedor;

public interface RelProveedorService {
    List<modeloRelProveedor> findAll();
    modeloRelProveedor findById(RelProveedorId id);
    modeloRelProveedor save(modeloRelProveedor relProveedor);
    void deleteById(RelProveedorId id);
    modeloRelProveedor obtenerRelacionPorMateriaPrima(int idMateriaPrima);
}
