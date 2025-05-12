package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloProducto;

public interface ProductoRepository extends JpaRepository<modeloProducto, Integer> {
}
