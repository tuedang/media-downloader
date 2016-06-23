package com.demo.parser.common;

import com.google.common.io.Resources;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HtmlPageContent {
    public enum ContentType {
        HTML, XML
    }
    private String content;
    private Document document;

    private HtmlPageContent() {
    }

    public HtmlPageContent(String content, ContentType contentType) {
        this.content = content;
        if (contentType != null && contentType == ContentType.XML) {
            this.document = Jsoup.parse(content, "", Parser.xmlParser());
        } else {
            this.document = Jsoup.parse(content);
        }
    }

    public HtmlPageContent(String content) {
        this(content, ContentType.HTML);
    }

    public static HtmlPageContent fromURL(URL url, ContentType contentType) {
        String text;
        try {
            if ("file".equals(url.getProtocol())) {
                text = Resources.toString(url, StandardCharsets.UTF_8);
            } else {
                Connection connection = Jsoup.connect(url.toString());
                text = connection.execute().body();
            }
            return new HtmlPageContent(text, contentType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Document getJsoupDocument() {
        return document;
    }

    public String cssSelector(String cssQuery) {
        return document.select(cssQuery).text();
    }

    public String searchText(String cssQuery, String searchText, int skip, String untilText) {
        String textElement = document.select(cssQuery).html();
        int indexSearchStart = textElement.indexOf(searchText);
        int indexStartValue = indexSearchStart + searchText.length() + skip;
        int indexSearchEnd = textElement.indexOf(untilText, indexStartValue);
        return textElement.substring(indexStartValue, indexSearchEnd);
    }
}
