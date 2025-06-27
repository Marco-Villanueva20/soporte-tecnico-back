package com.cibertec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cibertec.model.Usuario;
import com.cibertec.repository.UsuarioRepository;

@Service
public class UserServiceImplement implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findOneByEmail(email)
				.orElseThrow( () -> new UsernameNotFoundException("El usuario no se ha encontrado con el email: " + email) );
		
		return new UserDetailImplement(usuario);
	}

	
	
}
