package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.FileData;

public interface FileDataRepository extends JpaRepository<FileData, String>{

}
