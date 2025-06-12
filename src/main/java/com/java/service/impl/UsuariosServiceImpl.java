package com.java.service.impl;

import com.java.DTO.UsuarioDetalleDTO;
import com.java.model.modeloUsuarios;
import com.java.repository.UsuariosRepository;
import com.java.service.UsuariosService;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        // Asegurarse de que la contraseña esté cifrada
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return repository.save(usuario);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    // Método requerido por Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        modeloUsuarios usuario = repository.findByUsuario(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new UsuarioDetalleDTO(usuario.getUsuario(), usuario.getClave(), usuario.getPermisos());
    }


    @Autowired
    private UsuariosRepository usuariosRepository;

    @PostConstruct // se ejecuta al iniciar la aplicación (solo para pruebas)
    public void crearUsuarioTest() {
        if (usuariosRepository.findByUsuario("admin").isEmpty()) {
            modeloUsuarios usuario = new modeloUsuarios();
            usuario.setUsuario("admin");
            usuario.setClave(passwordEncoder.encode("admin123")); // cifrada
            usuario.setPermisos(1); // o el valor que uses para admin

            usuariosRepository.save(usuario);
            System.out.println("Usuario admin creado.");
        }
    }
}
