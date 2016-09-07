package com.izhoujie.Guice.imp;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.izhoujie.Guice.Service;

/**
 * @author admin@izhoujie.com
 *
 *         --setter注入
 */
public class SetterInjectDemo {
    private Service service;

    @Inject
    // 实际注入了Service的默认设置实现类
    public void setService(Service service) {
	this.service = service;
    }

    public Service getService() {
	return service;
    }

    public static void main(String[] args) {
	SetterInjectDemo demo = Guice.createInjector().getInstance(SetterInjectDemo.class);
	Service sv = demo.getService();
	sv.execute();
    }
}
