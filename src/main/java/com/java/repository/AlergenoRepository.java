package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloAlergeno;

public interface AlergenoRepository extends JpaRepository<modeloAlergeno, Integer> {
    Optional<modeloAlergeno> findByNombre(String nombre);
}
