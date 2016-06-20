package com.demo.parser.common;

import java.net.URI;

public class EncodeUrl {

    public static String encode(String src) {
		return URI.create(src).toASCIIString();
    }

}
