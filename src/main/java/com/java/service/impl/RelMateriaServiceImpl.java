package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.model.RelMateriaId;
import com.java.model.modeloRelMateria;
import com.java.repository.RelMateriaRepository;
import com.java.service.RelMateriaService;

import java.util.List;

@Service
public class RelMateriaServiceImpl implements RelMateriaService {

    @Autowired
    private RelMateriaRepository repository;

    @Override
    public List<modeloRelMateria> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloRelMateria findById(RelMateriaId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloRelMateria save(modeloRelMateria relMateria) {
        return repository.save(relMateria);
    }

    @Override
    public void deleteById(RelMateriaId id) {
        repository.deleteById(id);
    }

	@Override
	public List<modeloRelMateria> findByProductoId(int idProducto) {
		return repository.findByProducto_Id(idProducto);
	}
	
	@Transactional
	public void deleteByProductoId(int idProducto) {
	    repository.deleteByProducto_Id(idProducto);
	}

}
