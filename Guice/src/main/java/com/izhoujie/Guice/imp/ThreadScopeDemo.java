package com.izhoujie.Guice.imp;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.izhoujie.Guice.Service;

/**
 * @author admin@izhoujie.com
 *
 *
 *         绑定自定义Scope
 */
public class ThreadScopeDemo {

    static class ThreadServiceScope implements Scope {
	static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();

	@Override
	public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
	    return new Provider<T>() {

		@Override
		public T get() {
		    @SuppressWarnings("unchecked")
		    T instance = (T) threadLocal.get();
		    if (instance == null) {
			instance = unscoped.get();
			threadLocal.set(instance);
		    }
		    return instance;
		}
	    };
	}

	@Override
	public String toString() {
	    return "Scopes.ThreadServiceScope";
	}
    }

    /**
     * @param args
     * 
     * 
     *            --Test
     */

    public static void main(String[] args) {
	// 获得在容器中初始化后的注入对象
	final Injector injector = Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {
		binder.bind(Service.class).to(WebService.class).in(new ThreadServiceScope());
	    }
	});

	// new三个线程，并分别获得对象后打印哈希值
	for (int i = 0; i < 3; i++) {
	    new Thread("Thread#" + i) {
		@Override
		public void run() {
		    for (int j = 0; j < 4; j++) {
			System.out.println(Thread.currentThread().getName() + "-" + j + ":"
				+ injector.getInstance(Service.class).hashCode());
			try {
			    Thread.sleep(100);
			} catch (Exception e) {
			}
		    }
		}
	    }.start();
	}
    }
}
