package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{
	
	public Rol findByNombre(String nombre); // Método para buscar rol por nombre
}
