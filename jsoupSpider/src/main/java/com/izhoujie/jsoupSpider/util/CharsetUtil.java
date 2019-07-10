package com.izhoujie.jsoupSpider.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CharsetUtil {

    public static String urlEncode(String source) {

	try {
	    return URLEncoder.encode(source, "gb2312");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static void main(String[] args) {
	String str = "李毅吧";
	System.out.println(urlEncode(str));
    }
}
