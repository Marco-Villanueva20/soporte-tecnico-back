package com.cibertec.service.impl;

import java.util.Collection;
import java.util.Collections;
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
		String rol = usuario.getRol(); 
		
		if (rol == null || rol.isEmpty()) {
	        return Collections.emptyList();
	    }
		final String roleName = rol.startsWith("ROLE_") ? rol : "ROLE_" + rol;

		return Collections.singletonList(() -> roleName);
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
