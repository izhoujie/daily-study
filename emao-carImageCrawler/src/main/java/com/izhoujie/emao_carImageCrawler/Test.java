package com.izhoujie.emao_carImageCrawler;

import java.net.StandardSocketOptions;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java_cup.runtime.Symbol;

/**
 * Hello world!
 * 
 * -Test
 *
 */
public class Test {

    private static String host = "http://auto.emao.com";

    public static void main(String[] args) throws Exception {
	// 获取HttpClient对象--客户端实例
	HttpClient client = new HttpClient();
	// 获取GetMethod对象 以get方式
	HttpMethod method = new GetMethod("http://auto.emao.com/pic/");
	// 进行url访问
	client.executeMethod(method);
	// 打印服务器返回状态
	System.out.println(method.getStatusLine());
	// 打印返回的信息
	String html = method.getResponseBodyAsString();
	method.releaseConnection();

	// System.out.println(html);
	Parser parser = new Parser("http://auto.emao.com/pic/");
	System.out.println(parser.getURL());

	HasAttributeFilter filter = new HasAttributeFilter("class", "mainBrand ");

	NodeList list = parser.parse(filter);

	System.out.println(list.size());

	// HashSet<String> brand = new HashSet<String>();
	// for (int i = 0; i < 3; i++) {
	// Node node = list.elementAt(i);
	//
	// NodeList node1 = node.getChildren();
	// String text2 = node1.elementAt(1).getText();
	// brand.add(text2);
	//
	// String text = node.toHtml();
	// System.out.println("p1:" + node);
	// System.out.println("p2:" + text);
	// System.out.println("p3:" + text.split("\"")[1]);
	// System.out.println("p4:" + text2);
	// }

	Node node = list.elementAt(0);
	String carName = node.getChildren().elementAt(1).getText();
	String car = node.toHtml().split("\"")[1];
	// 车铭牌
	System.out.println(carName);
	System.out.println(car);
	Parser parser2 = new Parser(host + car);

	HasAttributeFilter filter2 = new HasAttributeFilter("class", "subBrand ");

	NodeList list2 = parser2.parse(filter2);
	// 需要判断大小 若0则continue
	System.out.println(list2.size());
	Node node2 = list2.elementAt(0);
	// 具体某车型
	System.out.println(node2.toHtml());
	// 具体车型的跳转链接
	String subCar = node2.toHtml().split("\"")[1];
	System.out.println("车：" + subCar);
	// 具体车型名称
	String text = node2.getChildren().elementAt(1).getText();
	System.out.println(text);

	Parser parser4 = new Parser(host + subCar);
	// 滤取有图片的ul标签
	HasAttributeFilter filter5 = new HasAttributeFilter("class", "car-list-pic");
	// 获取下一页的link-若有
	AndFilter andFilter = new AndFilter(new HasAttributeFilter("href"), new HasAttributeFilter("rel", "next"));

	NodeList list5 = parser4.parse(filter5);
	parser4.reset();
	NodeList nodeList = parser4.parse(andFilter);

	System.out.println(list5.size());
	// 获得图片的ul
	// System.out.println(list5.elementAt(0));
	System.out.println(nodeList.size());
	Parser parser7 = new Parser(list5.elementAt(0).toHtml());
	TagNameFilter filter6 = new TagNameFilter("li");
	NodeList list4 = parser7.parse(filter6);
	System.out.println(list4.size());
	String html3 = list4.elementAt(0).toHtml();
	System.out.println(html3);
	System.out.println("测试");
	System.out.println(list4.elementAt(0).getChildren().elementAt(3).getText());
	// java正则解析
	Pattern compile1 = Pattern.compile("src=.*.jpg");
	Pattern compile2 = Pattern.compile("<span>.*</span>");
	Pattern compile3 = Pattern.compile("href=.*html");
	Matcher matcher1 = compile1.matcher(html3);
	Matcher matcher2 = compile2.matcher(html3);
	Matcher matcher3 = compile3.matcher(nodeList.elementAt(0).toHtml());
	if (matcher1.find()) {
	    System.out.println(matcher1.group(0).split("\"")[1]);
	}
	if (matcher2.find()) {
	    System.out.println(matcher2.group(0));
	}
	if (matcher3.find()) {
	    System.out.println(matcher3.group(0).split("\"")[1]);
	}
	// 一张图片的解析完毕，但页循环处理，处理完毕后检查是否有下一页link，若有则递归处理
	// 是否有下一页
	// System.out.println(nodeList.elementAt(0).toHtml());

	System.exit(0);

	String toCarImg = node2.toHtml().split("\"")[5];

	System.out.println("3--" + toCarImg);

	WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
	// webClient.setJavaScriptTimeout(6000);
	webClient.getOptions().setCssEnabled(false);
	HtmlPage page = (HtmlPage) webClient.getPage(toCarImg);
	String asXml = page.asXml();
	// System.out.println(asXml);

	Parser parser3 = new Parser(asXml);
	HasAttributeFilter filter3 = new HasAttributeFilter("bigsrc");
	NodeList list3 = parser3.parse(filter3);
	System.out.println(list3.size());
	Node node3 = list3.elementAt(1);
	System.out.println(node3.toHtml());
	String string = node3.toHtml().split("\"")[3];

	System.out.println(string.subSequence(0, string.lastIndexOf("/")));
    }
}
