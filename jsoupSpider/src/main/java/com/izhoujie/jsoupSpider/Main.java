package com.izhoujie.jsoupSpider;

import java.util.List;

import org.jsoup.helper.Validate;

import com.izhoujie.jsoupSpider.entity.Topic;
import com.izhoujie.jsoupSpider.entity.TopicItem;
import com.izhoujie.jsoupSpider.spider.BaiduSpider;
import com.izhoujie.jsoupSpider.spider.Spider;

/**
 * @author admin@izhoujie.com
 * 
 *         2016年9月12日 15:52:02
 * 
 *         功能测试
 */
public class Main {

    public static void main(String[] args) {

	// 从外部获取贴吧名字作为参数
	// Validate.isTrue(args.length == 1, "参数正常：" + args[0]);
	// 直接参数-测试用
	String tieba = "lego";
	Spider baiduEnSpider = new BaiduSpider(tieba);
	String listPageHtml = baiduEnSpider.listPage(0);

	List<Topic> topics = baiduEnSpider.parseListPage(listPageHtml);
	System.out.println("topics:" + topics.size());
	System.exit(0);
	for (Topic topic : topics) {
	    String topicHtml = baiduEnSpider.topicPage(topic.getUrl(), 0);
	    List<TopicItem> items = baiduEnSpider.parseTopicItems(topicHtml);
	    topic.setItems(items);
	    topic.setCreateTime(items.get(0).getTime());
	}
	Topic topic2 = topics.get(0);
	String user = topic2.getUser();
	String url = topic2.getUrl();
	String title = topic2.getTitle();
	String createTime = topic2.getCreateTime();
	List<TopicItem> items = topic2.getItems();

	System.out.println(user + "\n" + url + "\n" + title + "\n" + createTime + "\n\n");
	for (TopicItem topicItem : items) {
	    System.out.println(
		    topicItem.getAuther() + "\n" + topicItem.getContent() + "\n" + topicItem.getTime() + "\n\n");
	}

    }
}
