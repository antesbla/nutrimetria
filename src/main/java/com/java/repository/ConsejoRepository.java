package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.model.modeloConsejo;

public interface ConsejoRepository extends JpaRepository<modeloConsejo, Integer> {
}
