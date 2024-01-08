package com.image.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.image.services.FileService;


@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws Exception {
		
		// File name
		String name = file.getOriginalFilename();
		
		// full path
		
		String randomId = UUID.randomUUID().toString();
		String fullPath = path+File.separator+randomId+"."+FilenameUtils.getExtension(name);
		
		
		// create folder if not created
		File file1 = new File(path);
		if(!file1.exists()) file1.mkdir();
		
		
		
		//Copying File
		Files.copy(file.getInputStream(),Paths.get(fullPath));
		
		
		
		
		return randomId+"."+FilenameUtils.getExtension(name);
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		
		String fullPath = path+ File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
