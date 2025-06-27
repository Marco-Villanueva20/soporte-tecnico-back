package com.cibertec.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final JWTAuthotizationFilter jwtAuthotizationFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
		jwtAuthenticationFilter.setAuthenticationManager(authManager);
		jwtAuthenticationFilter.setFilterProcessesUrl("/api/usuarios/auth/login");

		return http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/usuarios/auth/**").permitAll() // rutas abiertas (login, registro)
						.requestMatchers("/admin/**").hasRole("ADMIN") // solo admin puede acceder a /admin/**
						.requestMatchers("/usuario/**").hasAnyRole("USER", "ADMIN") // usuarios y admins pueden acceder
						.anyRequest().authenticated() // todo lo demas requiere estar autenticado
				).httpBasic(Customizer.withDefaults())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilter(jwtAuthenticationFilter)
				.addFilterBefore(jwtAuthotizationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();

	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * @Bean UserDetailsService userDetailsService() { InMemoryUserDetailsManager
	 * memoryManager = new InMemoryUserDetailsManager(); memoryManager.createUser(
	 * User .withUsername("javier") .password(passwordEncoder().encode("password"))
	 * .roles() .build() );
	 * 
	 * return memoryManager; }
	 */

	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return authManagerBuilder.build();
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println("Password encriptadda:" + new
	 * BCryptPasswordEncoder().encode("javier")); }
	 */
}
