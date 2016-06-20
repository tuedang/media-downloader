package com.demo.music.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;

import com.demo.music.downloader.dropbox.DropboxContext;
import com.demo.music.downloader.dropbox.DropboxOutputStream;

public class TargetOutputStreamContext {

	public enum TargetType {
		FILE_SYSTEM, DROPBOX
	}

	private TargetType targetType;
	private String basePath;
	public TargetOutputStreamContext(String basePath, TargetType targetType) {
		this.targetType = targetType;
		this.basePath = basePath;
	}

	public OutputStream createOutputStream(String fileName) throws IOException {
		if (targetType == TargetType.FILE_SYSTEM) {
			File baseFolder = new File(basePath);
			if(!baseFolder.exists()) {
				baseFolder.mkdirs();
			}
			
			File albumFolder = new File(basePath, fileName);
			return new FileOutputStream(albumFolder);
		} else {
			return new DropboxOutputStream(FilenameUtils.concat(basePath, fileName));
		}
	}
	
	public File getFileSystem(String fileName) throws IOException {
		if (targetType == TargetType.FILE_SYSTEM) {
			File baseFolder = new File(basePath);
			if(!baseFolder.exists()) {
				baseFolder.mkdirs();
			}
			
			File albumFolder = new File(basePath, fileName);
			return albumFolder;
		} else {
			throw new IOException("Only support for FILE_SYSTEM");
		}
	}
	public boolean existed(String fileName) {
		if (targetType == TargetType.FILE_SYSTEM) {
			File albumFolder = new File(basePath, fileName);
			return albumFolder.exists();
		} else {
			String path = FilenameUtils.concat(basePath, fileName);
			System.out.println("CHECK="+path);
			return DropboxContext.existed(path);
		}
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public String getBasePath() {
		return basePath;
	}

}