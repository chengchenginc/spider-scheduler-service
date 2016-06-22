package com.nearco.cc.model;

import java.io.Serializable;

import javax.ws.rs.FormParam;


public class ScheduleJob implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;	//任务名
	private String group = "spider-scheduler";	//任务组
	private String cronExpression;	//cron表达式
	private String status;	//状态
	private String description;	//描述
	private String className;	//要执行的任务类路径名
	
	private SimplePage simplePage;
	
	public ScheduleJob() {
		super();
	}

	public ScheduleJob(String name, String group, String cronExpression,
			String status, String description,String className) {
		super();
		this.name = name;
		this.group = group;
		this.cronExpression = cronExpression;
		this.status = status;
		this.description = description;
		this.className=className;
	}

	public String getName() {
		return name;
	}

	@FormParam("name")
	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	@FormParam("group")
	public void setGroup(String group) {
		this.group = group;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	@FormParam("description")
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	@FormParam("cron")
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getClassName() {
		return className;
	}

	@FormParam("className")
	public void setClassName(String className) {
		this.className = className;
	}

	public SimplePage getSimplePage() {
		return simplePage;
	}

	public void setSimplePage(SimplePage simplePage) {
		this.simplePage = simplePage;
	}


}
