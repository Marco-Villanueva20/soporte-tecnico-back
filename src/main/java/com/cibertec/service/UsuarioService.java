package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.Auth;
import com.cibertec.model.Usuario;

public interface UsuarioService {
	//Auth
	public ResponseEntity<?> login(Auth authRequest);
	//crear un usuario
	public ResponseEntity<?> registrar(Usuario usuario);
	
	
	public ResponseEntity<Map<String, Object>> actualizar(Usuario usuario);
	public ResponseEntity<Map<String, Object>> eliminar(Long id);
	public ResponseEntity<Map<String, Object>> listar();
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id);
	public ResponseEntity<Map<String, Object>> buscarPorEmail(String email);
	
}
