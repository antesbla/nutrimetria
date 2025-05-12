package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.RelConsejoId;
import com.java.model.modeloRelConsejo;

public interface RelConsejoRepository extends JpaRepository<modeloRelConsejo, RelConsejoId> {
}
