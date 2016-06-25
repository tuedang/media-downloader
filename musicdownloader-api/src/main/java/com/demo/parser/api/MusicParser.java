package com.demo.parser.api;

import com.demo.music.sdo.Album;

import java.io.IOException;
import java.net.URL;

public interface MusicParser {
    default String getDomain() {
        return "http://";
    }

    Album getAlbum(URL url) throws IOException;
}
