package com.izhoujie.emao_carImageCrawler;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Hello world!
 * 
 * -Test
 *
 */
public class App {

	private static String host = "http://auto.emao.com";

	public static void main(String[] args) throws Exception {
		// 获取HttpClient对象--客户端实例
		HttpClient client = new HttpClient();
		// 获取GetMethod对象 以get方式
		HttpMethod method = new GetMethod("http://auto.emao.com/pic/1/2/y2013/0-39220.html");
		// 进行url访问
		client.executeMethod(method);
		// 打印服务器返回状态
		System.out.println(method.getStatusLine());
		// 打印返回的信息
		String html = method.getResponseBodyAsString();
		method.releaseConnection();

		System.out.println(html);
		Parser parser = new Parser("http://auto.emao.com/pic/");
		System.out.println(parser.getURL());

		HasAttributeFilter filter = new HasAttributeFilter("class", "subBrand ");

		NodeList list = parser.parse(filter);

		System.out.println(list.size());

		// HashSet<String> brand = new HashSet<String>();
		// for (int i = 0; i < list.size(); i++) {
		// Node node = list.elementAt(i);
		//
		// NodeList node1 = node.getChildren();
		// String text2 = node1.elementAt(1).getText();
		// brand.add(text2);
		//
		// String text = node.toHtml();
		// System.out.println(node);
		// System.out.println(text);
		// System.out.println(text.split("\"")[1]);
		// System.out.println(text2);
		// }

		Node node = list.elementAt(0);
		String carName = node.getChildren().elementAt(1).getText();
		String car = node.toHtml().split("\"")[1];

		System.out.println(carName);
		Parser parser2 = new Parser(host + car);

		HasAttributeFilter filter2 = new HasAttributeFilter("class", "pic-min");

		NodeList list2 = parser2.parse(filter2);

		Node node2 = list2.elementAt(0);

		System.out.println(node2);
		System.out.println(node2.toHtml());
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
