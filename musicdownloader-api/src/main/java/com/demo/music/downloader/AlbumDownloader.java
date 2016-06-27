package com.demo.music.downloader;

import java.io.IOException;
import java.io.OutputStream;

import com.demo.music.generator.AudioTagger;
import com.demo.parser.common.FileUtils;
import com.demo.parser.common.StringHtmlUtils;
import com.demo.music.downloader.TargetOutputStreamContext.TargetType;
import com.demo.music.generator.M3UGenerator;
import com.demo.music.sdo.Album;
import com.demo.music.sdo.TagInfo;
import com.demo.music.sdo.Track;
import org.apache.commons.lang3.StringUtils;
import com.demo.music.downloader.Status.StatusType;

public class AlbumDownloader {
    private Album album;
    private TargetOutputStreamContext targetOutputStreamContext;

    private HttpDownloader httpDownloader;

    public AlbumDownloader(Album album, TargetOutputStreamContext targetContext, HttpDownloader httpDownloader) {
        this.album = album;
        this.targetOutputStreamContext = targetContext;
        this.httpDownloader = httpDownloader;
    }

    public void downloadAlbum(DownloadCallback downloadCallback) throws IllegalStateException, IOException {

        //image album
        String artistName = album.getArtist();
        if (artistName == null || artistName.isEmpty()) {
            artistName = "album";
        }

        Status status = new Status();
        status.setStatusType(StatusType.DOWNLOAD_IMAGE);
        downloadCallback.updateStatus(status);

        //download album image
        String imageFileName = artistName + FileUtils.getExt(album.getImageLink(), "jpg");
        if (StringUtils.isNotEmpty(album.getImageLink()) && !targetOutputStreamContext.existed(imageFileName)) {
            OutputStream imgOs = targetOutputStreamContext.createOutputStream(imageFileName);
            httpDownloader.download(album.getImageLink(), imgOs);
            imgOs.close();
        }

        //Create note in folder
        String noteFileName = "note.txt";
        if (!targetOutputStreamContext.existed(noteFileName)) {
            OutputStream note = targetOutputStreamContext.createOutputStream(noteFileName);
            String noteContent = "Reference link:" + album.getRefLink();
            note.write(noteContent.getBytes());
            note.close();
        }

        //Download music file and tag audio information
        status.setStatusType(StatusType.DOWNLOAD_SOUNDTRACK);
        status.setTotalTrack(album.getTracks().size());
        downloadCallback.updateStatus(status);
        for (Track track : album.getTracks()) {
            String musicFileName = StringHtmlUtils.unAccent(track.getTitle()) + FileUtils.getExt(track.getLocation(), "mp3");
            if (targetOutputStreamContext.existed(musicFileName)) {
                System.out.println(String.format("[%s] - Target file has been existed, do not download again.", musicFileName));
                continue;
            }
            OutputStream mp3os = targetOutputStreamContext.createOutputStream(musicFileName);
            httpDownloader.download(track.getLocation(), mp3os);

            String artist = StringUtils.isNotEmpty(track.getCreator()) ? track.getCreator() : album.getArtist();
            TagInfo tag = new TagInfo(track.getId(), artist, album.getName(), track.getTitle());
            tag.setTotalTrack(album.getTracks().size());
            System.out.println(String.format("Tag file %s=[%s]\n", musicFileName, tag));

            if (targetOutputStreamContext.getTargetType() == TargetType.FILE_SYSTEM) {
                AudioTagger.tag(targetOutputStreamContext.getFileSystem(musicFileName), tag);
            }

            status.setCurrentTrack(track.getId());
            downloadCallback.updateStatus(status);
        }

        //Generate m3u playlist
        System.out.println("Generate playlist:" + album.getName() + ".m3u");
        OutputStream m3u = targetOutputStreamContext.createOutputStream(StringHtmlUtils.unAccent(album.getName()) + ".m3u");
        M3UGenerator.generateM3U(album, m3u);
        m3u.close();


        System.out.println("Done............................................");
        status.setStatusType(StatusType.FINISH);
        downloadCallback.updateStatus(status);
    }

}
