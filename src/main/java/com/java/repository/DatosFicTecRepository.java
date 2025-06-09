package com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.model.DatosFijosFicha;

@Repository
public interface DatosFicTecRepository extends JpaRepository<DatosFijosFicha, Integer> {
}
