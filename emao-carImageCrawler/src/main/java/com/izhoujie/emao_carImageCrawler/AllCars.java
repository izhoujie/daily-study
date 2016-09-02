package com.izhoujie.emao_carImageCrawler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author admin@izhoujie.com
 * 
 *         --多线程爬取emao.com的车库图片
 * 
 *         --未整理
 *
 */
public class AllCars {
    public static void main(String[] args) throws Exception {
	// 图库页面
	Parser parser = new Parser("http://auto.emao.com/pic/");
	// 滤取class=subBrand的标签，每个标签就是一个车
	HasAttributeFilter filter1 = new HasAttributeFilter("class", "mainBrand ");

	// 拿到所有车的list
	NodeList htmlCars = parser.parse(filter1);
	System.out.println(htmlCars.size());
	System.exit(0);
	// 逐个解析
	for (int i = 0; i < htmlCars.size(); i++) {
	    Node node = htmlCars.elementAt(i);
	    // 获取车的铭牌
	    String brand = node.getChildren().elementAt(1).getText();
	    // 获取车的跳转页面link
	    String brandUrl = node.toHtml().split("\"")[1];
	    // System.out.println(brand);
	    // System.out.println(brandUrl);

	    // 加host头后解析当前铭牌车的简略图link
	    Parser parser2 = new Parser("http://auto.emao.com" + brandUrl);
	    // 滤取 class=pic-min取其一即可，以解析获取指向该铭牌车的全图库link
	    HasAttributeFilter filter2 = new HasAttributeFilter("class", "pic-min");

	    NodeList list2 = parser2.parse(filter2);
	    // 有的车无图片
	    if (list2.size() == 0) {
		continue;
	    }
	    // 解析获得全图库link
	    String toCarImgPage = list2.elementAt(0).toHtml().split("\"")[5];

	    // 启动一个线程抓取该铭牌车的全图库
	    new BrandThread(brand, toCarImgPage).start();
	}
    }

    // 测试用
    public static void writeResult(String str) {

	FileWriter writer = null;
	try {
	    writer = new FileWriter("emao/result.txt", true);
	    writer.write(str);
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (writer != null) {
		try {
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }
}

// 启动抓取一个铭牌车全图库的线程
class BrandThread extends Thread {

    // 保存路径
    private String savePath;
    // 图库link
    private String brandImgPage;
    // 铭牌标识
    private String brand;

    public BrandThread(String brand, String brandUrl) {
	this.brand = brand;
	this.brandImgPage = brandUrl;
	this.savePath = "emao/" + brand + "/";
    }

    // 需要使用htmlunit以获取js渲染后的页面内容
    public void run() {
	// 获得一个模拟浏览器对象
	WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
	// 关闭对CSS的解析
	webClient.getOptions().setCssEnabled(false);
	// 关闭对script解析异常的抛出
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	HtmlPage page = null;
	try {
	    // 获取经过js渲染后的html
	    page = (HtmlPage) webClient.getPage(brandImgPage);
	} catch (Exception e) {
	    System.out.println("出现异常，此处无影响，忽略！");
	}
	// 获得html文本
	String asXml = page.asXml();
	Parser parser3 = null;
	try {
	    parser3 = new Parser(asXml);
	} catch (ParserException e) {
	    e.printStackTrace();
	}
	// 滤取有bigsrc属性的标签，每个标签含有一张图片link
	HasAttributeFilter filter3 = new HasAttributeFilter("bigsrc");
	NodeList list3 = null;
	try {
	    list3 = parser3.parse(filter3);
	} catch (ParserException e) {
	    e.printStackTrace();
	}

	// 创建当前车铭牌的子路径
	new File("emao/" + brand).mkdirs();
	// 图片解析
	for (int i = 0; i < list3.size(); i++) {
	    String imghtml = list3.elementAt(i).toHtml();
	    // 分别定位bigsrc、title、id属性的位置
	    int index1 = imghtml.indexOf("bigsrc");
	    int index2 = imghtml.indexOf("title");
	    int index3 = imghtml.lastIndexOf("id");
	    // 分割获取对应的值
	    String bigsrc = imghtml.substring(index1).split("\"")[1];
	    String title = imghtml.substring(index2).split("\"")[1];
	    // 有的title有/字符，需要替换掉，避免后面出现无法找到文件路径的异常
	    title = title.replace("/", "-");
	    String id = imghtml.substring(index3).split("\"")[1];
	    String imgUrl = bigsrc.substring(0, bigsrc.lastIndexOf("/"));

	    // 启动一个线程下载一个图片，参数：图片路径名称，imgUrl
	    new SaveImg(savePath + "/" + title + "_" + id + ".jpg", imgUrl).start();
	}
    }
}

class SaveImg extends Thread {

    // 保存路径
    private String savePath;
    // imgUrl
    private String imgUrl;

    public SaveImg(String savePath, String imgUrl) {
	this.savePath = savePath;
	this.imgUrl = imgUrl;
    }

    public void run() {
	// 以get方式获取即可
	HttpClient client = new HttpClient();
	GetMethod getMethod = new GetMethod(imgUrl);

	try {
	    client.executeMethod(getMethod);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	InputStream is = null;
	try {
	    // 获得响应的字节流
	    is = getMethod.getResponseBodyAsStream();
	    BufferedInputStream bis = new BufferedInputStream(is);
	    // 创建文件以及后续写保存文件
	    File f = new File(savePath);

	    OutputStream os = new FileOutputStream(f);
	    BufferedOutputStream bos = new BufferedOutputStream(os);
	    byte[] buffer = new byte[1024];
	    int readLen = 0;
	    while ((readLen = bis.read(buffer)) != -1) {
		bos.write(buffer, 0, readLen);
	    }
	    bos.flush();
	    // 关闭流
	    bis.close();
	    bos.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
