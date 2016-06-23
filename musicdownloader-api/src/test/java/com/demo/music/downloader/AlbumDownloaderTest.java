package com.demo.music.downloader;

import java.io.IOException;

import org.junit.Test;

import com.demo.parser.common.StringUtils;
import com.demo.music.downloader.TargetOutputStreamContext.TargetType;
import com.demo.music.sdo.Album;
import com.demo.parser.nct.NctParser;

public class AlbumDownloaderTest {

    @Test
    public void downloadAlbum() throws IllegalStateException, IOException {
        String url = "http://www.nhaccuatui.com/playlist/tuyen-tap-nhung-ca-khuc-rock-quoc-te-hay-adam-lambert.xQBpvCYhDpjq.html";
        Album album = new NctParser().getAlbum(url);

        AlbumDownloader albumDownloader = new AlbumDownloader(album, new TargetOutputStreamContext("/Volumes/Data/NCT/"+StringUtils.trimCommonFileName(album.getName()), TargetType.FILE_SYSTEM));
        albumDownloader.downloadAlbum(new DownloadCallback() {
            public void updateStatus(Status status) {
                System.out.println(status.getStatusType());
            }
        });

    }

}
