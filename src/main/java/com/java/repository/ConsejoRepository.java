package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloConsejo;

public interface ConsejoRepository extends JpaRepository<modeloConsejo, Integer> {
    Optional<modeloConsejo> findByConsejoIgnoreCase(String consejo);
}
