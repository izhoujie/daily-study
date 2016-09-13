package com.izhoujie.jsoupSpider.entity;

import java.util.List;

/**
 * @author admin@izhoujie.com
 * 
 *         2016年9月12日 15:48:06
 * 
 *         帖子信息
 */
public class Topic {
    // 帖子链接
    private String url;
    // 帖子标题
    private String title;
    // 贴主
    private String auther;
    // 创建时间 -帖子时间比较特殊：可能只有时间，或只有月日，或只有年月-待处理
    private String createTime;
    // 楼层评论
    private List<TopicItem> items;

    public Topic(String url, String title, String user) {
	this(url, title, user, null);
    }

    public Topic(String url, String title, String user, String createTime) {
	this.url = url;
	this.title = title;
	this.auther = user;
	this.createTime = createTime;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getAuther() {
	return auther;
    }

    public void setAuther(String auther) {
	this.auther = auther;
    }

    public String getCreateTime() {
	return createTime;
    }

    public void setCreateTime(String createTime) {
	this.createTime = createTime;
    }

    public List<TopicItem> getItems() {
	return items;
    }

    public void setItems(List<TopicItem> items) {
	this.items = items;
    }
}
