package com.demo.music.generator;

import com.demo.music.sdo.TagInfo;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

public class AudioTagger {
    public static void tag(File inputFile, TagInfo tagInfo) throws IOException {
        try {
            AudioFile audioFile = AudioFileIO.read(inputFile);

            Tag tag = audioFile.getTagAndConvertOrCreateAndSetDefault();
            tag.setField(FieldKey.TRACK_TOTAL, String.valueOf(tagInfo.getTotalTrack()));
            tag.setField(FieldKey.TRACK, String.valueOf(tagInfo.getTrackNumber()));
            tag.setField(FieldKey.ARTIST, tagInfo.getArtist());
            tag.setField(FieldKey.ALBUM, tagInfo.getAlbumTitle());
            tag.setField(FieldKey.TITLE, tagInfo.getSongTitle());

            tag.deleteField(FieldKey.COMMENT);

            audioFile.commit();
        } catch (CannotWriteException
                | InvalidAudioFrameException
                | CannotReadException
                | ReadOnlyFileException
                | TagException e) {
            throw new IOException(e);
        }
    }
}

