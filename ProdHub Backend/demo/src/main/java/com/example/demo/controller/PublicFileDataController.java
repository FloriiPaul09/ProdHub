package com.example.demo.controller;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entities.FileData;
import com.example.demo.payload.FileDataResponse;
import com.example.demo.services.FileDataServiceImpl;

@RestController
@RequestMapping("/public")
public class PublicFileDataController {

	private FileDataServiceImpl fileDataService;
	
	public PublicFileDataController(FileDataServiceImpl fileDataService) {
		this.fileDataService = fileDataService;
	}
	
	@PostMapping("/upload")
	public FileDataResponse uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
		FileData fileData = null;
		String downloadUrl = "";
		fileData = fileDataService.saveAttachment(file);
		downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/download")
				.path(fileData.getId())
				.toUriString();
		
		return new FileDataResponse(fileData.getFileName(),
				downloadUrl,
				file.getContentType(),
				file.getSize());	
	}
	
	@GetMapping("/download/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
		FileData fileData = null;
		fileData = fileDataService.getAttachment(fileId);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(fileData.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + fileData.getFileName()
						+ "\"")
				.body(new ByteArrayResource(fileData.getData()));
	}

	
	@GetMapping("/upload")
	public ResponseEntity<Page<FileData>> getAll(Pageable pageable){
		return new ResponseEntity<Page<FileData>>(fileDataService.getAll(pageable), HttpStatus.OK);
	}
	
}
