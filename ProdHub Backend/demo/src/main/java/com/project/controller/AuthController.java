package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.payload.JwtAuthResponse;
import com.project.payload.Login;
import com.project.service.AuthService;
import com.project.service.AuthServiceImpl;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/ees/auth")
public class AuthController {

	private AuthService authService;
	@Autowired private AuthServiceImpl as;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<JwtAuthResponse> login(@RequestBody Login login){
		String token = authService.login(login);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setUsername(login.getUsername());
		jwtAuthResponse.setAccessToken(token);
		jwtAuthResponse.setRoles(as.getUser(login.getUsername()).getRoles());
		
		return ResponseEntity.ok(jwtAuthResponse);
	}
	
}
