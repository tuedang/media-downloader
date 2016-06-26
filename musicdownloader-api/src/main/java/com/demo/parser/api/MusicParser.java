package com.demo.parser.api;

import com.demo.music.sdo.Album;

import java.io.IOException;
import java.net.URL;

public interface MusicParser {
    String getDomain();

    Album getAlbum(URL url) throws IOException;
}
