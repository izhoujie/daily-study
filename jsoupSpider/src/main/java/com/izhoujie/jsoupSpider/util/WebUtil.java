package com.izhoujie.jsoupSpider.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.izhoujie.jsoupSpider.header.UserAgent;

/**
 * @author admin@izhoujie.com
 * 
 *         2016年9月12日 15:54:59
 * 
 *         web请求
 */
public class WebUtil {

    /**
     * @param url
     * @return
     */
    public static String webGet(String url) {

	BufferedReader in = null;
	String content = null;

	try {
	    HttpClient client = new HttpClient();
	    // 实例化一个HTTP方法
	    GetMethod getMethod = new GetMethod(url);
	    // TODO: 根据配置设置合适的user-agent
	    getMethod.setRequestHeader("User-Agent", UserAgent.CHROME);

	    // 发起get请求
	    client.executeMethod(getMethod);
	    // 得到流响应体
	    InputStream stream = getMethod.getResponseBodyAsStream();

	    // 流包装
	    in = new BufferedReader(new InputStreamReader(stream));
	    StringBuffer sb = new StringBuffer("");
	    String line = "";
	    // 获取当前系统的换行符
	    String NL = System.getProperty("line.separator");
	    while ((line = in.readLine()) != null) {
		sb.append(line + NL);
	    }
	    in.close();
	    content = sb.toString();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (final Exception e) {
		    e.printStackTrace();
		}
	    }
	}
	if (!"".equals(content) && url.contains("http://tieba.baidu.com")) {
	    // 实际页面内容被<!-- --> 注释掉了，所以需要去掉注释符
	    return content.replaceAll("<!--", "").replaceAll("-->", "");
	}
	return content;
    }

    /**
     * @param url
     * @return
     */
    public static String webGet2(String url) {

	// 获得一个模拟浏览器对象
	WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
	// 关闭对CSS的解析
	webClient.getOptions().setCssEnabled(false);
	// 关闭对script解析异常的抛出
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	webClient.getOptions().setTimeout(10000);
	HtmlPage page = null;
	try {
	    // 获取经过js渲染后的html
	    page = (HtmlPage) webClient.getPage(url);
	} catch (Exception e) {
	    System.out.println("htmlunit 解析html页面异常");
	}
	// 获得html文本
	String html = page.asXml();

	return html;
    }
}
