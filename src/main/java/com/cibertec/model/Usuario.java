package com.cibertec.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	private String nombres;
	private String apellidos;
	
	private String email;
	private String password;
	private String roles;
	@Column(name = "fecha_registro")
	private LocalDateTime fechaRegistro;

}
