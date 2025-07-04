package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.Ticket;

public interface TicketService {
	 ResponseEntity<Map<String, Object>> crear(Ticket ticket);
	 ResponseEntity<Map<String, Object>> actualizar(Ticket ticket);
	 ResponseEntity<Map<String, Object>> eliminar(Long id);
	 ResponseEntity<Map<String, Object>> listar();
	 ResponseEntity<Map<String, Object>> buscarPorId(Long id);

}
