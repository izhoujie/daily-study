package com.izhoujie.emao.main;

import java.util.List;

import com.izhoujie.emao.car.Car;
import com.izhoujie.emao.crawler.imp.EmaoSpiderImp;

public class EmaoGo {

    public static void main(String[] args) {
	EmaoSpiderImp emao = new EmaoSpiderImp();

	String startUrl = "http://auto.emao.com/pic/";

	List<Car> allBrands = emao.getAllBrands(startUrl);
	System.out.println(allBrands.get(0).getUrl());

	for (Car car : allBrands) {
	    emao.getAllSubBrands(car);
	}
    }
}
