package com.nearco.cc.pipline;

import javax.annotation.Resource;

import com.nearco.cc.search.service.SearchService;
import com.nearco.cc.util.SpringContextUtil;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * elastic 数据消费
 * @author chengchenginc
 */
public class ELKPipline implements Pipeline {
	
	
	private String index;
	private String type;
	

	public String getIndex() {
		return index;
	}

	public ELKPipline setIndex(String index) {
		this.index = index;
		return this;
	}

	public String getType() {
		return type;
	}

	public ELKPipline setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		SearchService searchServiceImpl =  SpringContextUtil.getBean(com.nearco.cc.search.service.impl.SearchServiceImpl.class);
		searchServiceImpl.bulkIndex(this.getIndex(), this.getType(), resultItems);
	}

}
