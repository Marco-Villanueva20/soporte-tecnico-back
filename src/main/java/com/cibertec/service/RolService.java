package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.Rol;

public interface RolService {
	// Crear un rol
	public ResponseEntity<Map<String, Object>> crear(Rol rol);
	public ResponseEntity<Map<String, Object>> actualizar(Rol rol);
	public ResponseEntity<Map<String, Object>> eliminar(Long id);
	public ResponseEntity<Map<String, Object>> listar();
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id);
}
