package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.model.RelAlergenoId;
import com.java.model.modeloRelAlergeno;
import com.java.repository.RelAlergenoRepository;
import com.java.service.RelAlergenoService;

import java.util.List;

@Service
public class RelAlergenoServiceImpl implements RelAlergenoService {

    @Autowired
    private RelAlergenoRepository repository;

    @Override
    public List<modeloRelAlergeno> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloRelAlergeno findById(RelAlergenoId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloRelAlergeno save(modeloRelAlergeno relAlergeno) {
        return repository.save(relAlergeno);
    }

    @Override
    public void deleteById(RelAlergenoId id) {
        repository.deleteById(id);
    }
}
