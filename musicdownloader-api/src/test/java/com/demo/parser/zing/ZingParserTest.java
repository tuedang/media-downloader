package com.demo.parser.zing;

import org.testng.annotations.Test;

import com.demo.music.sdo.Discography;

public class ZingParserTest {

	
	@Test
	public void getDiscography() {
		String url="http://mp3.zing.vn/tim-kiem/playlist.html?q=Nh%C6%B0+Qu%E1%BB%B3nh";
		ZingParser nctParser = new ZingParser();
		Discography discography= nctParser.getDiscography(url);
		System.out.println(discography);
	}

}
