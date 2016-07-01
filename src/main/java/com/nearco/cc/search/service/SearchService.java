package com.nearco.cc.search.service;

import org.elasticsearch.client.Client;

import com.nearco.cc.model.PagerModel;

import us.codecraft.webmagic.ResultItems;

public interface SearchService {

	public PagerModel search(String index,String type,PagerModel pager);
	
	public void clearData(String index,String type);
	
	public void bulkIndex(String index,String type,boolean isList,ResultItems resultItems);
	
	public boolean isExists(String index,String type);
	
	public Client getClient();
	
}
