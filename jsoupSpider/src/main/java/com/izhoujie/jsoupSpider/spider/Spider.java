package com.izhoujie.jsoupSpider.spider;

import java.util.List;

import com.izhoujie.jsoupSpider.entity.Topic;
import com.izhoujie.jsoupSpider.entity.TopicItem;

/**
 * @author admin@izhoujie.com
 * 
 *         2016年9月12日 16:05:05
 * 
 *         针对百度贴吧的爬虫
 */
public interface Spider {
    /**
     * 获取主题列表html
     * 
     * @param pageIndex
     * @return
     */
    public String listPage(int pageIndex);

    /**
     * 判断主题列表是否还有下一页
     * 
     * @param html
     * @return
     */
    public boolean listHasNext(String html);

    /**
     * 获取主题页html
     * 
     * @param url
     * @param pageIndex
     * @return
     */
    public String topicPage(String url, int pageIndex);

    /**
     * 判断主题回帖是否还有下一页
     * 
     * @param html
     * @return
     */
    public boolean topicHasNext(String html);

    /**
     * 主题列表html解析
     * 
     * @param pageIndex
     * @return
     */
    public List<Topic> parseListPage(String html);

    /**
     * 主题页解析
     * 
     * @param url
     * @return
     */
    public List<TopicItem> parseTopicItems(String html);
}
