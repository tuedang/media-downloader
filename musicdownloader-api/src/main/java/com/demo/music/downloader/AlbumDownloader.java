package com.demo.music.downloader;

import com.demo.music.downloader.Status.StatusType;
import com.demo.music.downloader.TargetOutputContext.TargetType;
import com.demo.music.generator.AudioTagger;
import com.demo.music.generator.M3UGenerator;
import com.demo.music.sdo.Album;
import com.demo.music.sdo.TagInfo;
import com.demo.music.sdo.Track;
import com.demo.parser.common.FileUtils;
import com.demo.parser.common.StringHtmlUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;

public class AlbumDownloader {
    private Album album;
    private TargetOutputContext targetOutputContext;

    private HttpDownloader httpDownloader;

    public AlbumDownloader(Album album, TargetOutputContext targetContext, HttpDownloader httpDownloader) {
        this.album = album;
        this.targetOutputContext = targetContext;
        this.httpDownloader = httpDownloader;
    }

    public void downloadAlbum(DownloadCallback downloadCallback) throws IllegalStateException, IOException {

        //image album
        String artistName = album.getArtist();
        if (artistName == null || artistName.isEmpty()) {
            artistName = "album";
        }

        Status status = new Status();
        downloadCallback.updateStatus(status.track(0).statusType(StatusType.DOWNLOAD_IMAGE).comment(album.getImageLink()));

        //download album image
        String imageFileName = artistName + FileUtils.getExt(album.getImageLink(), "jpg");
        if (StringUtils.isNotEmpty(album.getImageLink()) && !targetOutputContext.existed(imageFileName)) {
            downloadCallback.updateStatus(new Status().track(0).statusType(StatusType.DOWNLOAD_IMAGE).comment("Download image:" + imageFileName));
            httpDownloader.download(album.getImageLink(), targetOutputContext.getFileSystem(imageFileName));
        }

        //Create note in folder
        String noteFileName = "note.txt";
        if (!targetOutputContext.existed(noteFileName)) {
            String noteContent = "Reference link:" + album.getRefLink();
            org.apache.commons.io.FileUtils.writeStringToFile(targetOutputContext.getFileSystem(noteFileName), noteContent, "UTF-8");
        }


        //Download music file and tag audio information
        status.setTotalTrack(album.getTracks().size());
        Collections.sort(album.getTracks(), (o1, o2) -> o1.getId() - o2.getId());
        for (Track track : album.getTracks()) {
            String musicFileName = StringHtmlUtils.unAccent(track.getTitle()) + FileUtils.getExt(track.getLocation(), "mp3");
            if (targetOutputContext.existed(musicFileName)) {
                downloadCallback.updateStatus(status
                        .track(track.getId())
                        .statusType(StatusType.DOWNLOAD_SOUNDTRACK)
                        .comment(String.format("[%s] - Target file has been existed, do not download again.", musicFileName)));
                continue;
            }
            downloadCallback.updateStatus(status.track(track.getId()).statusType(StatusType.DOWNLOAD_SOUNDTRACK)
                    .comment(String.format("Downloading: [%s] %s", track.getTitle(), FilenameUtils.getName(track.getLocation()))));
            httpDownloader.download(track.getLocation(), targetOutputContext.getFileSystem(musicFileName));

            String artist = StringUtils.isNotEmpty(track.getCreator()) ? track.getCreator() : album.getArtist();
            TagInfo tag = new TagInfo(track.getId(), artist, album.getName(), track.getTitle(), album.getTracks().size());
            downloadCallback.updateStatus(status.comment(String.format("Tag file %s=[%s]\n", musicFileName, tag)));

            if (targetOutputContext.getTargetType() == TargetType.FILE_SYSTEM) {
                AudioTagger.tag(targetOutputContext.getFileSystem(musicFileName), tag);
            }

            status.setCurrentTrack(track.getId());
            downloadCallback.updateStatus(status);
        }

        //Generate m3u playlist
        downloadCallback.updateStatus(status.comment("Generate playlist:" + album.getName() + ".m3u"));
        M3UGenerator.generateM3U(album, targetOutputContext.getFileSystem(StringHtmlUtils.unAccent(album.getName()) + ".m3u"));

        downloadCallback.updateStatus(status.track(0).statusType(StatusType.FINISH).comment("Done..."+ targetOutputContext.getBasePath()));
    }

}
