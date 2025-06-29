package com.cibertec.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtTokenProvider {

	private final static String JWT_TOKEN_SECRETO = "aLg3eqbV2S4pZd9AFiMh4mAcRAt1Y0Jb";
	
	private final static Long JWT_TOKEN_DURACION = 3_600L;
	
	public static String crearToken(String nombres, String email, String rol) {
		 
		System.out.println("[FIRMA] Clave usada para firmar crearToken: " + Arrays.toString(getKey().getEncoded()));
		long expiracionTiempo = JWT_TOKEN_DURACION * 1_000L;
		Date expiracionFecha = new Date(System.currentTimeMillis() + expiracionTiempo);
		
		Map<String , Object> map = new HashMap<>();
		map.put("nombre", nombres);
		map.put("rol", rol);	
		
		System.out.println("[FIRMA] Clave usada para firmar: " + Arrays.toString(getKey().getEncoded()));

		
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(expiracionFecha)
				.addClaims(map)
				.signWith(getKey())
				.compact();
	}
	
	private static Key getKey() {
		return Keys.hmacShaKeyFor(JWT_TOKEN_SECRETO.getBytes());
	}
	
	
	public static UsernamePasswordAuthenticationToken getauth(String token) {
		System.out.println("[FIRMA] Clave usada para firmar getauth: " + Arrays.toString(getKey().getEncoded()));
		System.out.println("[DEBUG] Token recibido para validar: " + token); // <-- aquí
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(getKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
			String rol = (String) claims.get("rol");
			GrantedAuthority authority = () -> rol;
			
			String email = claims.getSubject();
			System.out.println("[VERIFICACIÓN] Clave usada para verificar: " + Arrays.toString(getKey().getEncoded()));

			
			return new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(authority));
			
		} catch (Exception e) {
			System.out.println("Error en el metodo {UsernamePasswordAuthenticationToken(): } " + e.getMessage());
			return null;
		} 
		
	}
	
	
	
}
