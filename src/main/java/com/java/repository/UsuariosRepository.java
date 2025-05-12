package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloUsuarios;

public interface UsuariosRepository extends JpaRepository<modeloUsuarios, Integer> {
}
