package com.izhoujie.jsoupSpider.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.izhoujie.jsoupSpider.header.UserAgent;

/**
 * @author admin@izhoujie.com
 * 
 *         2016年9月12日 15:54:59
 * 
 *         web请求
 */
public class WebUtil {
    public static String executeGet(String url) throws Exception {

	BufferedReader in = null;
	String content = null;

	try {
	    // 定义HttpClient
	    HttpClient client = new HttpClient();
	    // 实例化HTTP方法
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
		    in.close();// 最后要关闭BufferedReader
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
}
