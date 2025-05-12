package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.model.modeloProducto;
import com.java.repository.ProductoRepository;
import com.java.service.ProductoService;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Override
    public List<modeloProducto> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloProducto findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloProducto save(modeloProducto producto) {
        return repository.save(producto);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
