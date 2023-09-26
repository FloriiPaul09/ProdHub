package com.example.demo.services;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.FileData;

public interface FileDataService {

    FileData saveAttachment(MultipartFile file) throws Exception;
    FileData getAttachment(String fileId) throws Exception;
	
}
