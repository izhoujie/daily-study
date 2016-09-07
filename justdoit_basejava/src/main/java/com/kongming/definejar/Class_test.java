package com.kongming.definejar;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import com.izhoujie.Zhou_test.I_test;

/**
 * @author admin@izhoujie.com
 * 
 *         在JDK中加入自定义的jar包后可直接new出自定义对象
 * 
 *         本地JDK自定义jar 添加路径：C:\Program Files\Java\jdk1.8.0_60\jre\lib\ext
 * 
 *         添加的自定义jar包：Zhou-test-0.0.1-SNAPSHOT
 *
 */
public class Class_test {
    public static void main(String[] args) {
	Class_test test = new Class_test();

	I_test i_test = new I_test();
	i_test.outPrint();

	test.outPrint();
	System.out.println(System.getenv("servertool.locate2"));
	Properties properties = System.getProperties();
	Iterator<Entry<Object, Object>> iterator = properties.entrySet().iterator();
	while (iterator.hasNext()) {
	    Entry<Object, Object> entry = iterator.next();
	    System.out.println(entry.getKey() + "    " + entry.getValue());

	}

    }

    public void outPrint() {
	String name = getClass().getSimpleName();
	System.out.println(name);

    }
}
