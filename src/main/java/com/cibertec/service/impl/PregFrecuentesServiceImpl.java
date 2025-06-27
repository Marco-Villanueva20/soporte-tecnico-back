package com.cibertec.service.impl;

import com.cibertec.model.PregFrecuentes;
import com.cibertec.repository.PregFrecuentesRepository;
import com.cibertec.service.PregFrecuentesService;
import com.cibertec.util.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PregFrecuentesServiceImpl implements PregFrecuentesService {

    @Autowired
    private PregFrecuentesRepository pregFrecuentesRepository;

    @Override
    public ResponseEntity<Map<String, Object>> crear(PregFrecuentes preg) {
        if (preg == null) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El objeto no puede ser nulo.", null);
        }

        if (preg.getNombre() == null || preg.getNombre().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío.", null);
        }

        if (preg.getUsuarioId() == null || preg.getUsuarioId() <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del usuario es inválido.", null);
        }

        try {
            PregFrecuentes guardado = pregFrecuentesRepository.save(preg);
            return Response.crearResponse(HttpStatus.CREATED, "Pregunta frecuente creada correctamente.", guardado);
        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al crear: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al crear: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> actualizar(PregFrecuentes preg) {
        if (preg == null || preg.getId() == null || preg.getId() <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID inválido o nulo.", null);
        }

        if (preg.getNombre() == null || preg.getNombre().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío.", null);
        }

        if (preg.getUsuarioId() == null || preg.getUsuarioId() <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El ID del usuario es inválido.", null);
        }

        try {
            Optional<PregFrecuentes> existenteOpt = pregFrecuentesRepository.findById(preg.getId());

            if (existenteOpt.isPresent()) {
                PregFrecuentes existente = existenteOpt.get();
                existente.setNombre(preg.getNombre());
                existente.setUsuarioId(preg.getUsuarioId());

                PregFrecuentes actualizado = pregFrecuentesRepository.save(existente);
                return Response.crearResponse(HttpStatus.OK, "Registro actualizado correctamente.", actualizado);
            } else {
                return Response.crearResponse(HttpStatus.NOT_FOUND,
                        "No se encontró la pregunta frecuente con ID " + preg.getId(), null);
            }

        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al actualizar: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al actualizar: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> eliminar(Long id) {
        if (id == null || id <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID inválido para eliminar.", null);
        }

        try {
            if (pregFrecuentesRepository.existsById(id)) {
                pregFrecuentesRepository.deleteById(id);
                return Response.crearResponse(HttpStatus.OK, "Pregunta frecuente eliminada correctamente.", null);
            } else {
                return Response.crearResponse(HttpStatus.NOT_FOUND,
                        "No se encontró la pregunta frecuente con ID " + id, null);
            }

        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al eliminar: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al eliminar: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            List<PregFrecuentes> lista = pregFrecuentesRepository.findAll();

            if (lista.isEmpty()) {
                return Response.crearResponse(HttpStatus.OK, "No se encontraron preguntas frecuentes.", lista);
            } else {
                return Response.crearResponse(HttpStatus.OK, "Preguntas frecuentes listadas correctamente.", lista);
            }

        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al listar: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al listar: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID inválido para buscar.", null);
        }

        try {
            Optional<PregFrecuentes> encontrado = pregFrecuentesRepository.findById(id);

            if (encontrado.isPresent()) {
                return Response.crearResponse(HttpStatus.OK, "Registro encontrado correctamente.", encontrado.get());
            } else {
                return Response.crearResponse(HttpStatus.NOT_FOUND,
                        "No se encontró la pregunta frecuente con ID " + id, null);
            }

        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al buscar: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al buscar: " + e.getMessage(), null);
        }
    }
}
