package com.java.service;

import java.util.List;

import com.java.model.modeloUsuarios;

public interface UsuariosService {
    List<modeloUsuarios> findAll();
    modeloUsuarios findById(int id);
    modeloUsuarios save(modeloUsuarios usuario);
    void deleteById(int id);
}
