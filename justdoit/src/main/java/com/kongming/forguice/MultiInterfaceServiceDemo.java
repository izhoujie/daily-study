package com.kongming.forguice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

/**
 * @author ACER
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
				binder.bind(Service.class).annotatedWith(IClient.class)
						.to(ClientService.class);
				binder.bind(Service.class).annotatedWith(IWeb.class)
						.to(WebService.class);
			}
		}).getInstance(MultiInterfaceServiceDemo.class);
		// 直接.获取实例变量后执行方法
		demo.iclentService.execute();
		demo.iwebService.execute();
	}
}
