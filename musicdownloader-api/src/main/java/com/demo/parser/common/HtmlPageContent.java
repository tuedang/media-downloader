package com.demo.parser.common;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HtmlPageContent {
    public enum ContentType {
        HTML, XML, HTML_JS
    }
    private String content;
    private Document document;

    public HtmlPageContent(String content, ContentType contentType) {
        this.content = content;
        if (contentType != null && contentType == ContentType.XML) {
            this.document = Jsoup.parse(content, "", Parser.xmlParser());
        } else {
            this.document = Jsoup.parse(content);
        }
    }

    public static HtmlPageContent fromURL(URL url, ContentType contentType) {
        String text;
        try {
            if ("file".equals(url.getProtocol())) {
                text = IOUtils.toString(url, StandardCharsets.UTF_8);
            } else {
                if (contentType == ContentType.HTML || contentType == ContentType.XML) {
                    text = Jsoup.connect(url.toString()).execute().body();
                } else if(contentType == ContentType.HTML_JS){
                    text = parse(url, false);
                } else {
                    throw new IllegalArgumentException("Not accept file with HtmlJS engine");
                }
            }
            return new HtmlPageContent(text, contentType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String parse(URL url, boolean jsEnable) throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.setActiveXObjectMap(null);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(jsEnable);

        HtmlPage htmlPage = webClient.getPage(url);
        webClient.close();
        return htmlPage.asXml();
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
