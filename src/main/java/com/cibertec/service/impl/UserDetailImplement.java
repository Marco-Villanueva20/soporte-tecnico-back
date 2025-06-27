package com.cibertec.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cibertec.model.Usuario;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailImplement implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
	        return Collections.emptyList();
	    }

	    return usuario.getRoles().stream()
	        .map(rol -> {
	            String nombre = rol.getNombre();
	            final String roleName = nombre.startsWith("ROLE_") ? nombre : "ROLE_" + nombre;
	            return (GrantedAuthority) () -> roleName;
	        })
	        .collect(Collectors.toList());
	}



	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	public String getTelefono() {
		return usuario.getTelefono();
	}
	public String getNombres() {
		return usuario.getNombres();
	}
	public String getApellidos() {
		return usuario.getApellidos();
	}

}
