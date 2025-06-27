package com.cibertec.service.impl;

import java.util.Map;

import com.cibertec.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
		if (usuario == null || usuario.getId() == null || usuario.getId() <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID del usuario es inválido o nulo.", null);
		}

		try {
			return usuarioRepository.findById(usuario.getId())
					.map(existing -> {
						if (usuario.getNombres() != null) existing.setNombres(usuario.getNombres());
						if (usuario.getApellidos() != null) existing.setApellidos(usuario.getApellidos());
						if (usuario.getEmail() != null) existing.setEmail(usuario.getEmail().toLowerCase());
						if (usuario.getPassword() != null) existing.setPassword(passwordEncoder.encode(usuario.getPassword()));
						if (usuario.getRol() != null) existing.setRol(usuario.getRol().toUpperCase());

						Usuario actualizado = usuarioRepository.save(existing);
						return Response.crearResponse(HttpStatus.OK, "Usuario actualizado correctamente.", actualizado);
					})
					.orElse(Response.crearResponse(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID " + usuario.getId(), null));
		} catch (Exception e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el usuario: " + e.getMessage(), null);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminar(Long id) {
		if (id == null || id <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID del usuario es inválido.", null);
		}

		try {
			if (usuarioRepository.existsById(id)) {
				usuarioRepository.deleteById(id);
				return Response.crearResponse(HttpStatus.OK, "Usuario eliminado correctamente.", null);
			} else {
				return Response.crearResponse(HttpStatus.NOT_FOUND, "No se encontró el usuario con ID " + id, null);
			}
		} catch (Exception e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el usuario: " + e.getMessage(), null);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> listar() {
		try {
			var usuarios = usuarioRepository.findAll();
			String mensaje = usuarios.isEmpty() ? "No se encontraron usuarios." : "Usuarios listados correctamente.";
			return Response.crearResponse(HttpStatus.OK, mensaje, usuarios);
		} catch (Exception e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar los usuarios: " + e.getMessage(), null);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id) {
		if (id == null || id <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID del usuario es inválido.", null);
		}

		try {
			return usuarioRepository.findById(id)
					.map(usuario -> Response.crearResponse(HttpStatus.OK, "Usuario encontrado correctamente.", usuario))
					.orElse(Response.crearResponse(HttpStatus.NOT_FOUND, "No se encontró el usuario con ID " + id, null));
		} catch (Exception e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el usuario: " + e.getMessage(), null);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> buscarPorEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El email es inválido o está vacío.", null);
		}

		try {
			return usuarioRepository.findOneByEmail(email.toLowerCase())
					.map(usuario -> Response.crearResponse(HttpStatus.OK, "Usuario encontrado correctamente.", usuario))
					.orElse(Response.crearResponse(HttpStatus.NOT_FOUND, "No se encontró un usuario con ese email.", null));
		} catch (Exception e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el usuario por email: " + e.getMessage(), null);
		}
	}


}
