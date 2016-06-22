package com.nearco.cc.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.nearco.cc.Constants;
import com.nearco.cc.model.ScheduleJob;
import com.nearco.cc.model.SimplePage;
import com.nearco.cc.pipline.ELKPipline;
import com.nearco.cc.processor.SimplePageProcessor;

import us.codecraft.webmagic.Spider;

@DisallowConcurrentExecution 
public class SimpleSpiderTask implements Job {

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get(Constants.JOB_DATA_SHEDULE_JOB);
		 SimplePage simplePage = scheduleJob.getSimplePage();
		 if(simplePage!=null){
			 SimplePageProcessor pageProcessor = new SimplePageProcessor();
			 pageProcessor.setSimplePage(simplePage);
			 Spider.create(pageProcessor).addUrl(simplePage.getStartUrl()).addPipeline(new ELKPipline()
					 .setIndex(scheduleJob.getName()).setType(scheduleJob.getGroup())).thread(5).run();
		 }
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");    
		 System.out.println("任务名称 = [" + scheduleJob.getName() + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行"); 
	}
	
/*	//获取注册的任务bean
	private void getBean(JobExecutionContext context){
		
	}
	
	//注册任务类bean
	private void registerBeanDefinition(){
		
	}*/

}
