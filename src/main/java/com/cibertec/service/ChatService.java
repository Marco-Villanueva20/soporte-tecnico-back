package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.Chat;


public interface ChatService {

	ResponseEntity<?> enviarMensaje(Chat chat);

	 ResponseEntity<Map<String, Object>> crear(Chat chat);
	 ResponseEntity<Map<String, Object>> actualizar(Chat chat);
	 ResponseEntity<Map<String, Object>> eliminar(Long id);
	 ResponseEntity<Map<String, Object>> listar();

	ResponseEntity<Map<String, Object>> buscarPorId(Long id);


}
