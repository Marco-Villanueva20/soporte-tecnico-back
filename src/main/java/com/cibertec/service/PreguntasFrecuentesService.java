package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.PreguntasFrecuentes;

public interface PreguntasFrecuentesService {
	public ResponseEntity<Map<String, Object>> crear(PreguntasFrecuentes preguntasFrecuentes);
	public ResponseEntity<Map<String, Object>> actualizar(PreguntasFrecuentes preguntasFrecuentes);
	public ResponseEntity<Map<String, Object>> eliminar(Long id);
	public ResponseEntity<Map<String, Object>> listar();
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id);
}
