package com.kongming.forguice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @author ACER
 *
 *         --static inject 静态注入
 */
public class StaticFieldInjectDemo {
	// 静态service
	@Inject
	private static Service service;
	// 实例service1
	@Inject
	private Service service1;

	public Service getService1() {
		return service1;
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new Module() {

			@Override
			public void configure(Binder binder) {
				// TODO Auto-generated method stub
				binder.requestStaticInjection(StaticFieldInjectDemo.class);
				binder.requestInjection(StaticFieldInjectDemo.class);
			}
		});
		StaticFieldInjectDemo.service.execute();

		StaticFieldInjectDemo demo = injector
				.getInstance(StaticFieldInjectDemo.class);
		Service sv = demo.getService1();
		sv.execute();
	}
}
