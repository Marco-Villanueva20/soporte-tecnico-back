package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.EstadoTicket;

public interface EstadoTicketService {
	public ResponseEntity<Map<String, Object>> crear(EstadoTicket estadoTicket);
	public ResponseEntity<Map<String, Object>> actualizar(EstadoTicket estadoTicket);
	public ResponseEntity<Map<String, Object>> eliminar(Long id);
	public ResponseEntity<Map<String, Object>> listar();
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id);

}
