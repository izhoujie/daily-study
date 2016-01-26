package com.kongming.forguice;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * @author ACER
 *
 *         --构造器注入，可传多个参数。 构造注入方式降低了获取对象的灵活性。
 */
public class ConstructorInjectDemo {

	private Service service;
	private HelloWorld helloword;

	@Inject
	public ConstructorInjectDemo(Service service, HelloWorld helloWorld) {
		// TODO Auto-generated constructor stub
		this.service = service;
		this.helloword = helloWorld;
	}

	public Service getService() {
		return service;
	}

	public HelloWorld getHelloword() {
		return helloword;
	}

	public static void main(String[] args) {
		ConstructorInjectDemo demo = Guice.createInjector().getInstance(
				ConstructorInjectDemo.class);
		Service sv = demo.getService();
		sv.execute();

		HelloWorld hw = demo.getHelloword();
		System.out.println(hw.sayHello());
	}
}
