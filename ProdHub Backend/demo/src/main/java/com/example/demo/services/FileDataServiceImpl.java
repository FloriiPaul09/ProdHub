package com.example.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.FileData;
import com.example.demo.repositories.FileDataRepository;
@Service
public class FileDataServiceImpl{
	
    private FileDataRepository fileDataRepository;

    public FileDataServiceImpl(FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
    }

    public FileData saveAttachment(MultipartFile file) throws Exception {
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                + fileName);
            }

            FileData fileData
                    = new FileData(fileName,
                    file.getContentType(),
                    file.getBytes());
            return fileDataRepository.save(fileData);

       } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
       }
    }

    public FileData getAttachment(String fileId) throws Exception {
        return fileDataRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
    
    public Page<FileData> getAll(Pageable pageable){
    	return fileDataRepository.findAll(pageable);
    }
    
}
