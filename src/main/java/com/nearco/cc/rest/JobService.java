package com.nearco.cc.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.resteasy.annotations.Form;

import com.nearco.cc.model.ScheduleJob;
import com.nearco.cc.model.SimplePage;

@Path("/job/")
@Produces("application/json; charset=utf-8")
public interface JobService {

	@Path("add")
	@POST
	public Map<?, ?> addJob(@Form ScheduleJob job, @Form SimplePage page);

	@Path("trigger")
	@GET
	public Map<?, ?> triggerJob(@QueryParam("name") String name, @QueryParam("group") String group);

	@Path("delete")
	@GET
	public Map<?, ?> deleteJob(@QueryParam("name") String name, @QueryParam("group") String group);

	@Path("stop")
	@GET
	public Map<?, ?> stopJob(@QueryParam("name") String name, @QueryParam("group") String group);

	@Path("modify")
	@POST
	public Map<?, ?> modifyTrigger(@FormParam("name") String name, @FormParam("group") String group,
			@FormParam("cron") String cron);

	@Path("list")
	@GET
	public List<ScheduleJob> listAll(@QueryParam("state") String state);
	
	@Path("restart")
	@GET
	public Map<?, ?> restartJob(@QueryParam("name") String name, @QueryParam("group") String group);

}
