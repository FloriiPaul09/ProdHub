package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FileDataResponse {

	
	private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}
