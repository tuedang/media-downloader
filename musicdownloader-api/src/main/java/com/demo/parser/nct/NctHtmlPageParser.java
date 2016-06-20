package com.demo.parser.nct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Node;

import com.demo.parser.common.HtmlParser;

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
    public List<String> parseDiscographyLink() {
        if(htmlPage.getByXPath("//ul[@class='list_playlist']").isEmpty()) {
            return new ArrayList<String>();
        }

        List<String> links = new ArrayList<String>();

        HtmlUnorderedList ul = (HtmlUnorderedList) htmlPage.getByXPath("//ul[@class='list_playlist']").get(0);
        Iterator<DomNode> lis = ul.getChildren().iterator();
        while(lis.hasNext()) {
            DomNode domNode = lis.next();
            if(domNode instanceof HtmlListItem) {
                Node n =domNode.getFirstChild().getAttributes().getNamedItem("href");
                links.add(HOME_PAGE +n.getTextContent());
            }
        }

        //VIEW_MORE
        DomNode pTag = ul.getNextSibling();
        if(!(pTag instanceof HtmlParagraph)  || ! pTag.getAttributes().getNamedItem("class").getTextContent().equals("more")) {
            return links;
        }

        String linkViewMore = ul.getNextSibling().getChildren().iterator().next().getAttributes().getNamedItem("href").getTextContent();

        for(int i=1; i<10; i++) {
            String linkMoreRequest = HOME_PAGE+linkViewMore+"&page="+i;
            NctHtmlPageParser viewMoreParser = new NctHtmlPageParser(linkMoreRequest);
            List<String> subLinks = viewMoreParser.parseDiscographyLink();
            if(subLinks.isEmpty()) {
                break;
            }
            links.addAll(subLinks);
        }


        links.add(nctUrl);
        links.add(0, nctUrl);
        Set<String> setUrls = new LinkedHashSet<String>(links);
        return new ArrayList<String>(setUrls);
    }


}
