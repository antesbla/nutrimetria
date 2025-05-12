package com.java.service;

import java.util.List;

import com.java.model.RelAlergenoId;
import com.java.model.modeloRelAlergeno;

public interface RelAlergenoService {
    List<modeloRelAlergeno> findAll();
    modeloRelAlergeno findById(RelAlergenoId id);
    modeloRelAlergeno save(modeloRelAlergeno relAlergeno);
    void deleteById(RelAlergenoId id);
}
