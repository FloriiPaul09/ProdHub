package com.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.UserRole;
import com.project.enums.EUser;

public interface RoleRepository extends JpaRepository<UserRole, Long>{

	Optional<UserRole> findByUserRoleName(EUser userRoleName);
	
}
