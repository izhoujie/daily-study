package com.kongming.test;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import com.izhoujie.Zhou_test.I_test;

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
