package com.kongming.threads;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lock_Sychronized {
	public static void main(String[] args) throws Exception {

		// 显示锁
		runTask(Task_Lock.class);
		// 隐式锁
		runTask(Task_Sync.class);

	}

	public static void runTask(Class<? extends Runnable> task) throws Exception {
		// 缓冲线程池
		ExecutorService pool = Executors.newCachedThreadPool();
		System.out.println("*********" + task.getSimpleName() + "开始执行**********");

		for (int i = 0; i < 3; i++) {

			pool.submit(task.newInstance());

		}

		// 等待，给输出预留时间
		TimeUnit.SECONDS.sleep(10);
		System.out.println("*********" + task.getSimpleName() + "执行完毕**********\n");

		// 关闭线程池
		pool.shutdown();

	}

}

/**
 * @author km-zhou
 *
 *         -业务处理
 */
class Task {

	public void doSomething() {

		// 线程等待2秒完成业务处理
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 打印当前线程信息
		String name = Thread.currentThread().getName();
		int s = Calendar.getInstance().get(13);
		System.out.println("线程名-" + name + "，执行时间：" + s);

	}
}

/**
 * @author km-zhou
 *
 *         -显式锁/外部锁
 */
class Task_Lock extends Task implements Runnable {
	// Lock本身是对象级别的锁，声明为static将其升级到类级别才能跟Sync有一样的同步效果
	private static Lock lock = new ReentrantLock();
	// 如果是fianl 则lock还是对象级别的锁，每次都是新new一个锁，必然不会有线程同步的效果
	// private final Lock lock = new ReentrantLock();

	@Override
	public void run() {
		try {
			// 加锁
			lock.lock();
			doSomething();
		} finally {
			// 释放锁
			lock.unlock();
		}
	}
}

/**
 * @author km-zhou
 *
 *         -内部锁/外部锁
 */
class Task_Sync extends Task implements Runnable {

	@Override
	public void run() {
		synchronized ("Sync") {
			doSomething();
		}
	}
}
