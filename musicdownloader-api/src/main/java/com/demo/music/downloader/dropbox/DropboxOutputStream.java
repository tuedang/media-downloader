package com.demo.music.downloader.dropbox;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.WebAuthSession;

public class DropboxOutputStream extends ByteArrayOutputStream{
    private static DropboxAPI<WebAuthSession> mDBApi = DropboxContext.getAPI();

    private String fileName;
    public DropboxOutputStream(String fileName) {
    	System.out.println("Dropbox:setup path="+fileName);
    	this.fileName = fileName;
	}
    
	@Override
	public void flush() throws IOException {
		super.flush();
		
		System.out.println("Upload to dropbox...");
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(toByteArray());
        Entry newEntry;
		try {
			newEntry = mDBApi.putFile("/"+fileName, inputStream, size(), null, new ProgressListener() {
				@Override
				public void onProgress(long bytes, long total) {
					System.out.println(bytes+"/"+total);
				}
			});
			System.out.println("Done. Revision of file: " + newEntry.rev);
		} catch (DropboxException e) {
			e.printStackTrace();
		}
        
	}

	@Override
	public void close() throws IOException {
//		this.flush();
	}
	
}
