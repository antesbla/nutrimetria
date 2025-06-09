package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.java.model.RelProveedorId;
import org.springframework.stereotype.Service;

import com.java.model.modeloRelProveedor;
import com.java.repository.RelProveedorRepository;
import com.java.service.RelProveedorService;

import java.util.List;

@Service
public class RelProveedorServiceImpl implements RelProveedorService {

    @Autowired
    private RelProveedorRepository repository;

    @Override
    public List<modeloRelProveedor> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloRelProveedor findById(RelProveedorId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloRelProveedor save(modeloRelProveedor relProveedor) {
        return repository.save(relProveedor);
    }

    @Override
    public void deleteById(RelProveedorId id) {
        repository.deleteById(id);
    }
    
    public modeloRelProveedor obtenerRelacionPorMateriaYProveedor(int idMateria, int idProveedor) {
        return repository.findByMateriaPrima_IdAndProveedor_Id(idMateria, idProveedor)
                         .orElse(null);
    }

    @Override
    public modeloRelProveedor obtenerRelacionPorMateriaPrima(int idMateriaPrima) {
        return repository.findByMateriaPrima_Id(idMateriaPrima);
    }




}
