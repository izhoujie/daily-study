package com.kongming.forguice;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * @author ACER
 *
 *         --setter注入
 */
public class SetterInjectDemo {
	private Service service;

	@Inject
	public void setService(Service service) {
		this.service = service;
	}

	public Service getService() {
		return service;
	}

	public static void main(String[] args) {
		SetterInjectDemo demo = Guice.createInjector().getInstance(
				SetterInjectDemo.class);
		Service sv = demo.getService();
		sv.execute();
	}
}
