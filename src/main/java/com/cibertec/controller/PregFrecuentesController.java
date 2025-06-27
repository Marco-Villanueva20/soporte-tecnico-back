package com.cibertec.controller;

import com.cibertec.model.PregFrecuentes;
import com.cibertec.service.PregFrecuentesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/preg-frecuentes")
public class PregFrecuentesController {

    @Autowired
    private PregFrecuentesService pregFrecuentesService;

    // Crear
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody PregFrecuentes pregunta) {
        return pregFrecuentesService.crear(pregunta);
    }

    // Actualizar
    @PutMapping
    public ResponseEntity<Map<String, Object>> actualizar(@RequestBody PregFrecuentes pregunta) {
        return pregFrecuentesService.actualizar(pregunta);
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return pregFrecuentesService.eliminar(id);
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return pregFrecuentesService.listar();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Long id) {
        return pregFrecuentesService.buscarPorId(id);
    }
}
