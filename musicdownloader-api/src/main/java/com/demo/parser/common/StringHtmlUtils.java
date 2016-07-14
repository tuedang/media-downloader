package com.demo.parser.common;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringHtmlUtils {
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(temp).replaceAll("");
        result = result.replace((char) 273, 'd').replace((char) 272, 'D');
        result = result.replaceAll(":", "");
        result = trimCommonFileName(result);

        return result;
    }

    public static String trimCommonFileName(String fn) {
        fn = fn.trim();
        while (fn.startsWith(".")) {
            fn = fn.replaceFirst("\\.", "");
        }
        fn = fn.replaceAll("/", " ");
        fn = fn.replaceAll(":", "");
        fn = fn.replaceAll("&","").replaceAll("\\.","").trim();
        return fn;
    }

    public static String encode(String src) {
        return URI.create(src).toASCIIString();
    }

    public static String encodeParamUrl(String sUrl) {
        try {
            URL url = new URL(sUrl);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            return uri.toString();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
            return sUrl;
        }

    }
}
