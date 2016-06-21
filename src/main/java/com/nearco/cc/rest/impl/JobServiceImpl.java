package com.nearco.cc.rest.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.CronExpression;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.nearco.cc.model.ScheduleJob;
import com.nearco.cc.model.SimplePage;
import com.nearco.cc.rest.APIService;
import com.nearco.cc.rest.JobService;
import com.nearco.cc.service.ScheduleJobService;

@Service
public class JobServiceImpl extends APIService implements JobService {

	@Resource
	private ScheduleJobService jobService;

	@Override
	public Map<?, ?> addJob(ScheduleJob job, SimplePage page) {
		if (job == null || job.getName() == null || page == null || page.getAttributes() == null) {
			return error("参数错误");
		}
		System.out.println(JSON.toJSON(page.getAttributes()));
		System.out.println(JSON.toJSON(page.getTargetRequests()));
		return success(null, "ok");
	}

	@Override
	public Map<?, ?> triggerJob(String name, String group) {
		jobService.startNowJob(name, group);
		return success(null, "ok");
	}

	@Override
	public Map<?, ?> deleteJob(String name, String group) {
		jobService.delJob(name, group);
		return success(null, "ok");
	}

	@Override
	public Map<?, ?> stopJob(String name, String group) {
		jobService.stopJob(name, group);
		return success(null, "ok");
	}

	@Override
	public Map<?, ?> modifyTrigger(String name, String group, String cron) {
		try {
			cron = URLDecoder.decode(cron,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(CronExpression.isValidExpression(cron)){
			jobService.modifyTrigger(name, group, cron);
			return success(null, "ok");
		}else{
			return error("表达式不正确");
		}
	}

	@Override
	public List<ScheduleJob> listAll(String state) {
		if (state!=null && state.equals("running")) {
			return jobService.getAllRuningScheduleJob();
		}
		return jobService.getAllScheduleJob();
	}

	@Override
	public Map<?, ?> restartJob(String name, String group) {
		jobService.restartJob(name, group);
		return success(null, "ok");
	}

}
