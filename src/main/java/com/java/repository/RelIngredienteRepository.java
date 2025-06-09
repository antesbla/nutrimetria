package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.RelIngredienteId;
import com.java.model.modeloRelIngrediente;

public interface RelIngredienteRepository extends JpaRepository<modeloRelIngrediente, RelIngredienteId> {
	List<modeloRelIngrediente> findByMateriaPrima_Id(int id);
	void deleteByMateriaPrima_Id(int id);
}
