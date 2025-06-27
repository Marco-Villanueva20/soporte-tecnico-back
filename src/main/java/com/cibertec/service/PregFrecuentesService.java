package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.PregFrecuentes;

public interface PregFrecuentesService {
	 ResponseEntity<Map<String, Object>> crear(PregFrecuentes pregFrecuentes);
	 ResponseEntity<Map<String, Object>> actualizar(PregFrecuentes pregFrecuentes);
	 ResponseEntity<Map<String, Object>> eliminar(Long id);
	 ResponseEntity<Map<String, Object>> listar();
	 ResponseEntity<Map<String, Object>> buscarPorId(Long id);
}
