package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.model.modeloProveedor;
import com.java.repository.ProveedorRepository;
import com.java.service.ProveedorService;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    @Override
    public List<modeloProveedor> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloProveedor findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloProveedor save(modeloProveedor proveedor) {
        return repository.save(proveedor);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
