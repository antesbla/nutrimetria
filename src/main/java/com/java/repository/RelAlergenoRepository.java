package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.RelAlergenoId;
import com.java.model.modeloRelAlergeno;

public interface RelAlergenoRepository extends JpaRepository<modeloRelAlergeno, RelAlergenoId> {
	List<modeloRelAlergeno> findByMateria_Id(int id);
	void deleteByMateria_Id(int id);
}
