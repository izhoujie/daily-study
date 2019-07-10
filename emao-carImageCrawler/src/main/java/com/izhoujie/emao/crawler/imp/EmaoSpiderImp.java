package com.izhoujie.emao.crawler.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

import com.izhoujie.emao.car.Car;
import com.izhoujie.emao.crawler.EmaoSpider;

public class EmaoSpiderImp implements EmaoSpider {
    private static String host = "http://auto.emao.com";

    @Override
    public List<Car> getAllBrands(String url) {
	List<Car> carlist = new ArrayList<Car>();
	try {
	    Parser parser = new Parser(url);

	    HasAttributeFilter filter = new HasAttributeFilter("class", "mainBrand ");

	    NodeList list = parser.parse(filter);
	    if (list.size() == 0) {
		throw new Exception("获取所有车铭牌页面失败！");
	    }
	    for (int i = 0; i < list.size(); i++) {
		Node node = list.elementAt(0);
		String carBrand = node.getChildren().elementAt(1).getText();
		String carUrl = node.toHtml().split("\"")[1];
		carUrl = host + carUrl;
		Car car = new Car(carBrand, "", carUrl);
		carlist.add(car);
	    }

	} catch (Exception e) {
	    System.out.println("获取所有车铭牌页面失败！");
	}
	return carlist;
    }

    @Override
    public void getAllSubBrands(Car car) {
	String url = car.getUrl();
	try {

	    Parser parser = new Parser(url);
	    // 滤取有图片的ul标签
	    HasAttributeFilter filter = new HasAttributeFilter("class", "car-list-pic");
	    // 获取下一页的link-若有
	    AndFilter andFilter = new AndFilter(new HasAttributeFilter("href"), new HasAttributeFilter("rel", "next"));

	    NodeList list = parser.parse(filter);
	    parser.reset();
	    NodeList next = parser.parse(andFilter);
	    if (next.size() == 0) {
		car.setUrl(null);
	    } else {
		Pattern compile = Pattern.compile("href=.*html");
		Matcher matcher = compile.matcher(next.elementAt(0).toHtml());
		if (matcher.find()) {
		    car.setUrl(matcher.group(0).split("\"")[1]);
		}
	    }
	    if (list.size() == 0) {
		System.out.println("该车没有图片！");
		return;
	    }
	    Parser parser2 = new Parser(list.elementAt(0).toHtml());
	    TagNameFilter liFilter = new TagNameFilter("li");
	    NodeList list2 = parser2.parse(liFilter);
	    if (list2.size() == 0) {
		System.out.println("该车没有图片！");
		return;
	    }

	    for (int i = 0; i < list2.size(); i++) {
		String html = list2.elementAt(i).toHtml();
		// java正则解析
		Pattern compile1 = Pattern.compile("src=.*.jpg");
		Pattern compile2 = Pattern.compile("<span>.*</span>");
		Matcher matcher1 = compile1.matcher(html);
		Matcher matcher2 = compile2.matcher(html);
		String imagName = "";
		String path = "";
		if (matcher1.find()) {
		    String string = matcher1.group(0).split("\"")[1];

		}
		if (matcher2.find()) {
		    System.out.println(matcher2.group(0));
		}
		// 一张图片的解析完毕，但页循环处理，处理完毕后检查是否有下一页link，若有则递归处理
		// 是否有下一页
		// System.out.println(nodeList.elementAt(0).toHtml());
	    }
	} catch (Exception e) {

	}
    }
}
