package com.example.demo.services;

import com.example.demo.entities.Folder;
import com.example.demo.payload.FolderPayload;

public interface FolderService {

	Folder createFolder(FolderPayload payload);
	
}
