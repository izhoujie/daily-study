package com.izhoujie.jsoupSpider;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Base64.Decoder;

import org.apache.commons.lang3.CharSetUtils;
import org.apache.http.util.CharsetUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.izhoujie.jsoupSpider.util.CharsetUtil;
import com.izhoujie.jsoupSpider.util.ConfigReader;
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

	// 获取总页数
	String html = WebUtil.webGet("http://tieba.baidu.com/f?kw=lego");
	Document parse = Jsoup.parse(html);

	Elements elements = parse.select("a[href~=.*&pn=\\d{2,}].pagination-item");

	Elements elements2 = parse.select("a.last");

	System.out.println(elements2.size());
	System.out.println("------------");

	System.out.println(elements.size());
	int num = 0;

	for (Element element : elements) {
	    String string = element.attr("href");
	    String i = string.split("=")[3];
	    int j = Integer.parseInt(i);
	    if (num < j) {
		num = j;
	    }
	    System.out.println(i);

	    System.out.println(string);
	}
	System.out.println(num);

	String string = ConfigReader.get("savePath");
	System.out.println(string);

	String s = "李毅吧";

	// String ss = CharsetUtil.toGb2312(s);
	// System.out.println(ss);

	System.out.println(System.getProperty("file.encoding"));
	System.out.println(new String(s.getBytes("gb2312"), "gb2312"));
    }
}
