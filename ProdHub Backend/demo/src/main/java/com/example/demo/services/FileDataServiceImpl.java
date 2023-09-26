package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.FileData;
import com.example.demo.repositories.FileDataRepository;
@Service
public class FileDataServiceImpl implements FileDataService{
	
    private FileDataRepository fileDataRepository;

    public FileDataServiceImpl(FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
    }

    @Override
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

    @Override
    public FileData getAttachment(String fileId) throws Exception {
        return fileDataRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
}
