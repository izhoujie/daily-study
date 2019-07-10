package com.izhoujie.Guice.imp;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.izhoujie.Guice.IClient;
import com.izhoujie.Guice.IWeb;
import com.izhoujie.Guice.Service;

/**
 * @author admin@izhoujie.com
 *
 *         --模仿客户端和服务器的服务。分别有两对实现与注解和inject的联合使用，来实现同一接口的多实现注入。
 */

public class MultiInterfaceServiceDemo {

    @Inject
    @IWeb
    private Service iwebService;
    @Inject
    @IClient
    private Service iclentService;

    public static void main(String[] args) {
	MultiInterfaceServiceDemo demo = Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {
		// TODO Auto-generated method stub
		// 绑定客户端实现类到被注解了的目标成员变量上
		binder.bind(Service.class).annotatedWith(IClient.class).to(ClientService.class);
		// 绑定服务端实现类到被注解了的目标成员变量上
		binder.bind(Service.class).annotatedWith(IWeb.class).to(WebService.class);
	    }
	}).getInstance(MultiInterfaceServiceDemo.class);
	// 直接通过.获取实例变量后执行逻辑方法
	demo.iclentService.execute();
	demo.iwebService.execute();
    }
}
