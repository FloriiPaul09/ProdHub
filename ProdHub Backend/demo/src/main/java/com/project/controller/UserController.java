package com.project.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.User;
import com.project.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired private UserService us;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN_ROLE')")
	public ResponseEntity<?> create(@RequestBody User user){
		return new ResponseEntity<User>(us.create(user), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		return new ResponseEntity<User>(us.getById(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN_ROLE')")
	public ResponseEntity<?> modify(@PathVariable Long id, @RequestBody User user){
		return new ResponseEntity<User>(us.update(id, user), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN_ROLE')")
	public ResponseEntity<String> delete(@PathVariable Long id){
		return new ResponseEntity<String>(us.delete(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-multiple")
	@PreAuthorize("hasRole('ADMIN_ROLE')")
	public ResponseEntity<String> deleteMultiple(@RequestBody Set<Long> ids){
		for(Long id : ids) us.delete(id);
		return new ResponseEntity<>("Users deleted with success", HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Page<User>> getAll(Pageable pageable){
		return new ResponseEntity<Page<User>>(us.getAllUsersPageable(pageable), HttpStatus.OK);
	}
	
}
