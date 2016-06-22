package com.nearco.cc.pipline;

import com.nearco.cc.Constants;
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
	
	private boolean isList = false;

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
	

	public boolean isList() {
		return isList;
	}

	public ELKPipline setList(boolean isList) {
		this.isList = isList;
		return this;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		//SearchService searchServiceImpl =  SpringContextUtil.getBean(com.nearco.cc.search.service.impl.SearchServiceImpl.class);
		SearchService searchServiceImpl =  SpringContextUtil.getBean(Constants.ELK_INSTANCE_SERVICE);
		searchServiceImpl.bulkIndex(this.getIndex(), this.getType(),this.isList,resultItems);
	}
	
}
