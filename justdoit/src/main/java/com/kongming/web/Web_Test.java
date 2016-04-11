package com.kongming.web;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

@Path("/helloworld")
public class Web_Test {
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServerFactory.create("http://192.168.67.28:9999/");

		server.start();

		System.out.println("restful webserver is start in:192.168.1.100");

	}

	@GET
	@Produces("text/plain")
	public String webTest() {

		return "Hello World!";
	}

}
