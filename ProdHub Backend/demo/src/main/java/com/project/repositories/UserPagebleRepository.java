package com.project.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.project.entity.User;

public interface UserPagebleRepository extends PagingAndSortingRepository<User, Long>{

	
	
}
