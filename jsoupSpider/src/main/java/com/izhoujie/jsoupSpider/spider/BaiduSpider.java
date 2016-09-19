package com.izhoujie.jsoupSpider.spider;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.izhoujie.jsoupSpider.entity.Topic;
import com.izhoujie.jsoupSpider.entity.TopicItem;
import com.izhoujie.jsoupSpider.util.WebUtil;

/**
 * @author admin@izhoujie.com
 * 
 *         2016年9月12日 16:05:14
 * 
 *         jsoup中文API文档快速参考:http://www.open-open.com/jsoup/
 * 
 *         jsoup官方文档:https://jsoup.org/
 * 
 */
public class BaiduSpider implements Spider {

    // 贴吧url统一前缀
    private static String tiebaURL = "http://tieba.baidu.com/f?kw=";
    // 贴吧keyworld
    private String kw = "";
    // 目标贴吧url
    private String targetURL;

    public BaiduSpider(String kw) {
	this.kw = kw;
	this.targetURL = tiebaURL + kw;
    }

    public String getKw() {
	return kw;
    }

    public void setKw(String kw) {
	this.kw = kw;
    }

    public List<Topic> parseListPage(String html) {

	List<Topic> topics = new ArrayList<>();

	Document doc = Jsoup.parse(html);
	// 获取主题信息标签
	Elements els = doc.getElementsByAttributeValue("class", "threadlist_lz clearfix");
	for (int i = 0; i < els.size(); i++) {
	    Element el = els.get(i);
	    // 获取帖子title
	    Elements elements = el.getElementsByAttributeValue("class", "j_th_tit ");
	    Element aEl = null;
	    // 如果是强插的广告，则跳过，广告的标签内容不合规律
	    if (elements.size() > 0) {
		aEl = elements.get(0);
	    } else {
		continue;
	    }
	    String link = aEl.attr("href");
	    String title = aEl.attr("title");
	    // 获取贴主
	    Elements userEls = el.getElementsByClass("j_user_card");
	    String user = null;
	    if (userEls != null && !userEls.isEmpty()) {
		user = userEls.get(0).text();
	    }
	    // 封装帖子
	    topics.add(new Topic("http://tieba.baidu.com" + link, title, user));
	}

	return topics;
    }

    public List<TopicItem> parseTopicItems(String html) {
	List<TopicItem> items = new ArrayList<>();

	Document doc = Jsoup.parse(html);
	// Elements comments = doc.getElementsByClass("l_post_bright");
	Elements comments = doc.select(".l_post_bright[data-field]");
	for (int i = 0; i < comments.size(); i++) {
	    Element comment = comments.get(i);
	    System.out.println(comment.html());
	    System.exit(0);
	    Element auther = comment.getElementsByClass("p_author_name").get(0);
	    Element content = comment.getElementsByClass("p_content").get(0);
	    // 如何取得评论的时间- -||
	    // Elements spans = comment.getElementsByClass("core_reply_tail");
	    // System.out.println(spans.size());
	    // for (Element element : spans) {
	    //
	    // System.out.println(element.text());
	    // }
	    // System.exit(0);

	    TopicItem item = new TopicItem(auther.text(), content.text(), "");
	    items.add(item);
	}

	return items;
    }

    @Override
    public String listPage(int pageIndex) {
	String url = targetURL;
	if (pageIndex > -1) {
	    url += "&pn=" + (pageIndex * 50);
	}

	try {
	    return WebUtil.webGet(url);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    @Override
    public boolean listHasNext(String html) {
	Document doc = Jsoup.parse(html);
	return doc.select("a.last").size() > 0;
    }

    @Override
    public String topicPage(String url, int pageIndex) {
	String pageUrl = url;
	if (pageIndex > 0) {
	    pageUrl += "?pn=" + pageIndex;
	}

	try {
//	    return WebUtil.webGet2(pageUrl);
	     return WebUtil.webGet(pageUrl);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    @Override
    public boolean topicHasNext(String html) {
	Document doc = Jsoup.parse(html);
	Elements pageList = doc.getElementsByClass("pager_theme_5");

	if (pageList == null || pageList.isEmpty()) {
	    return false;
	}

	Elements pageLinks = pageList.get(0).getElementsByTag("a");
	for (int i = 0; i < pageLinks.size(); i++) {
	    Element a = pageLinks.get(i);
	    if ("下一页".equals(a.text())) {
		return true;
	    }
	}
	return false;
    }
}
