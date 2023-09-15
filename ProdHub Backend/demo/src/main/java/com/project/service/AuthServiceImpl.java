package com.project.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.entity.User;
import com.project.entity.UserRole;
import com.project.enums.EUser;
import com.project.exceptions.MyAPIException;
import com.project.payload.Login;
import com.project.payload.RegisterReq;
import com.project.payload.RegisterRes;
import com.project.repositories.RoleRepository;
import com.project.repositories.UserRepository;
import com.project.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService{

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	
	public AuthServiceImpl(AuthenticationManager authenticationManager,
						UserRepository userRepository,
						RoleRepository roleRepository,
						PasswordEncoder passwordEncoder,
						JwtTokenProvider jwtTokenProvider) {
		
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		
	}
	
	public User getUser(String username) {
		return userRepository.findByUsername(username).get();
	}
	
	@Override
	public String login(Login login) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.getUsername(), login.getPassword()
						)	
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		
		return token;
		
	}
	
	@Override
	public RegisterRes register (RegisterReq registerReq) {
		
		if(userRepository.existsByUsername(registerReq.getUsername())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
		}
		
		
		if(userRepository.existsByEmail(registerReq.getEmail())) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
		}
		
		User user = new User();
		user.setUsername(registerReq.getUsername());
		user.setEmail(registerReq.getEmail());
		user.setPassword(passwordEncoder.encode(registerReq.getPassword()));
		
		Set<UserRole> roles = new HashSet<>();
		
		if(registerReq.getRoles() != null) {
			registerReq.getRoles().forEach(role -> {
				UserRole userRole = roleRepository.findByRoleName(getRole(role)).get();
				roles.add(userRole);
			});
		} else {
			UserRole userRole = roleRepository.findByRoleName(EUser.USER_ROLE).get();
			roles.add(userRole);
		}
		
		user.setRoles(roles);
		System.out.println(user);
		userRepository.save(user);
		
		return new RegisterRes(
				registerReq.getUsername(),
				registerReq.getEmail(),
				"User registered successfully.");
		
	}
	
	public EUser getRole(String role) {
		if(role.equals("ADMIN_ROLE")) return EUser.ADMIN_ROLE;
		else return EUser.USER_ROLE;
	}
	
}
