package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloIngredientes;

public interface IngredientesRepository extends JpaRepository<modeloIngredientes, Integer> {
}
