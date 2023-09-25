package com.example.demo.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.JwtAuthenticationEntryPoint;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private UserDetailsService userService;
	private JwtAuthenticationFilter authFilter;
	private JwtAuthenticationEntryPoint jwtEntry;
	
	
	public SecurityConfig(UserDetailsService userService,
			JwtAuthenticationFilter authFilter,
					JwtAuthenticationEntryPoint jwtEntry) {
		
		this.userService = userService;
		this.authFilter = authFilter;
		this.jwtEntry = jwtEntry;
		
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(Collections.singletonList("http://localhost4200"));
			config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
			config.setAllowedHeaders(Collections.singletonList("*"));
			return config;
		}))
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests((authorize) -> authorize
			.requestMatchers(HttpMethod.GET, "/**").permitAll()
			.requestMatchers("prodhub/auth").permitAll()
			.anyRequest().authenticated())
		.exceptionHandling(ex -> ex
			.authenticationEntryPoint(jwtEntry)
		).sessionManagement(session -> session
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);
		
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
}
