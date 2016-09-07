package com.izhoujie.Guice.imp;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.izhoujie.Guice.HelloWorld;

/**
 * @author admin@izhoujie.com
 * 
 *         --直接在接口上加@ImplementedBy(实现类.class)可实现自动绑定。但仍可通过Module方式再手动更换绑定，
 *         手动实现优先于自动绑定
 * 
 *         --直接在实现类上加@Singleton可指定该实现类是单例的，同样可在Module时另作变更
 *
 */
public class HelloWorldTest {

    // Hello World 1-可以绑定接口的实现类以及继承的子类，但不能是自己，每次都是新实例，非单例
    @Test
    public void testSayHello() {
	Injector injector = Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {
		// TODO Auto-generated method stub
		binder.bind(HelloWorld.class).to(HelloWorldImpl.class);
		binder.bind(HelloWorldImpl.class).to(HelloworldSubImpl.class);
	    }
	});
	HelloWorld hw1 = injector.getInstance(HelloWorld.class);
	HelloWorld hw2 = injector.getInstance(HelloWorld.class);
	HelloWorld hw3 = injector.getInstance(HelloWorldImpl.class);

	System.out.println(hw1.getClass().getName());
	System.out.println(hw2.getClass().getName());
	System.out.println(hw3.getClass().getName());

	System.out.println(hw1.sayHello());
	System.out.println(hw2.sayHello());
	System.out.println(hw3.sayHello());

	System.out.println(hw1.hashCode());
	System.out.println(hw2.hashCode());
	System.out.println(hw3.hashCode());
	System.out.println(hw1.hashCode() + "---" + hw2.hashCode());
	Assert.assertEquals(hw1.hashCode(), hw2.hashCode());
	Assert.assertEquals(hw1.sayHello(), "Hello World!");
    }

    // 绑定到自己构造出的实例上 new出实例，上面的方式是class
    @Test
    public void test1() {
	Injector injector = Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {
		// TODO Auto-generated method stub
		binder.bind(HelloWorld.class).toInstance(new HelloworldSubImpl());
	    }
	});

	HelloWorld hw = injector.getInstance(HelloWorld.class);
	System.out.println(hw.sayHello());
    }

    // 使用Provider<T>方式构造对象实例
    @Test
    public void test2() {
	Injector injector = Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {
		// TODO Auto-generated method stub
		binder.bind(HelloWorld.class).toProvider(new Provider<HelloWorld>() {

		    @Override
		    public HelloWorld get() {
			// TODO Auto-generated method stub
			return new HelloworldSubImpl();
		    }
		});
	    }
	});

	HelloWorld hw = injector.getInstance(HelloWorld.class);
	System.out.println(hw.sayHello());
    }

    // 不绑定Module的自动寻找实现类
    @Test
    public void test3() {
	Injector injector = Guice.createInjector();
	HelloWorld hw = injector.getInstance(HelloworldSubImpl.class);
	System.out.println(hw.sayHello());
    }

    // 绑定一个单例
    @Test
    public void test4() {
	Injector injector = Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {
		// TODO Auto-generated method stub
		binder.bind(HelloWorld.class).to(HelloworldSubImpl.class).in(Scopes.SINGLETON);
	    }
	});

	HelloWorld hw1 = injector.getInstance(HelloWorld.class);
	HelloWorld hw2 = injector.getInstance(HelloWorld.class);
	HelloWorld hw3 = injector.getInstance(HelloWorldImpl.class);
	System.out.println(hw1.sayHello() + "\n" + hw3.sayHello());
	System.out.println(hw1.hashCode() + "\n" + hw2.hashCode() + "\n" + hw3.hashCode());
    }
}
