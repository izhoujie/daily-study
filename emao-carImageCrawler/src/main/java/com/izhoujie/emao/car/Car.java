package com.izhoujie.emao.car;

import java.util.ArrayList;
import java.util.List;

/**
 * Car bean
 * 
 * @author km-zhou
 *
 */
public class Car {
    // 铭牌
    private String brand = "";
    // 子铭牌
    private String subBrand = "";
    // 跳转链接
    private String url = "";
    // 图片下载保存路径
    private String savePath = "";
    // 暂无用
    private List<String> imagUrlList = new ArrayList<String>();

    public Car(String brand, String subBrand, String url) {
	super();
	this.brand = brand;
	this.subBrand = subBrand;
	this.url = url;
    }

    public String getBrand() {
	return brand;
    }

    public void setBrand(String brand) {
	this.brand = brand;
    }

    public String getSubBrand() {
	return subBrand;
    }

    public void setSubBrand(String subBrand) {
	this.subBrand = subBrand;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getSavePath() {
	return savePath;
    }

    public void setSavePath(String savePath) {
	this.savePath = savePath;
    }

    public List<String> getImagUrlList() {
	return imagUrlList;
    }

    public void setImagUrlList(List<String> imagUrlList) {
	this.imagUrlList = imagUrlList;
    }
}
