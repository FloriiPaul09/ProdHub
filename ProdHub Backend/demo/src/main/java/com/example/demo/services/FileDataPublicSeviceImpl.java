package com.example.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.FileDataPublic;
import com.example.demo.repositories.FileDataPublicRepository;


@Service
public class FileDataPublicSeviceImpl {

    private FileDataPublicRepository fileDataPublicRepository;

    public FileDataPublicSeviceImpl(FileDataPublicRepository fileDataPublicRepository) {
        this.fileDataPublicRepository = fileDataPublicRepository;
    }

    public FileDataPublic saveAttachment(MultipartFile file) throws Exception {
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                + fileName);
            }

            FileDataPublic fileDataPublic
                    = new FileDataPublic(fileName,
                    file.getContentType(),
                    file.getBytes());
            return fileDataPublicRepository.save(fileDataPublic);

       } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
       }
    }

    public FileDataPublic getAttachment(String fileId) throws Exception {
        return fileDataPublicRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
    
    public Page<FileDataPublic> getAll(Pageable pageable){
    	return fileDataPublicRepository.findAll(pageable);
    }
	
}
