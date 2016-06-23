package com.demo.parser.nct;

import com.demo.parser.common.HtmlParser;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Node;

import java.util.List;

public class NctHtmlPageParser {
    private static final String HOME_PAGE="http://www.nhaccuatui.com";
    private static final String PLAYLIST_LINK_PRE="http://www.nhaccuatui.com/flash/xml?key2=";
    private String nctUrl;
    private HtmlPage htmlPage;
    private Document document;


    public NctHtmlPageParser(String nctUrl) {
        this.nctUrl = nctUrl;
        try {
            htmlPage = HtmlParser.parse(nctUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        document = Jsoup.parse(htmlPage.asXml());
    }

    /**
     * playlist link
     * @return url link
     */
    public String parsePlaylistLink() {
        String pageAsText = document.select("div.playing_absolute").html();
        int jqueryIndexLoader = pageAsText.indexOf("NCTNowPlaying.intFlashPlayer");
        int jqueryIndexPlaylist = pageAsText.indexOf("playlist", jqueryIndexLoader) + 12;
        int jqueryLastIndexPlaylist = pageAsText.indexOf("\"", jqueryIndexPlaylist);
        String plId = pageAsText.substring(jqueryIndexPlaylist, jqueryLastIndexPlaylist);

        return PLAYLIST_LINK_PRE + plId;
    }

    /**
     * Get image album
     * @return url album
     */
    public String parseAlbumLink() {
        List<?> nodes = htmlPage.getByXPath("//div[@class='img-cover']");
        if (nodes != null && nodes.size() > 0) {
            HtmlDivision div = (HtmlDivision) nodes.get(0);

            DomNode domNode = div.getChildNodes().get(1);
            Node node = domNode.getAttributes().getNamedItem("src");
            return node.getTextContent();
        }
        return null;
    }
    /**
     * Get album name
     * @return String
     */
    public String parseAlbumName() {
        return document.select("div.name_title h1[itemprop='name']").text();
    }
    /**
     * get Artist of album
     * @return String
     */
    public String parseArtist() {
        return document.select("div.name_title .name-singer").text();
    }

}
