package com.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Reposi;
import com.project.entity.RepoPostOption;


@Repository
public interface RepositoryRepository extends JpaRepository<Reposi, Long>{
	
	
}
