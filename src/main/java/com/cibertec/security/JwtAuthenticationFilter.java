package com.cibertec.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cibertec.model.Auth;
import com.cibertec.service.impl.UserDetailImplement;
import com.cibertec.util.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	

	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		
		try {
			Auth auth = new ObjectMapper().readValue(request.getReader(), Auth.class);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					auth.getEmail(),
					auth.getPassword(),
					Collections.emptyList());
			
			return getAuthenticationManager().authenticate(authToken);
			
		} catch (IOException e) {
		    throw new RuntimeException("Error al leer las credenciales del login", e);
		}
		
		

	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain,
			Authentication authResult) 
					throws IOException, ServletException {
		
		UserDetailImplement userDetails = (UserDetailImplement) authResult.getPrincipal();

		String token = JwtTokenProvider.crearToken(userDetails.getNombres(), userDetails.getUsername());
		
		response.addHeader("Authorization", "Bearer " + token);
		
		response.getWriter().flush();
		
		//super.successfulAuthentication(request, response, chain, authResult);
	}

}
