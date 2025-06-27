package com.cibertec.controller;

import com.cibertec.model.Ticket;
import com.cibertec.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Crear ticket
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Ticket ticket) {
        return ticketService.crear(ticket);
    }

    // Actualizar ticket
    @PutMapping
    public ResponseEntity<Map<String, Object>> actualizar(@RequestBody Ticket ticket) {
        return ticketService.actualizar(ticket);
    }

    // Eliminar ticket por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return ticketService.eliminar(id);
    }

    // Listar todos los tickets
    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return ticketService.listar();
    }

    // Buscar ticket por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Long id) {
        return ticketService.buscarPorId(id);
    }
}
