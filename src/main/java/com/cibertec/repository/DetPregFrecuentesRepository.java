package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.DetPregFrecuentes;
import org.springframework.stereotype.Repository;

@Repository
public interface DetPregFrecuentesRepository extends JpaRepository<DetPregFrecuentes, Long> {
	

}
