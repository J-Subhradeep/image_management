package com.image.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.image.payload.FileResponse;
import com.image.services.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

	
	@Autowired
	private FileService fileService;
	
	
	@Value("${project.image}")
	private String path;
	
	
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(
			
			@RequestParam("image") MultipartFile image
			){
		String uploadImage;
		try {
			uploadImage = this.fileService.uploadImage(path, image);
		} catch (Exception e) {
			return new ResponseEntity<FileResponse>(new FileResponse(null,"Error"),HttpStatus.INTERNAL_SERVER_ERROR);
			// TODO Auto-generated catch block
			
		}
		return new ResponseEntity<FileResponse>(new FileResponse(uploadImage,"Successful"),HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
