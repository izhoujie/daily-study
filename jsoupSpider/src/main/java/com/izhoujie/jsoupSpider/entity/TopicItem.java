package com.izhoujie.jsoupSpider.entity;

/**
 * @author admin@izhoujie.com
 * 
 *         2016年9月12日 15:49:10
 * 
 *         楼层回帖内容
 * 
 */
public class TopicItem {
    // 回帖者
    private String auther;
    // 回帖内容
    private String content;
    // 回帖时间
    private String time;

    public TopicItem(String auther, String content, String time) {
	this.auther = auther;
	this.content = content;
	this.time = time;
    }

    public String getAuther() {
	return auther;
    }

    public void setAuther(String auther) {
	this.auther = auther;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getTime() {
	return time;
    }

    public void setTime(String time) {
	this.time = time;
    }
}
