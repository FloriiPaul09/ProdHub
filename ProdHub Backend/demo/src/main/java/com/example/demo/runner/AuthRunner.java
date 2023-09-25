package com.example.demo.runner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entities.UserRole;
import com.example.demo.enums.EUserRole;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AuthService;

@Component
public class AuthRunner implements CommandLineRunner{

	@Autowired RoleRepository roleRepository;
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired AuthService authService;
	
	private Set<UserRole> adminRole;
	private Set<UserRole> moderatorRole;
	private Set<UserRole> userRole;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Run...");
		setRoleDefault();
		
	}
	
	private void setRoleDefault() {
		UserRole admin = new UserRole();
		admin.setRoleName(EUserRole.ADMIN);
		roleRepository.save(admin);
		
		UserRole user = new UserRole();
		user.setRoleName(EUserRole.USER);
		roleRepository.save(user);
		
		adminRole = new HashSet<UserRole>();
		adminRole.add(admin);
		adminRole.add(user);
		
		userRole = new HashSet<UserRole>();
		userRole.add(user);
	}

}
