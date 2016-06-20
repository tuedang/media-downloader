package com.demo.parser.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.music.sdo.Album;

public class DiscographyHelper {
	public static String getDiscographyName(List<Album> albums) {
		if(albums==null) {
			return "DEFAULT";
		}
		Map<String, Integer> countNames =  new HashMap<String, Integer>();
		
		for (Album album : albums) {
			String discographyName = album.getTracks().get(0).getCreator();
			Integer count = countNames.get(discographyName);
			if(count==null) {
				count=0;
			}
			countNames.put(discographyName, ++count);
		}
		String finalDiscographyName="";
		int totalCount=-1;
		for(String disName: countNames.keySet()) {
			int t=countNames.get(disName);
			if(t>totalCount) {
				finalDiscographyName = disName;
				totalCount =t;
			}
		}
		System.out.println(String.format("Discography with name=%s, vote count=%s on total=%s albums", finalDiscographyName, totalCount, albums.size()));
		return finalDiscographyName;
	}

}
