package com.demo.parser.common;

import java.net.URI;
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
        return fn;
    }

    public static String encode(String src) {
        return URI.create(src).toASCIIString();
    }
}
