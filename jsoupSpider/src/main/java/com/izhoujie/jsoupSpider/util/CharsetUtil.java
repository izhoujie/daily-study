package com.izhoujie.jsoupSpider.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CharsetUtil {

    public static String toGb2312(String source) {
	FileInputStream inputStream = null;
	InputStreamReader reader = null;
	StringBuffer buffer = new StringBuffer();
	try {
	    InputStream stream = new ByteArrayInputStream(source.getBytes());
	    reader = new InputStreamReader(stream, "gb2312");
	    char[] buf = new char[64];
	    int count = 0;
	    while ((count = reader.read(buf)) != -1) {
		buffer.append(buffer, 0, count);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (inputStream != null) {
		try {
		    inputStream.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	return buffer.toString();
    }

    public static void main(String[] args) {

    }
}
