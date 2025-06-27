package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.Ticket;

public interface TicketService {
	public ResponseEntity<Map<String, Object>> crear(Ticket ticket);
	public ResponseEntity<Map<String, Object>> actualizar(Ticket ticket);
	public ResponseEntity<Map<String, Object>> eliminar(Long id);
	public ResponseEntity<Map<String, Object>> listar();
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id);

}
