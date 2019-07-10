package com.izhoujie.Guice.imp;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.izhoujie.Guice.Service;

/**
 * @author admin@izhoujie.com
 * 
 *         --选项注入/选择注入。
 *
 */
public class OptionalInjectDemo {
    // 选项注入，在此处直接new出一个备用的实例
    @Inject
    Service service = new WebService();

    public static void main(String[] args) {
	Injector injector = Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {
		// 若此处绑定的实例失败，则会用之前已经new好的备用实例替代之
		// binder.bind(Service.class).to(ClientService.class);
	    }
	});

	Service sv = injector.getInstance(OptionalInjectDemo.class).service;
	sv.execute();
    }
}
