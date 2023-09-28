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

import com.example.demo.entities.FileDataPublic;
import com.example.demo.payload.FileDataResponse;
import com.example.demo.services.FileDataPublicSeviceImpl;

@RestController
@RequestMapping("/public")
public class PublicFileDataController {

	private FileDataPublicSeviceImpl fileDataPublicService;
	
	public PublicFileDataController(FileDataPublicSeviceImpl fileDataPublicService) {
		this.fileDataPublicService = fileDataPublicService;
	}
	
	@PostMapping("/upload")
	public FileDataResponse uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
		FileDataPublic fileDataPublic = null;
		String downloadUrl = "";
		fileDataPublic = fileDataPublicService.saveAttachment(file);
		downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/download")
				.path(fileDataPublic.getId())
				.toUriString();
		
		return new FileDataResponse(fileDataPublic.getFileName(),
				downloadUrl,
				file.getContentType(),
				file.getSize());	
	}
	
	@GetMapping("/download/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
		FileDataPublic fileDataPublic = null;
		fileDataPublic = fileDataPublicService.getAttachment(fileId);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(fileDataPublic.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + fileDataPublic.getFileName()
						+ "\"")
				.body(new ByteArrayResource(fileDataPublic.getData()));
	}

	
	@GetMapping("/upload")
	public ResponseEntity<Page<FileDataPublic>> getAll(Pageable pageable){
		return new ResponseEntity<Page<FileDataPublic>>(fileDataPublicService.getAll(pageable), HttpStatus.OK);
	}
	
}
