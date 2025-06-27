package com.cibertec.service.impl;

import com.cibertec.model.DetPregFrecuentes;
import com.cibertec.repository.DetPregFrecuentesRepository;
import com.cibertec.service.DetPregFrecuentesService;
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
public class DetPregFrecuentesServiceImpl implements DetPregFrecuentesService {

    @Autowired
    private DetPregFrecuentesRepository detPregFrecuentesRepository;

    @Override
    public ResponseEntity<Map<String, Object>> crear(DetPregFrecuentes det) {
        if (det == null) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El objeto no puede ser nulo.", null);
        }

        if (det.getTitulo() == null || det.getTitulo().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El título no puede estar vacío.", null);
        }

        if (det.getDescripcion() == null || det.getDescripcion().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "La descripción no puede estar vacía.", null);
        }


        try {
            DetPregFrecuentes guardado = detPregFrecuentesRepository.save(det);
            return Response.crearResponse(HttpStatus.CREATED, "Registro creado correctamente.", guardado);
        } catch (DataAccessException e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de base de datos al crear: " + e.getMessage(), null);
        } catch (Exception e) {
            return Response.crearResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error inesperado al crear: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> actualizar(DetPregFrecuentes det) {
        if (det == null || det.getId() == null || det.getId() <= 0) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "ID inválido o nulo.", null);
        }

        if (det.getTitulo() == null || det.getTitulo().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "El título no puede estar vacío.", null);
        }

        if (det.getDescripcion() == null || det.getDescripcion().trim().isEmpty()) {
            return Response.crearResponse(HttpStatus.BAD_REQUEST, "La descripción no puede estar vacía.", null);
        }

        try {
            Optional<DetPregFrecuentes> existenteOpt = detPregFrecuentesRepository.findById(det.getId());

            if (existenteOpt.isPresent()) {
                DetPregFrecuentes existente = existenteOpt.get();
                existente.setTitulo(det.getTitulo());
                existente.setDescripcion(det.getDescripcion());
                DetPregFrecuentes actualizado = detPregFrecuentesRepository.save(existente);

                return Response.crearResponse(HttpStatus.OK, "Registro actualizado correctamente.", actualizado);
            } else {
                return Response.crearResponse(HttpStatus.NOT_FOUND,
                        "No se encontró el registro con ID " + det.getId(), null);
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
            if (detPregFrecuentesRepository.existsById(id)) {
                detPregFrecuentesRepository.deleteById(id);
                return Response.crearResponse(HttpStatus.OK, "Registro eliminado correctamente.", null);
            } else {
                return Response.crearResponse(HttpStatus.NOT_FOUND, "No se encontró el registro con ID " + id, null);
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
            List<DetPregFrecuentes> lista = detPregFrecuentesRepository.findAll();

            if (lista.isEmpty()) {
                return Response.crearResponse(HttpStatus.OK, "No se encontraron registros.", lista);
            } else {
                return Response.crearResponse(HttpStatus.OK, "Registros listados correctamente.", lista);
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
            Optional<DetPregFrecuentes> encontrado = detPregFrecuentesRepository.findById(id);

            if (encontrado.isPresent()) {
                return Response.crearResponse(HttpStatus.OK, "Registro encontrado correctamente.", encontrado.get());
            } else {
                return Response.crearResponse(HttpStatus.NOT_FOUND, "No se encontró el registro con ID " + id, null);
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
