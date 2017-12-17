package api;

import com.demo.music.downloader.HttpDownloader;
import com.demo.music.downloader.httpdownloader.WgetHttpDownloader;
import com.demo.parser.common.HtmlPageContent;
import com.demo.parser.common.StringHtmlUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BlogspotDownloader {

    public List<Image> getAlbum(URL url) throws IOException {
        HtmlPageContent htmlPageContent = HtmlPageContent.fromURL(url, HtmlPageContent.ContentType.HTML);

        AtomicInteger trackIdInteger = new AtomicInteger(0);
        return htmlPageContent.getJsoupDocument()
                .select(".hentry img[src]")
                .stream()
                .map(e -> new Image(trackIdInteger.incrementAndGet(), e.attr("src")))
                .collect(Collectors.toList());

    }

    public static void main(String[] args) throws Exception {
        HttpDownloader httpDownloader = WgetHttpDownloader.getInstance(10);
        File out = new File("/Data/books/nguoimaysieudang");

        ClassLoader classLoader = BlogspotDownloader.class.getClassLoader();
        File file = new File(classLoader.getResource("Links.txt").getFile());

        List<String> links = FileUtils.readLines(file, Charset.defaultCharset()).stream()
                .filter(l -> !l.isEmpty())
                .collect(Collectors.toList());
        for (String link: links) {
            String chapter = StringHtmlUtils.trimCommonFileName(link.substring(7));
            System.out.println(chapter);



            File parent = new File(out, chapter);
            parent.mkdirs();

            List<Image> images = new BlogspotDownloader().getAlbum(new URL(link));
            for (Image image : images) {
                try {
                    httpDownloader.download(image.getLocation(), new File(parent, image.getTitle()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
