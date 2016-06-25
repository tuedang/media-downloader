package com.demo.music.generator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import com.demo.parser.common.StringHtmlUtils;
import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;

public class M3UGenerator {
    private static final String NEW_LINE="\r\n";
    public static void generateM3U(Album album, OutputStream os) throws IOException {
        StringBuffer sb = new StringBuffer();
        for(Track track: album.getTracks()) {
            sb.append(StringHtmlUtils.unAccent(track.getTitle()));
            sb.append(com.demo.parser.common.FileUtils.getExt(track.getLocation(), ".mp3"));
            sb.append(NEW_LINE);
        }
        os.write(sb.toString().getBytes());
    }

}
