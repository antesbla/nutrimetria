package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.model.RelConsejoId;
import com.java.model.modeloConsejo;
import com.java.model.modeloRelConsejo;
import com.java.repository.RelConsejoRepository;
import com.java.service.RelConsejoService;

import java.util.List;

@Service
public class RelConsejoServiceImpl implements RelConsejoService {

    @Autowired
    private RelConsejoRepository repository;

    @Override
    public List<modeloRelConsejo> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloRelConsejo findById(RelConsejoId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloRelConsejo save(modeloRelConsejo relConsejo) {
        return repository.save(relConsejo);
    }

    @Override
    public void deleteById(RelConsejoId id) {
        repository.deleteById(id);
    }

    @Override
    public List<modeloRelConsejo> findByProductoId(int idProducto) {
        return repository.findByProductoId(idProducto);
    }

    public void guardarRelacion(modeloRelConsejo rel) {
    	repository.save(rel);
    }

    @Override
    public void eliminarRelacionesPorProducto(int idProducto) {
        repository.deleteByProductoId(idProducto);
    }

	@Override
	public modeloConsejo findByConsejo(String texto) {
		return null;
	}

}
