package com.izhoujie.threads;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author admin@izhoujie.com
 * 
 *         -线程池以及多线程同步之CountDownLatch
 *
 */
public class Runner implements Callable<Integer> {
    // 同步线程起始信号
    private CountDownLatch begin;
    // 同步线程结束信号
    private CountDownLatch end;

    public Runner(CountDownLatch _begin, CountDownLatch _end) {
	this.begin = _begin;
	this.end = _end;
    }

    @Override
    public Integer call() throws Exception {

	// 当前线程处理业务所需的时间
	int score = new Random().nextInt(30);

	// 准备处理前等待开始信号
	begin.await();

	// 业务处理中...
	TimeUnit.MILLISECONDS.sleep(score);

	// 线程处理完毕等待结束信号以进行下一步
	end.countDown();

	return score;
    }

    public static void main(String[] args) throws Exception {
	int num = 10;
	// 起始信号
	CountDownLatch begin = new CountDownLatch(1);
	// 结束信号-num为线程数
	CountDownLatch end = new CountDownLatch(num);

	// 使用固定线程池
	ExecutorService es = Executors.newFixedThreadPool(num);

	ArrayList<Future<Integer>> future = new ArrayList<Future<Integer>>();

	for (int i = 0; i < num; i++) {
	    // 依次开启num个线程，并等待开始信号以进行业务处理
	    future.add(es.submit(new Runner(begin, end)));

	}
	System.out.println(begin.toString());
	System.out.println(end.toString());

	// 调用方法减为0，所有线程开始处理业务
	begin.countDown();
	// 等待所有线程处理完毕
	end.await();

	// 对所有线程的返回结果进行处理
	for (Future<Integer> f : future) {
	    System.out.println(f.get());
	}
	System.out.println(begin.toString());
	System.out.println(end.toString());
    }
}
