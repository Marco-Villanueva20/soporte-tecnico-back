package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.Chat;


public interface ChatService {
	public ResponseEntity<Map<String, Object>> crear(Chat chat);
	public ResponseEntity<Map<String, Object>> actualizar(Chat chat);
	public ResponseEntity<Map<String, Object>> eliminar(Long id);
	public ResponseEntity<Map<String, Object>> listar();
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id);

}
