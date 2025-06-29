package com.cibertec.service.impl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.cibertec.model.Chat;
import com.cibertec.repository.ChatRepository;
import com.cibertec.service.ChatService;
import com.cibertec.util.Response;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;


	@Override
	public ResponseEntity<?> enviarMensaje(Chat chat) {
		// Validar y guardar mensaje
		if (chat.getFechaMensaje() == null) {
			chat.setFechaMensaje(LocalDateTime.now());
		}
		Chat guardado = chatRepository.save(chat);

		String destino = "/topic/chat/" + guardado.getTicketId();
		messagingTemplate.convertAndSend(destino, guardado);
        return ResponseEntity.ok("Se guardo el mensaje");
    }

	@Override
	public ResponseEntity<Map<String, Object>> crear(Chat chat) {
		if (chat == null) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El objeto Chat no puede ser nulo.", null);
		}
		if (chat.getMensaje() == null || chat.getMensaje().trim().isEmpty()) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El mensaje de chat no puede estar vacío.", null);
		}
		if (chat.getTicketId() == null || chat.getTicketId() <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del ticket es inválido.", null);
		}
		if (chat.getUsuarioId() == null || chat.getUsuarioId() <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del usuario es inválido.", null);
		}

		try {
			if (chat.getFechaMensaje() == null) {
				chat.setFechaMensaje(LocalDateTime.now());
			}

			Chat chatGuardado = chatRepository.save(chat);
			return Response.crearResponse(HttpStatus.CREATED, "Mensaje de chat creado exitosamente.", chatGuardado);

		} catch (DataAccessException e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error de base de datos al crear el mensaje de chat: " + e.getMessage(), null);
		} catch (Exception e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Ocurrió un error inesperado al crear el mensaje de chat: " + e.getMessage(), null);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> actualizar(Chat chat) {
		if (chat == null || chat.getId() == null || chat.getId() <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST,
					"El ID del mensaje de chat a actualizar es inválido o nulo.", null);
		}
		if (chat.getMensaje() == null || chat.getMensaje().trim().isEmpty()) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El mensaje de chat no puede estar vacío.", null);
		}
		// Normalmente ticketId y usuarioId no se deberían cambiar, pero si tu lógica lo
		// permite, valida aquí
		if (chat.getTicketId() == null || chat.getTicketId() <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del ticket es inválido.", null);
		}
		if (chat.getUsuarioId() == null || chat.getUsuarioId() <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del usuario es inválido.", null);
		}

		try {
			Optional<Chat> chatExistenteOpt = chatRepository.findById(chat.getId());

			if (chatExistenteOpt.isPresent()) {
				Chat chatToUpdate = chatExistenteOpt.get();

				chatToUpdate.setMensaje(chat.getMensaje());

				Chat chatActualizado = chatRepository.save(chatToUpdate);
				return Response.crearResponse(HttpStatus.OK, "Mensaje de chat actualizado exitosamente.",
						chatActualizado);
			} else {
				return Response.crearResponse(HttpStatus.NOT_FOUND,
						"Mensaje de chat con ID " + chat.getId() + " no encontrado para actualizar.", null);
			}
		} catch (DataAccessException e) {

			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error de base de datos al actualizar el mensaje de chat: " + e.getMessage(), null);
		} catch (Exception e) {

			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Ocurrió un error inesperado al actualizar el mensaje de chat: " + e.getMessage(), null);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminar(Long id) {
		if (id == null || id <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del mensaje de chat a eliminar es inválido.",
					null);
		}

		try {
			if (chatRepository.existsById(id)) {
				chatRepository.deleteById(id);
				return Response.crearResponse(HttpStatus.NO_CONTENT, "Mensaje de chat eliminado exitosamente.", null);
			} else {
				return Response.crearResponse(HttpStatus.NOT_FOUND,
						"Mensaje de chat con ID " + id + " no encontrado para eliminar.", null);
			}
		} catch (DataAccessException e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error de base de datos al eliminar el mensaje de chat: " + e.getMessage(), null);
		} catch (Exception e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Ocurrió un error inesperado al eliminar el mensaje de chat: " + e.getMessage(), null);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> listar() {
		try {
			List<Chat> chats = chatRepository.findAll();

			if (chats.isEmpty()) {
				return Response.crearResponse(HttpStatus.OK, "No se encontraron mensajes de chat.", chats);
			} else {
				return Response.crearResponse(HttpStatus.OK, "Mensajes de chat listados exitosamente.", chats);
			}
		} catch (DataAccessException e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error de base de datos al listar los mensajes de chat: " + e.getMessage(),
					null);
		} catch (Exception e) {
			
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Ocurrió un error inesperado al listar los mensajes de chat: " + e.getMessage(),
					null);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> buscarPorId(Long id) {
		if (id == null || id <= 0) {
			return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del mensaje de chat a buscar es inválido.", null);
		}

		try {
			Optional<Chat> chat = chatRepository.findById(id);

			if (chat.isPresent()) {
				return Response.crearResponse(HttpStatus.OK, "Mensaje de chat encontrado exitosamente.", chat.get());
			} else {
				return Response.crearResponse(HttpStatus.NOT_FOUND, "Mensaje de chat con ID " + id + " no encontrado.", null);
			}
		} catch (DataAccessException e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error de base de datos al buscar el mensaje de chat: " + e.getMessage(),
					null);
		} catch (Exception e) {
			return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Ocurrió un error inesperado al buscar el mensaje de chat: " + e.getMessage(),
					null);
		}
	}

}
