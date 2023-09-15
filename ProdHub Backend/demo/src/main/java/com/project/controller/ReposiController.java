package com.project.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.entity.Reposi;
import com.project.service.ReposiService;



@Controller
public class ReposiController {

	@Autowired
	private ReposiService reposiService;
	
	@GetMapping("/")
	public String get(Model model) {
		List<Reposi> reposi = reposiService.getFiles();
		model.addAttribute("reposi", reposi);
		return "redirect:/";
	}
	
	@PostMapping("/uploadFiles")
	public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		for(MultipartFile file: files) {
			reposiService.saveFile(file);
		}
		return "redirect:/";
	}
	
	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId){
		Reposi reposi = reposiService.getFile(fileId).get();
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(reposi.getType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\""+reposi.getName()+"\"")
				.body(new ByteArrayResource(reposi.getData()));
	}
	
}
