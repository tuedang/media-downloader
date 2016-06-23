package com.demo.parser.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HtmlPageContent {
    private String content;
    private Document document;

    private HtmlPageContent() {
    }

    public HtmlPageContent(String content) {
        this.content = content;
        this.document = Jsoup.parse(content);
    }

    public static HtmlPageContent fromUrl(String url) throws IOException {
        HtmlPage htmlPage = HtmlParser.parse(url);
        return new HtmlPageContent(htmlPage.asXml());
    }

    public static HtmlPageContent fromResource(String resource) throws IOException {
        URL url = Resources.getResource(resource);
        String text = Resources.toString(url, StandardCharsets.UTF_8);
        return new HtmlPageContent(text);
    }

    public static HtmlPageContent fromFile(File file) throws IOException {
        return new HtmlPageContent((FileUtils.readFileToString(file, Charset.defaultCharset())));
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
