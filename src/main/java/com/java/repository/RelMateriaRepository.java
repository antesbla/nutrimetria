package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.RelMateriaId;
import com.java.model.modeloRelMateria;

public interface RelMateriaRepository extends JpaRepository<modeloRelMateria, RelMateriaId> {
}
