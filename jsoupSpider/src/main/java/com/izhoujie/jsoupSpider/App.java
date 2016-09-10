package com.izhoujie.jsoupSpider;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.izhoujie.jsoupSpider.util.WebUtil;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
	System.out.println("Hello World!");

	// connect方法...待研究
	Document doc = Jsoup.connect("http://tieba.baidu.com/f?kw=%C0%EE%D2%E3&fr=ala0&tpl=5").userAgent("Mozilla")
		.timeout(3000).get();

	System.out.println(doc.title());
	System.out.println(doc.text());
	
	
	String html = WebUtil.executeGet("http://tieba.baidu.com/f?kw=lego");
	Document parse = Jsoup.parse(html);
	
	
    }
}
