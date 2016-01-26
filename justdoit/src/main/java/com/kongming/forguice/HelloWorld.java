package com.kongming.forguice;

import com.google.inject.ImplementedBy;

//添加默认的自动实现类
@ImplementedBy(HelloWorldImpl.class)
public interface HelloWorld {
	String sayHello();
}
