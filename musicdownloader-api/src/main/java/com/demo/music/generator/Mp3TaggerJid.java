package com.demo.music.generator;

import java.io.File;
import java.io.IOException;

import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.io.TextEncoding;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.blinkenlights.jid3.v2.ID3V2Tag;
import org.blinkenlights.jid3.v2.ID3V2_3_0Tag;

import com.demo.music.sdo.TagInfo;

public class Mp3TaggerJid {
    public static void tag(File mp3Src, TagInfo tag) throws IOException {
        if(!mp3Src.getName().contains(".mp3")) {
            return;
        }
        try {
            TextEncoding.setDefaultTextEncoding(TextEncoding.UNICODE);

            MP3File oMediaFile = new MP3File(mp3Src);
            ID3V1Tag v1Tag= oMediaFile.getID3V1Tag();
            oMediaFile.removeID3V1Tag();

            // write v2.3.0 tag to file
            ID3V2Tag oID3V2Tag = new ID3V2_3_0Tag();
            oID3V2Tag.setArtist(tag.getArtist());
            oID3V2Tag.setTitle(tag.getSongTitle());
            oID3V2Tag.setAlbum(tag.getAlbumTitle());
            if (tag.getTotalTrack() == null) {
                oID3V2Tag.setTrackNumber(tag.getTrackNumber());
            } else {
                oID3V2Tag.setTrackNumber(tag.getTrackNumber(), tag.getTotalTrack());
            }

            //clone v1Tag
            if(v1Tag!=null) {
                if(v1Tag.getYear()!=null && !v1Tag.getYear().isEmpty()) {
                    oID3V2Tag.setYear(Integer.valueOf(v1Tag.getYear()));
                }
                if(v1Tag.getGenre()!=null) {
                    oID3V2Tag.setGenre(v1Tag.getGenre().toString());
                }
            }

            oID3V2Tag.setComment("");

            oMediaFile.setID3Tag(oID3V2Tag);

            oMediaFile.sync();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
