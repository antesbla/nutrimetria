package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloMateriasPrimas;
import com.java.model.modeloRelProveedor;

public interface RelProveedorRepository extends JpaRepository<modeloRelProveedor, Integer> {
    modeloRelProveedor findByMateriaPrima(modeloMateriasPrimas materiaPrima);
}
