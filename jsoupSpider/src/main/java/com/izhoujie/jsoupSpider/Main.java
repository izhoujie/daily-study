package com.izhoujie.jsoupSpider;

import java.util.ArrayList;
import java.util.List;

import com.izhoujie.jsoupSpider.entity.Topic;
import com.izhoujie.jsoupSpider.entity.TopicItem;
import com.izhoujie.jsoupSpider.export.Export;
import com.izhoujie.jsoupSpider.spider.BaiduSpider;
import com.izhoujie.jsoupSpider.spider.Spider;
import com.izhoujie.jsoupSpider.util.ConfigReader;

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

	StringBuffer buffer1 = new StringBuffer();
	buffer1.append("帖子链接,帖子标题,帖主\n");
	for (Topic topic : allTopics) {
	    buffer1.append(topic.getUrl()).append(",");
	    buffer1.append(topic.getTitle().replaceAll(",", "，")).append(",");
	    buffer1.append(topic.getAuther()).append(",\n");
	}

	String path = ConfigReader.get("savePath");
	String name = tieba + "_themes.csv";
	Export.saveDatasToFile(path, name, buffer1.toString());

	String test = baiduEnSpider.topicPage(allTopics.get(2).getUrl(), 1);
	Export.saveDatasToFile(path, "test.html", test);
	System.exit(0);
	List<TopicItem> allItems = new ArrayList<TopicItem>();
	for (Topic topic : allTopics) {
	    StringBuffer buffer2 = new StringBuffer();
	    String url = topic.getUrl();
	    String topicHtml = "";
	    // 获取到的最大页数
	    int maxPage = 3;
	    int i = 1;
	    buffer2.append("帖子标题,贴主,帖子链接\n");
	    buffer2.append(topic.getTitle()).append(",");
	    buffer2.append(topic.getAuther().replaceAll(",", "，")).append(",");
	    buffer2.append(topic.getUrl()).append(",\n\n");

	    buffer2.append("回帖者,回帖内容\n");
	    do {
		topicHtml = baiduEnSpider.topicPage(url, i);
		Export.saveDatasToFile(path, "test.html", topicHtml);
		System.exit(0);
		List<TopicItem> items = baiduEnSpider.parseTopicItems(topicHtml);
		allItems.addAll(items);
	    } while (baiduEnSpider.topicHasNext(topicHtml) && i++ < maxPage);

	    for (TopicItem topicItem : allItems) {
		buffer2.append(topicItem.getAuther()).append(",");
		buffer2.append(topicItem.getContent().replaceAll(",", "，")).append(",\n");
	    }
	    Export.saveDatasToFile(path, topic.getTitle() + ".csv", buffer2.toString());
	}
    }
}
