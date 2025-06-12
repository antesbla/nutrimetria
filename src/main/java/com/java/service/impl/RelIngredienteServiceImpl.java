package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.model.RelIngredienteId;
import com.java.model.modeloRelIngrediente;
import com.java.repository.RelIngredienteRepository;
import com.java.service.RelIngredienteService;

import java.util.List;

@Service
public class RelIngredienteServiceImpl implements RelIngredienteService {

    @Autowired
    private RelIngredienteRepository repository;

    @Override
    public List<modeloRelIngrediente> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloRelIngrediente findById(RelIngredienteId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloRelIngrediente save(modeloRelIngrediente relIngrediente) {
        return repository.save(relIngrediente);
    }

    @Override
    public void deleteById(RelIngredienteId id) {
        repository.deleteById(id);
    }

	@Override
	public List<modeloRelIngrediente> findByMateriaPrimaId(int idMateria) {
		return repository.findByMateriaPrimaId(idMateria);
	}
    

}
