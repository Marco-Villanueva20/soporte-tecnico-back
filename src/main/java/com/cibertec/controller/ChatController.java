package com.cibertec.controller;
import com.cibertec.model.Chat;
import com.cibertec.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;


    // Crear un mensaje
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Chat chat) {
        return chatService.crear(chat);
    }

    // Actualizar un mensaje
    @PutMapping
    public ResponseEntity<Map<String, Object>> actualizar(@RequestBody Chat chat) {
        return chatService.actualizar(chat);
    }

    // Eliminar un mensaje por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return chatService.eliminar(id);
    }

    // Listar todos los mensajes
    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return chatService.listar();
    }

    // Buscar mensaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Long id) {
        return chatService.buscarPorId(id);
    }
}