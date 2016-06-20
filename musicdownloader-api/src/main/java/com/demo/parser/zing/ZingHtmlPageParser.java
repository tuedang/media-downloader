package com.demo.parser.zing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;

import com.demo.parser.common.HtmlParser;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class ZingHtmlPageParser {
    private static final String HOME_PAGE="http://mp3.zing.vn";
    private String zingUrl;
    private HtmlPage htmlPage;


    public ZingHtmlPageParser(String zingUrl) {
        try {
            this.zingUrl = zingUrl;
            htmlPage = HtmlParser.parse(this.zingUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String parsePlaylistLink() {
        String pageAsText=htmlPage.asXml();
        int index=pageAsText.indexOf("http://mp3.zing.vn/xml/album-xml/");
        if(index!=-1) {
            int indexTill = pageAsText.indexOf("&", index);
            return pageAsText.substring(index, indexTill);
        }
        return null;

    }

    public String parseAlbumLink() {
        List<?> nodes = htmlPage.getByXPath("//span[@class='album-detail-img']");
        if (nodes != null && nodes.size() > 0) {
            HtmlSpan span = (HtmlSpan)nodes.get(0);

            DomNode domNode= span.getFirstChild();
            Node node = domNode.getAttributes().getNamedItem("src");
            return node.getTextContent();
        }
        return null;
    }
    public String parseAlbumName() {
        HtmlHeading1 h1 = (HtmlHeading1) htmlPage.getByXPath("//h1[@class='detail-title']").get(0);
        return getPart(h1.asText(), "-", 0);
    }

    public String parseArtist() {
        HtmlHeading1 h1 = (HtmlHeading1) htmlPage.getByXPath("//h1[@class='detail-title']").get(0);
        return getPart(h1.asText(), "-", 1);
    }
    private String getPart(String s, String separator, int index) {
        String[] rets = s.split(separator);
        if(rets.length>index) {
            return s.split(separator)[index].trim();
        }
        return null;
    }

    public List<String> parseDiscographyLink() {
        List<String> links = new ArrayList<String>();
        for(int i=1; i<4; i++) {
            String req = zingUrl+"&p="+i;
            List<String> subLinks = parseDiscographyLinkInPage(req);
            if(subLinks.isEmpty()) {
                break;
            }
            links.addAll(subLinks);
        }

        Set<String> setUrls = new LinkedHashSet<String>(links);
        return new ArrayList<String>(setUrls);
    }

    private List<String> parseDiscographyLinkInPage(String url) {
        HtmlPage htmlPage=null;
        try {
            htmlPage = HtmlParser.parse(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> links = new ArrayList<String>();
        if(htmlPage.getByXPath("//div[@class='content-block']").isEmpty()) {
            return links;
        }

        HtmlDivision div = (HtmlDivision) htmlPage.getByXPath("//div[@class='content-block']").get(0);
        Iterator<DomNode> lis = div.getChildren().iterator();
        lis.next();//ignore resultStats

        while(lis.hasNext()) {
            DomNode domNode = lis.next();
            if(domNode instanceof HtmlDivision) {
                Node n =domNode.getFirstChild().getAttributes().getNamedItem("href");
                links.add(HOME_PAGE +n.getTextContent());
            }
        }

        Set<String> setUrls = new LinkedHashSet<String>(links);
        return new ArrayList<String>(setUrls);
    }


}
