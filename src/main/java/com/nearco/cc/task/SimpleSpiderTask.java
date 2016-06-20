package com.nearco.cc.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.nearco.cc.model.ScheduleJob;
import com.nearco.cc.model.SimplePage;
import com.nearco.cc.processor.SimplePageProcessor;

@DisallowConcurrentExecution 
public class SimpleSpiderTask implements Job {

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
		 SimplePageProcessor pageProcessor = (SimplePageProcessor)context.getMergedJobDataMap().get("pageProcessor");
		 SimplePage simplePage = (SimplePage)context.getMergedJobDataMap().get("simplePage");
		 
		 if(pageProcessor!=null && simplePage!=null){
			 
		 }
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");    
		 System.out.println("任务名称 = [" + scheduleJob.getName() + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行"); 
	}

}
