package com.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.model.modeloConsejo;
import com.java.repository.ConsejoRepository;
import com.java.service.ConsejoService;

import java.util.List;

@Service
public class ConsejoServiceImpl implements ConsejoService {

    @Autowired
    private ConsejoRepository repository;

    @Override
    public List<modeloConsejo> findAll() {
        return repository.findAll();
    }

    @Override
    public modeloConsejo findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public modeloConsejo save(modeloConsejo consejo) {
        return repository.save(consejo);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
