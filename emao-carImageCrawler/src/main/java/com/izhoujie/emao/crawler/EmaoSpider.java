package com.izhoujie.emao.crawler;

import java.util.List;

import com.izhoujie.emao.car.Car;

public interface EmaoSpider {

    /**
     * 获取所有的车的主铭牌及跳转链接
     * 
     * @param url
     * @return
     */
    public List<Car> getAllBrands(String url);

    /**
     * 获取所有车的次铭牌及图片并下载保存
     * 
     * @param car
     * @return
     */
    public void getAllSubBrands(Car car);

}
