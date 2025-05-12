package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.RelAlergenoId;
import com.java.model.modeloRelAlergeno;

public interface RelAlergenoRepository extends JpaRepository<modeloRelAlergeno, RelAlergenoId> {
}
