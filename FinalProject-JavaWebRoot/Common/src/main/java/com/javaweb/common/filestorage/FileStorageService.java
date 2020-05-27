package com.javaweb.common.filestorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStorageService {

	private static final String FILE_LOCATION = "/images";

	public List<String> storage(HttpServletRequest servletRequest, List<MultipartFile> files) {

		// Get the uploaded files and store them
		List<String> fileNames = new ArrayList<String>();
		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {
				String fileName = multipartFile.getOriginalFilename();
				fileNames.add(fileName);
				File imageFile = new File(servletRequest.getServletContext().getRealPath(FILE_LOCATION), fileName);
				try {
					multipartFile.transferTo(imageFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return fileNames;
	}

	public String storage(HttpServletRequest servletRequest, MultipartFile file) {
		String fileName = "";

		if (null != file && file.getSize() > 0) {
			fileName = file.getOriginalFilename();
			File imageFile = new File(servletRequest.getServletContext().getRealPath(FILE_LOCATION), fileName);
			try {
				file.transferTo(imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return fileName;
	}

}
