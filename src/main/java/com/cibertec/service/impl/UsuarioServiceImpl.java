package com.cibertec.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibertec.model.Auth;
import com.cibertec.model.Usuario;
import com.cibertec.repository.UsuarioRepository;
import com.cibertec.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder; // Para encriptar contraseñas si es necesario
	
	@Override
	public ResponseEntity<?> login(Auth authRequest) {
		return usuarioRepository.findOneByEmail(authRequest.getEmail())
				.map(usuario -> {
					if (usuario.getPassword().equals(authRequest.getPassword())) {
						return ResponseEntity.ok("Login exitoso");
					} else {
						return ResponseEntity.status(401).body("Contraseña incorrecta");
					}
				})
				.orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
	}

	@Override
	public ResponseEntity<?> registrar(Usuario usuario) {
		
		try {
		    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuario.setEmail(usuario.getEmail().toLowerCase()); // email en minúsculas
		
			usuario.setRol(usuario.getRol().toUpperCase());
			
			Usuario nuevoUsuario = usuarioRepository.save(usuario);
			Usuario usuarioMapeado = usuarioRepository.findById(nuevoUsuario.getId())
				    .orElseThrow(() -> new RuntimeException("No encontrado"));
			return ResponseEntity.status(201).body(usuarioMapeado);
		} catch (Exception e) {
			// Manejar excepciones, como email duplicado
			return ResponseEntity.status(400).body("Error al registrar usuario: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> actualizar(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Map<String, Object>> buscarPorEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}


}
