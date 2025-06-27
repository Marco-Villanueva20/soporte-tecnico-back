package com.cibertec.service.impl;

import com.cibertec.model.Ticket;
import com.cibertec.repository.TicketRepository;
import com.cibertec.service.TicketService;
import com.cibertec.util.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public ResponseEntity<Map<String, Object>> crear(Ticket ticket) {
        if (ticket == null) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El objeto Ticket no puede ser nulo.", null);
        }

        if (ticket.getDescripcion() == null || ticket.getDescripcion().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "La descripción del ticket no puede estar vacía.", null);
        }

        if (ticket.getEstado() == null || ticket.getEstado().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El estado del ticket no puede estar vacío.", null);
        }

        if (ticket.getClienteId() == null || ticket.getClienteId() <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del cliente es inválido.", null);
        }

        if (ticket.getTrabajadorId() == null || ticket.getTrabajadorId() <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del trabajador es inválido.", null);
        }

        try {
            if (ticket.getFechaCreacion() == null) {
                ticket.setFechaCreacion(LocalDateTime.now());
            }

            Ticket guardado = ticketRepository.save(ticket);
            return Response.crearResponse(HttpStatus.CREATED, "Ticket creado exitosamente.", guardado);
        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al crear el ticket: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al crear el ticket: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> actualizar(Ticket ticket) {
        if (ticket == null || ticket.getId() == null || ticket.getId() <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID del ticket inválido o nulo.", null);
        }

        if (ticket.getDescripcion() == null || ticket.getDescripcion().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "La descripción no puede estar vacía.", null);
        }

        if (ticket.getEstado() == null || ticket.getEstado().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El estado no puede estar vacío.", null);
        }

        if (ticket.getClienteId() == null || ticket.getClienteId() <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del cliente es inválido.", null);
        }

        if (ticket.getTrabajadorId() == null || ticket.getTrabajadorId() <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del trabajador es inválido.", null);
        }

        try {
            Optional<Ticket> existenteOpt = ticketRepository.findById(ticket.getId());

            if (existenteOpt.isPresent()) {
                Ticket existente = existenteOpt.get();
                existente.setDescripcion(ticket.getDescripcion());
                existente.setEstado(ticket.getEstado());
                existente.setClienteId(ticket.getClienteId());
                existente.setTrabajadorId(ticket.getTrabajadorId());
                // No actualizamos fechaCreacion, ya que representa el momento original de creación

                Ticket actualizado = ticketRepository.save(existente);
                return Response.crearResponse(HttpStatus.OK, "Ticket actualizado correctamente.", actualizado);
            } else {
                return Response.crearResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el ticket con ID " + ticket.getId(), null);
            }

        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al actualizar el ticket: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al actualizar el ticket: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> eliminar(Long id) {
        if (id == null || id <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID inválido para eliminar.", null);
        }

        try {
            if (ticketRepository.existsById(id)) {
                ticketRepository.deleteById(id);
                return Response.crearResponse(HttpStatus.OK, "Ticket eliminado correctamente.", null);
            } else {
                return Response.crearResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el ticket con ID " + id, null);
            }
        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al eliminar el ticket: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al eliminar el ticket: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            List<Ticket> lista = ticketRepository.findAll();

            if (lista.isEmpty()) {
                return Response.crearResponse(HttpStatus.OK, "No se encontraron tickets.", lista);
            } else {
                return Response.crearResponse(HttpStatus.OK, "Tickets listados correctamente.", lista);
            }

        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al listar tickets: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al listar tickets: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID inválido para buscar.", null);
        }

        try {
            Optional<Ticket> encontrado = ticketRepository.findById(id);

            if (encontrado.isPresent()) {
                return Response.crearResponse(HttpStatus.OK, "Ticket encontrado correctamente.", encontrado.get());
            } else {
                return Response.crearResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el ticket con ID " + id, null);
            }

        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al buscar el ticket: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al buscar el ticket: " + e.getMessage(), null);
        }
    }
}
