package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloUsuarios;

public interface UsuariosRepository extends JpaRepository<modeloUsuarios, Integer> {
	Optional<modeloUsuarios> findByUsuario(String usuario);
}
