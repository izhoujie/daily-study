package com.izhoujie.Guice;

import com.google.inject.ImplementedBy;
import com.izhoujie.Guice.imp.HelloWorldImpl;

//添加默认的自动实现类
@ImplementedBy(HelloWorldImpl.class)
public interface HelloWorld {
	String sayHello();
}
