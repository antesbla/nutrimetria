package com.java.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.model.RelProveedorId;
import com.java.model.modeloRelProveedor;

public interface RelProveedorRepository extends JpaRepository<modeloRelProveedor, RelProveedorId> {
	@Query("SELECT r FROM modeloRelProveedor r WHERE r.materiaPrima.id = :id")
	List<modeloRelProveedor> findAllByMateriaPrimaId(@Param("id") int id);
	modeloRelProveedor findTopByMateriaPrima_Id(RelProveedorId id);
	void deleteByMateriaPrima_Id(RelProveedorId materiaPrimaId);
	Optional<modeloRelProveedor> findByMateriaPrima_IdAndProveedor_Id(int idMateria, int idProveedor);
	modeloRelProveedor findByMateriaPrima_Id(int idMateriaPrima);

}
