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
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);
	    webClient.setCssEnabled(false);
	    webClient.setActiveXNative(false);
	    webClient.setActiveXObjectMap(null);
	    webClient.setRedirectEnabled(false);
	    webClient.setThrowExceptionOnFailingStatusCode(false);
	    webClient.setThrowExceptionOnScriptError(false);
	    webClient.setJavaScriptEnabled(false);
//	    ProxyConfig proxy = new ProxyConfig("66.35.68.146", 3128);
//	    webClient.setProxyConfig(proxy);
	    
	    HtmlPage htmlPage = webClient.getPage(EncodeUrl.encode(url));
	    webClient.closeAllWindows();
		return htmlPage;
	}

	public static HtmlPage parse(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		return parse(url, false);
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
