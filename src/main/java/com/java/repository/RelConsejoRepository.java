package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.java.model.RelConsejoId;
import com.java.model.modeloRelConsejo;

public interface RelConsejoRepository extends JpaRepository<modeloRelConsejo, RelConsejoId> {

    @Transactional
    @Modifying
    @Query("DELETE FROM modeloRelConsejo r WHERE r.producto.id = :idProducto")
    void deleteByProductoId(int idProducto);

    List<modeloRelConsejo> findByProductoId(int idProducto);
}
