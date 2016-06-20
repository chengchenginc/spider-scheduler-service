package com.nearco.cc.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.alibaba.fastjson.JSON;
import com.nearco.cc.model.ScheduleJob;

@ContextConfiguration(locations = { "classpath:META-INF/spring/spring.xml" }) 
public class ScheduleJobServiceTest extends AbstractJUnit4SpringContextTests{

	@Autowired
	private ScheduleJobService jobService;
	
	
	@Before  
    public void setup() {  
    }  
	
	@Test
	public void addJob(){
		ScheduleJob scheduleJob = new ScheduleJob();
		scheduleJob.setName("hh4");//任务名称
		scheduleJob.setGroup("crawler");//按类型或者用户划分
		scheduleJob.setStatus("1");
		scheduleJob.setClassName("com.nearco.cc.task.SimpleSpiderTask");
		scheduleJob.setCronExpression("0/5 * * * * ?");
		jobService.add(scheduleJob);
	}
	
	
	@Test
	public void getJobs(){
		System.out.println(JSON.toJSON(jobService.getAllScheduleJob()));
	}
	
	
	@Test
	public void delJob(){
		jobService.delJob("hhh", "crawler");
	}
	
	@Test
	public void stopJob(){
		jobService.stopJob("hhh", "crawler");
	}
	
}
