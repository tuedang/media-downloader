package com.demo.parser.common;

import java.io.IOException;
import java.net.MalformedURLException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlParser {
    public static HtmlPage parse(String url, boolean jsEnable) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.setActiveXObjectMap(null);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(jsEnable);

        HtmlPage htmlPage = webClient.getPage(EncodeUrl.encode(url));
        webClient.close();
        return htmlPage;
    }

    public static HtmlPage parse(String url) throws FailingHttpStatusCodeException, IOException {
        return parse(url, false);
    }

    public static String parseHtml(String url) throws FailingHttpStatusCodeException, IOException {
        HtmlPage htmlPage = parse(url, false);
        return htmlPage.asXml();
    }

    public static String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if(nl != null && nl.getLength() > 0) {
            Element el = (Element)nl.item(0);
            textVal = el.getFirstChild().getNodeValue().trim();
        }

        return textVal;
    }

}
