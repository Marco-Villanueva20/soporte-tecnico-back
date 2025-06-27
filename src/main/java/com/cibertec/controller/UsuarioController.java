package com.cibertec.controller;

import com.cibertec.model.Auth;
import com.cibertec.model.Usuario;
import com.cibertec.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/auth/registrar")
	public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
		return usuarioService.registrar(usuario);
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> actualizar(@RequestBody Usuario usuario) {
		return usuarioService.actualizar(usuario);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
		return usuarioService.eliminar(id);
	}

	public ResponseEntity<Map<String, Object>> listar() {
		return usuarioService.listar();
	}

	public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Long id) {
		return usuarioService.buscarPorId(id);
	}

	public ResponseEntity<Map<String, Object>> buscarPorEmail(@RequestParam String valor) {
		return usuarioService.buscarPorEmail(valor);
	}
}
