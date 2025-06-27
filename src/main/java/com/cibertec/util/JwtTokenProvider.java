package com.cibertec.util;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtTokenProvider {

	private final static String JWT_TOKEN_SECRETO = "aLg3eqbV2S4pZd9AFiMh4mAcRAt1Y0Jb";
	
	private final static Long JWT_TOKEN_DURACION = 3_600L;
	
	public static String crearToken(String nombres, String email) {
		
		long expiracionTiempo = JWT_TOKEN_DURACION * 1_000L;
		Date expiracionFecha = new Date(System.currentTimeMillis() + expiracionTiempo);
		
		Map<String , Object> map = new HashMap<>();
		map.put("nombre", nombres);
		
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

		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(JWT_TOKEN_SECRETO.getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();
			
			String email = claims.getSubject();
			
			return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
			
		} catch (Exception e) {
			System.out.println("Error en el metodo {UsernamePasswordAuthenticationToken(): } " + e.getMessage());
			return null;
		} 
		
	}
	
	
	
}
