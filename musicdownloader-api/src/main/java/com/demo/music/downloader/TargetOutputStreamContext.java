package com.demo.music.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;

public class TargetOutputStreamContext {

    public enum TargetType {
        FILE_SYSTEM, DROPBOX
    }

    private TargetType targetType;
    private String basePath;
    public TargetOutputStreamContext(String basePath) {
        this.targetType = TargetType.FILE_SYSTEM;
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
            throw new IOException("Not support yet");
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
        }
        return false;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public String getBasePath() {
        return basePath;
    }

}
