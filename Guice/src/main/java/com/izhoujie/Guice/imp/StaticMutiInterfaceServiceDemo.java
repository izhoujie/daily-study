package com.izhoujie.Guice.imp;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.izhoujie.Guice.IClient;
import com.izhoujie.Guice.IWeb;
import com.izhoujie.Guice.Service;

/**
 * @author admin@izhoujie.com
 *
 *         --静态多服务注入
 */
public class StaticMutiInterfaceServiceDemo {
    @Inject
    @IClient
    private static Service iclientService;
    @Inject
    @IWeb
    private static Service iwebService;

    public static void main(String[] args) {
	Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {
		// TODO Auto-generated method stub
		binder.bind(Service.class).annotatedWith(IClient.class).to(ClientService.class);
		binder.bind(Service.class).annotatedWith(IWeb.class).to(WebService.class);
		binder.requestStaticInjection(StaticMutiInterfaceServiceDemo.class);
	    }
	});
	StaticMutiInterfaceServiceDemo.iclientService.execute();
	StaticMutiInterfaceServiceDemo.iwebService.execute();
    }
}
