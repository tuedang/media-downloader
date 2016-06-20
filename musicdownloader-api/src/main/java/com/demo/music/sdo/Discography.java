package com.demo.music.sdo;

import java.util.List;

public class Discography {
	private String name;
	private List<Album> albums;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("name"+name+"\n");
		for(Album album: albums) {
			sb.append("-album:"+album.getName());
		}
		return sb.toString();
	}

}
