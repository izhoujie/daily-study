package com.kongming.test;

import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Lambda_study_1 {
	public static void main(String[] args) throws Exception {
		// lambda 返回匹配目标类型  lambda函数的连用
		Callable<Runnable> call = () -> () -> {
			System.out.println("Hello World!-call");
		};
		call.call().run();
		// 简单匹配目标类型
		Runnable r = () -> {
			System.out.println("Hello World!");
		};
		r.run();
		
		Comparator<String> s = (String s1, String s2) -> {
			System.out.println("Hello World!");
			return s2.length() - s1.length();
		};

		System.out.println(s.compare("100", "100345340"));
		//java8  util下新增Function接口 以lambda写法作函数参数
		Function<Integer, String> f = (Integer n) -> {
			System.out.println("Hello World!");
			return "num=" + n;
		};

		System.out.println(f.apply(1000));
		// java8  新增接口 lambda形式的常用参数 返回值类型：void
		Consumer<Integer> user = (Integer n) -> {
			System.out.println("Hello World!" + n);
		};
		user.accept(999);
		// java8  新增接口  返回值类型：boolean
		Predicate<Integer> p = (Integer n) -> {
			System.out.println("Hello World!" + n);

			return n > 100;
		};

		System.out.println(p.test(1000));

		Lambda_study_1 lam = new Lambda_study_1();
		// 阶乘
		Integer result = lam.factorial.apply(10);
		System.out.println(result);
		// 自然数和
		Integer sum = lam.fun.apply(100);
		System.out.println(sum);

	}

	// java8新增接口 lambda的一元操作
	public UnaryOperator<Integer> factorial = i -> i == 1 ? 1 : i * this.factorial.apply(i - 1);
	public UnaryOperator<Integer> fun = i -> i == 1 ? 1 : i + this.fun.apply(i - 1);

}
