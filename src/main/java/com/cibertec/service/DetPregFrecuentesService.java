package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.DetPregFrecuentes;

public interface DetPregFrecuentesService {
	 ResponseEntity<Map<String, Object>> crear(DetPregFrecuentes detPregFrecuentes);
	 ResponseEntity<Map<String, Object>> actualizar(DetPregFrecuentes detPregFrecuentes);
	 ResponseEntity<Map<String, Object>> eliminar(Long id);
	 ResponseEntity<Map<String, Object>> listar();
	 ResponseEntity<Map<String, Object>> buscarPorId(Long id);

}
