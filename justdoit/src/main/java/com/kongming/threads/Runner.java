package com.kongming.threads;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author km-zhou
 * 
 *         -线程池以及多线程同步之CountDownLatch
 *
 */
public class Runner implements Callable<Integer> {
	private CountDownLatch begin;
	private CountDownLatch end;

	public Runner(CountDownLatch _begin, CountDownLatch _end) {
		this.begin = _begin;
		this.end = _end;
	}

	@Override
	public Integer call() throws Exception {

		int score = new Random().nextInt(30);

		begin.await();

		TimeUnit.MILLISECONDS.sleep(score);

		end.countDown();

		return score;
	}

	public static void main(String[] args) throws Exception {
		int num = 10;
		CountDownLatch begin = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(num);

		ExecutorService es = Executors.newFixedThreadPool(num);

		ArrayList<Future<Integer>> future = new ArrayList<Future<Integer>>();

		for (int i = 0; i < num; i++) {

			future.add(es.submit(new Runner(begin, end)));

		}
		System.out.println(begin.toString());
		System.out.println(end.toString());

		begin.countDown();
		end.await();

		for (Future<Integer> f : future) {
			System.out.println(f.get());

		}
		System.out.println(begin.toString());
		System.out.println(end.toString());
	}
}
