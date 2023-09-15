package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.entity.User;
import com.project.repositories.UserDAO;
import com.project.repositories.UserPagebleRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired private UserDAO userRepo;
	@Autowired private UserPagebleRepository userPageRepo;
	
	public User create(User user) {
		if(userRepo.existsByUsername(user.getUsername()))
			throw new EntityExistsException("It exixst already a username with this username");
		if(userRepo.existsByEmail(user.getEmail()))
			throw new EntityExistsException("It already exists this email");
		return userRepo.save(user);
	}
	
	public User getById(Long id) {
		if(!userRepo.existsById(id))
			throw new EntityNotFoundException("Impossible to find user");
		return userRepo.findById(id).get();
	}
	
	public User update(Long id, User user) {
		if(!userRepo.existsById(id)|| user.getId() != id)
			throw new EntityNotFoundException("Impossible to find the user");
		if(userRepo.existsByEmailAndIdNot(user.getEmail(), user.getId()))
			throw new EntityExistsException("User already existing with this email");
		
		return userRepo.save(user);
	}
	
	public String delete(Long id) {
		if(!userRepo.existsById(id))
			throw new EntityNotFoundException("Impossible to find the user");
		userRepo.deleteById(id);
		return "User deleted with success";
	}
	
	public Page<User> getAllUsersPageable(Pageable pageable){
		return userPageRepo.findAll(pageable);
	}
	
}
