package com.cibertec.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "chat")
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mensaje;

	@Column(name = "fecha_mensaje")
	private LocalDateTime fechaMensaje;

	@Column(name = "ticket_id")
	private Long ticketId;

	@Column(name = "usuario_id")
	private Long usuarioId;

}
