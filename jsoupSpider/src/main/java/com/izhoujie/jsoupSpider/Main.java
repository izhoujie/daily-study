package com.izhoujie.jsoupSpider;

import java.util.ArrayList;
import java.util.List;

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
	int pages = 1;
	Spider baiduEnSpider = new BaiduSpider(tieba);

	List<Topic> allTopics = new ArrayList<Topic>();

	for (int i = 0; i < pages; i++) {
	    String html = baiduEnSpider.listPage(0);
	    List<Topic> topics = baiduEnSpider.parseListPage(html);
	    allTopics.addAll(topics);
	    // 判断是否还有下一页
	    if (!baiduEnSpider.listHasNext(html)) {
		break;
	    }
	}
	System.out.println("topics:" + allTopics.size());

	for (Topic topic : allTopics) {
	    String url = topic.getUrl();
	    String topicHtml = "";
	    // 获取到的最大页数
	    int maxPage = 20;
	    int i = 1;
	    do {
		topicHtml = baiduEnSpider.topicPage(url, i);
		List<TopicItem> items = baiduEnSpider.parseTopicItems(topicHtml);
	    } while (baiduEnSpider.topicHasNext(topicHtml) && i++ < maxPage);
	}
	System.exit(0);

	Topic topic2 = allTopics.get(0);
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
