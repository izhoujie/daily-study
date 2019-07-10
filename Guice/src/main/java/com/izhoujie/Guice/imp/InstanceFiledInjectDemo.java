package com.izhoujie.Guice.imp;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.izhoujie.Guice.Service;

/**
 * @author admin@izhoujie.com
 *
 *         --实例变量注入的简单方式
 */
public class InstanceFiledInjectDemo {
    @Inject
    private Service service;

    public static void main(String[] args) {
	InstanceFiledInjectDemo demo = new InstanceFiledInjectDemo();
	Guice.createInjector().injectMembers(demo);
	// 直接.获取实例变量
	demo.service.execute();
    }
}
