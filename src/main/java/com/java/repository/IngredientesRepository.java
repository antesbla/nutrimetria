package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloIngredientes;

public interface IngredientesRepository extends JpaRepository<modeloIngredientes, Integer> {
    Optional<modeloIngredientes> findByNombre(String nombre);
}
