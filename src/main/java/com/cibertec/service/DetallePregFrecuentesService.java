package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.DetallePregFrecuentes;

public interface DetallePregFrecuentesService {
	public ResponseEntity<Map<String, Object>> crear(DetallePregFrecuentes detallePregFrecuentes);
	public ResponseEntity<Map<String, Object>> actualizar(DetallePregFrecuentes detallePregFrecuentes);
	public ResponseEntity<Map<String, Object>> eliminar(Long id);
	public ResponseEntity<Map<String, Object>> listar();
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id);

}
