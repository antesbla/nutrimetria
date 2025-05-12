package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.RelIngredienteId;
import com.java.model.modeloRelIngrediente;

public interface RelIngredienteRepository extends JpaRepository<modeloRelIngrediente, RelIngredienteId> {
}
