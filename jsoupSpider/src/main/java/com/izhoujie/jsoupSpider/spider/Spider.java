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
     * 读取列表页html
     * 
     * @param pageIndex
     * @return
     */
    public String listPage(int pageIndex);

    /**
     * 判断列表页是否有下一页
     * 
     * @param html
     * @return
     */
    public boolean listHasNext(String html);

    /**
     * 读取主题页
     * 
     * @param url
     * @param pageIndex
     * @return
     */
    public String topicPage(String url, int pageIndex);

    /**
     * 判断主题页是否有下一页
     * 
     * @param html
     * @return
     */
    public boolean topicHasNext(String html);

    /**
     * 列表页解析
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
