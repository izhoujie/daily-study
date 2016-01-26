package com.kongming.forguice;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * @author ACER
 *
 *         --字段/属性注入
 */
public class FieldInjectDemo {
	@Inject
	private Service service;

	public Service getService() {
		return service;
	}

	public static void main(String[] args) {
		FieldInjectDemo demo = Guice.createInjector().getInstance(
				FieldInjectDemo.class);
		Service sv = demo.getService();
		sv.execute();
	}
}