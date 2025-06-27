package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.PregFrecuentes;
import org.springframework.stereotype.Repository;

@Repository
public interface PregFrecuentesRepository extends JpaRepository<PregFrecuentes, Long> {


}
