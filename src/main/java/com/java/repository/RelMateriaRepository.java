package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.RelMateriaId;
import com.java.model.modeloRelMateria;

public interface RelMateriaRepository extends JpaRepository<modeloRelMateria, RelMateriaId> {
    List<modeloRelMateria> findByProducto_Id(int idProducto);
    void deleteByProducto_Id(int idProducto);
}
