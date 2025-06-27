package com.cibertec.controller;

import com.cibertec.model.DetPregFrecuentes;
import com.cibertec.service.DetPregFrecuentesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/det-preg-frecuentes")
public class DetPregFrecuentesController {

    @Autowired
    private DetPregFrecuentesService detPregFrecuentesService;

    // Crear
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody DetPregFrecuentes detalle) {
        return detPregFrecuentesService.crear(detalle);
    }

    // Actualizar
    @PutMapping
    public ResponseEntity<Map<String, Object>> actualizar(@RequestBody DetPregFrecuentes detalle) {
        return detPregFrecuentesService.actualizar(detalle);
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return detPregFrecuentesService.eliminar(id);
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return detPregFrecuentesService.listar();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Long id) {
        return detPregFrecuentesService.buscarPorId(id);
    }
}
