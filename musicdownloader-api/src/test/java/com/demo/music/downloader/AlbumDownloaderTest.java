package com.demo.music.downloader;

import java.io.IOException;
import java.net.URL;

import com.demo.music.downloader.httpdownloader.SimpleHttpDownloader;
import com.demo.music.downloader.httpdownloader.WgetHttpDownloader;
import com.demo.parser.CsnParser;
import com.demo.parser.NhacvuiParser;
import com.demo.parser.ZingParser;
import org.junit.Test;

import com.demo.parser.common.StringHtmlUtils;
import com.demo.music.sdo.Album;
import com.demo.parser.NctParser;

public class AlbumDownloaderTest {

    @Test
    public void downloadAlbumNCT() throws IllegalStateException, IOException {
        String url = "http://www.nhaccuatui.com/playlist/tuyen-tap-nhung-ca-khuc-rock-quoc-te-hay-adam-lambert.xQBpvCYhDpjq.html";
        Album album = new NctParser().getAlbum(new URL(url));

        AlbumDownloader albumDownloader = new AlbumDownloader(album, new TargetOutputContext("/Data/NCT/"+ StringHtmlUtils.trimCommonFileName(album.getName())), new SimpleHttpDownloader());
        albumDownloader.downloadAlbum(new DownloadCallback() {
            public void updateStatus(Status status) {
                System.out.println(status.getStatusType());
            }
        });
    }

    @Test
    public void downloadAlbumZing() throws IllegalStateException, IOException {
        String url = "http://mp3.zing.vn/album/Tim-Lai-Bau-Troi-Tuan-Hung/ZWZ9E89F.html";
        Album album = new ZingParser().getAlbum(new URL(url));

        AlbumDownloader albumDownloader = new AlbumDownloader(album, new TargetOutputContext("/Data/NCT/"+ StringHtmlUtils.trimCommonFileName(album.getName())), new WgetHttpDownloader());
        albumDownloader.downloadAlbum((status) -> {
            System.out.println(status);
        });
    }

    @Test
    public void downloadAlbumNhacvui() throws IllegalStateException, IOException {
        String url = "http://nhac.vui.vn/album-anh-cu-di-di-single-hari-won-a55076p30510.html";
        Album album = new NhacvuiParser().getAlbum(new URL(url));

        AlbumDownloader albumDownloader = new AlbumDownloader(album, new TargetOutputContext("/Data/NCT/"+ StringHtmlUtils.trimCommonFileName(album.getName())), new SimpleHttpDownloader());
        albumDownloader.downloadAlbum(status -> System.out.println(status.getStatusType()));
    }

    @Test
    public void downloadAlbumCsn() throws IOException {
        String url = "http://chiasenhac.vn/nghe-album/invocation-part-3~medwyn-goodall~1382118.html";
        Album album = new CsnParser().getAlbum(new URL(url));

        AlbumDownloader albumDownloader = new AlbumDownloader(album, new TargetOutputContext("/Data/NCT/"+ StringHtmlUtils.trimCommonFileName(album.getName())), new WgetHttpDownloader());
        albumDownloader.downloadAlbum(status -> System.out.println(status.getStatusType()));
    }
}
