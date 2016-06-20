package com.demo.parser.zing;

import java.util.List;

import org.testng.annotations.Test;

public class ZingHtmlPageParserTest {
	
	private String url="http://mp3.zing.vn/album/Tim-Lai-Bau-Troi-Tuan-Hung/ZWZ9E89F.html?st=1";
	private ZingHtmlPageParser zp = new ZingHtmlPageParser(url);
	
	@Test
	public void parsePlaylistLink() throws Exception{
		String playlistLink = zp.parsePlaylistLink();
		System.out.println(playlistLink);
	}
	
	@Test
	public void parseAlbumLink() throws Exception{
		String playlistLink = zp.parseAlbumLink();
		System.out.println(playlistLink);
	}
	
	@Test
	public void parseAlbumName() throws Exception{
		String playlistLink = zp.parseAlbumName();
		System.out.println(playlistLink);
	}
	
	
	@Test
	public void parseArtist() throws Exception{
		String playlistLink = zp.parseArtist();
		System.out.println(playlistLink);
	}
	
	@Test
	public void parseDiscographyLink() throws Exception {
		String u="http://mp3.zing.vn/tim-kiem/playlist.html?q=Nh%C6%B0+Qu%E1%BB%B3nh";
		ZingHtmlPageParser zpd = new ZingHtmlPageParser(u);
		List<String> discographyLinks = zpd.parseDiscographyLink();
		System.out.println(discographyLinks.size());
		for (String string : discographyLinks) {
			System.out.println(string);
		}
	}

}
