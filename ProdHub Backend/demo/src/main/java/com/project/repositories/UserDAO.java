package com.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.project.entity.User;

public interface UserDAO extends CrudRepository<User, Long>{

	public boolean existsByUsername(String username);
	public boolean existsByEmail(String email);
	
	public boolean existsByUsernameAndIdNot(String username, Long id);
	public boolean existsByEmailAndIdNot(String email, Long id);
	
}
