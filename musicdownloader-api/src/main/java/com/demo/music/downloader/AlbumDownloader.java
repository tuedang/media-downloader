package com.demo.music.downloader;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;

import com.demo.parser.common.StringUtils;
import com.demo.music.downloader.TargetOutputStreamContext.TargetType;
import com.demo.music.downloader.httpdownloader.SimpleHttpDownloader;
import com.demo.music.generator.M3UGenerator;
import com.demo.music.generator.Mp3TaggerJid;
import com.demo.music.sdo.Album;
import com.demo.music.sdo.TagInfo;
import com.demo.music.sdo.Track;

public class AlbumDownloader{
    private Album album;
    private TargetOutputStreamContext targetOutputStreamContext;

    public AlbumDownloader(Album album, TargetOutputStreamContext targetContext) {
        this.album = album;
        this.targetOutputStreamContext = targetContext;
    }
    private HttpDownloader httpDownloader = new SimpleHttpDownloader();

    public void downloadAlbum(DownloadCallback downloadCallback) throws IllegalStateException, IOException{

        //image album
        String artistName = album.getArtist();
        if(artistName==null || artistName.isEmpty()) {
            artistName="album";
        }

        Status status = new Status();
        status.setStatusType(StatusType.DOWNLOAD_IMAGE);
        downloadCallback.updateStatus(status);

        String imageFileName = artistName+ getExt(album.getImageLink(), "jpg");
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(album.getImageLink()) && !targetOutputStreamContext.existed(imageFileName)) {
            OutputStream imgOs = targetOutputStreamContext.createOutputStream(imageFileName);
            httpDownloader.download(album.getImageLink(), imgOs);
            imgOs.close();
        }

        String noteFileName = "note.txt";
        if(!targetOutputStreamContext.existed(noteFileName)) {
            OutputStream note = targetOutputStreamContext.createOutputStream(noteFileName);
            String noteContent = "Reference link:"+album.getRefLink();
            note.write(noteContent.getBytes());
            note.close();
        }

        //mp3(s)
        status.setStatusType(StatusType.DOWNLOAD_SOUNDTRACK);
        status.setTotalTrack(album.getTracks().size());
        downloadCallback.updateStatus(status);
        for(Track track: album.getTracks()) {
            String mp3FileName = StringUtils.unAccent(track.getTitle())+getExt(track.getLocation(), "mp3");
            if(targetOutputStreamContext.existed(mp3FileName)) {
                System.out.println(String.format("[%s] - Target file has been existed, do not download again.", mp3FileName));
                continue;
            }
            OutputStream mp3os= targetOutputStreamContext.createOutputStream(mp3FileName);
            httpDownloader.download(track.getLocation(), mp3os);

            String artist = ! StringUtils.isEmpty(track.getCreator()) ? track.getCreator(): album.getArtist();
            TagInfo tag = new TagInfo(track.getId(), artist, album.getName(), track.getTitle());
            tag.setTotalTrack(album.getTracks().size());
            System.out.println(String.format("Tag file %s=[%s]\n", mp3FileName, tag));

            if(targetOutputStreamContext.getTargetType()==TargetType.FILE_SYSTEM) {
                Mp3TaggerJid.tag(targetOutputStreamContext.getFileSystem(mp3FileName), tag);
            }

            status.setCurrentTrack(track.getId());
            downloadCallback.updateStatus(status);
        }

        System.out.println("Generate playlist:"+album.getName()+".m3u");
        OutputStream m3u = targetOutputStreamContext.createOutputStream(StringUtils.unAccent(album.getName()) +".m3u");
        M3UGenerator.generateM3U(album, m3u);
        m3u.close();


        System.out.println("Done............................................");
        status.setStatusType(StatusType.FINISH);
        downloadCallback.updateStatus(status);
    }

    private String getExt(String url, String defaultValue) {
        String ext = FilenameUtils.getExtension(url);
        if (ext == null || ext.isEmpty()) {
            ext=defaultValue;
        }
        return "." + ext;
    }

}
