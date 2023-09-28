package com.example.demo.payload;

import java.util.List;

import com.example.demo.entities.Folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FolderPayload {

    private String projectName;
    private List<Folder> filesAndFolders;
	
}
