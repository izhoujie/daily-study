package com.izhoujie.Guice.imp;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @author admin@izhoujie.com
 *
 *         --guice 默认是在初次调用getInstance()方法时初始化。但也可以强制在容器中时就初始化实例对象。
 *
 */
public class EagerlySingletonDemo {

    public EagerlySingletonDemo() {
	System.out.println(System.nanoTime() + "--构造器初始化");
    }

    public void doSomething() {
	System.out.println(System.nanoTime() + "--doing... ");
    }

    public static void main(String[] args) throws Exception {
	Injector injector = Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {
		// 指定在容器中就初始化该实例对象，且是单例对象
		binder.bind(EagerlySingletonDemo.class).asEagerSingleton();
		System.out.println(binder.currentStage());
	    }
	});

	System.out.println(System.nanoTime() + "--从容器获取实例前");
	Thread.sleep(200L);
	// 获取实例调用方法
	EagerlySingletonDemo instance = injector.getInstance(EagerlySingletonDemo.class);
	instance.doSomething();
    }
}
