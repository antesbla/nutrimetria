package com.java.service.impl;

import com.java.DTO.UsuarioDetalleDTO;
import com.java.model.modeloUsuarios;
import com.java.repository.UsuariosRepository;
import com.java.service.UsuariosService;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuariosServiceImpl implements UsuariosService, UserDetailsService {

    @Autowired
    private UsuariosRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<modeloUsuarios> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloUsuarios findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloUsuarios save(modeloUsuarios usuario) {
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return repository.save(usuario);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        modeloUsuarios usuario = repository.findByUsuario(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new UsuarioDetalleDTO(usuario.getUsuario(), usuario.getClave(), usuario.getPermisos());
    }


    @Autowired
    private UsuariosRepository usuariosRepository;

    @PostConstruct 
    public void crearUsuarioTest() {
        if (usuariosRepository.findByUsuario("usuario").isEmpty()) {
            modeloUsuarios usuario = new modeloUsuarios();
            usuario.setUsuario("usuario");
            usuario.setClave(passwordEncoder.encode("usuario123")); 
            usuario.setPermisos(1); 

            usuariosRepository.save(usuario);
            System.out.println("Usuario usuario creado.");
        }
    }
}
