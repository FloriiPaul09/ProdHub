package com.project.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.entity.Reposi;
import com.project.repositories.RepositoryRepository;

@Service
public class ReposiService {

	@Autowired
	private RepositoryRepository repositoryRepository;
	
	public Reposi saveFile(MultipartFile file) {
		String docname = file.getOriginalFilename();
		try {
			Reposi reposi = new Reposi(docname, file.getContentType(), file.getBytes());
			return repositoryRepository.save(reposi);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<Reposi> getFile(Long fileId) {
		return repositoryRepository.findById(fileId);
	}
	
	public List<Reposi> getFiles(){
		return repositoryRepository.findAll();
	}
	
}
