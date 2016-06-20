package com.nearco.cc.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class CrosFilter implements ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext res)
			throws IOException {
		res.getHeaders().add("Access-Control-Allow-Origin", "*");
	}

}
