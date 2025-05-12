package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloProveedor;

public interface ProveedorRepository extends JpaRepository<modeloProveedor, Integer> {
}
