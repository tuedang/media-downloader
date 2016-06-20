package com.demo.parser.common;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Discography;

public interface MusicParser {
	Album getAlbum(String url);
	Discography getDiscography(String url);

}
