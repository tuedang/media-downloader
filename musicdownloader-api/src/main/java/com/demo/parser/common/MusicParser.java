package com.demo.parser.common;

import com.demo.music.sdo.Album;

public interface MusicParser {
    Album getAlbum(String url);
}
