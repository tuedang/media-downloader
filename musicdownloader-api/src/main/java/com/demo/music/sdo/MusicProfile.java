package com.demo.music.sdo;

import java.io.Serializable;

public class MusicProfile implements Serializable{
	private static final long serialVersionUID = 1234567890L;
	
	private String url;
	private String destFolder;
	private boolean discography;
	public MusicProfile() {
		url="";
		destFolder="";
		discography=false;
	}
	public MusicProfile(String url, String destFolder, boolean discography) {
		this.url=url;
		this.destFolder=destFolder;
		this.discography = discography;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDestFolder() {
		return destFolder;
	}

	public void setDestFolder(String destFolder) {
		this.destFolder = destFolder;
	}

	public boolean isDiscography() {
		return discography;
	}

	public void setDiscography(boolean discography) {
		this.discography = discography;
	}

}
