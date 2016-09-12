package com.izhoujie.jsoupSpider.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author admin@izhoujie.com
 * 
 *         2016年9月12日 16:05:44
 * 
 *         获取配置信息
 */
public class ConfigReader {
    private static Properties properties;

    public static String read(String key, String defaultValue) {
	if (properties == null) {
	    load();
	}

	return properties.getProperty(key, defaultValue);
    }

    public static String read(String key) {
	if (properties == null) {
	    load();
	}

	return properties.getProperty(key);
    }

    private static void load() {
	Properties properties = new Properties();
	try {
	    properties.load(ClassLoader.getSystemResourceAsStream("config.properties"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}