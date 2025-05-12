package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloAlergeno;

public interface AlergenoRepository extends JpaRepository<modeloAlergeno, Integer> {
}
