package com.demo.music.generator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

import com.demo.parser.common.StringUtils;
import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;

public class M3UGenerator {
    private static final String NEW_LINE="\r\n";
    public static void generateM3U(Album album, File targetFolder) throws IOException {
        StringBuffer sb = new StringBuffer();
        for(Track track: album.getTracks()) {
            sb.append(StringUtils.unAccent(track.getTitle()));
            sb.append(".mp3");
            sb.append(NEW_LINE);
        }
        File out = new File(targetFolder, album.getName()+".m3u");
        FileUtils.writeStringToFile(out, sb.toString());
    }

    public static void generateM3U(Album album, OutputStream os) throws IOException {
        StringBuffer sb = new StringBuffer();
        for(Track track: album.getTracks()) {
            sb.append(StringUtils.unAccent(track.getTitle()));
            sb.append(".mp3");
            sb.append(NEW_LINE);
        }
        os.write(sb.toString().getBytes());
    }

}
