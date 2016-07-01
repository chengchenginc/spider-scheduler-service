package com.nearco.cc.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.annotations.Form;

import com.nearco.cc.model.PagerModel;

@Path("/crawler/data")
@Produces("application/json; charset=utf-8")
public interface DataService {

	@Path("list")
	@GET
	public PagerModel getPagerList(@QueryParam("name") String name,@QueryParam("group") String group,@Form PagerModel pager);
	
	
	@Path("delete")
	@GET
	public Map<?,?> delete(@QueryParam("name") String name,@QueryParam("group") String group);
	
	@Path("export")
	@GET
	public void export(@QueryParam("name") String name,@QueryParam("group") String group, @QueryParam("format") String format,@Context HttpServletRequest request,@Context HttpServletResponse response);
}
