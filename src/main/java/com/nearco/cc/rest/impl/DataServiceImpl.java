package com.nearco.cc.rest.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nearco.cc.model.PagerModel;
import com.nearco.cc.rest.APIService;
import com.nearco.cc.rest.DataService;
import com.nearco.cc.search.service.SearchService;


@Service
public class DataServiceImpl extends APIService implements DataService {

	@Resource
	private SearchService searchServiceImpl;
	
	@Override
	public PagerModel getPagerList(String name, String group, PagerModel pager) {
		return searchServiceImpl.search(name, group, pager);
	}

	@Override
	public Map<?, ?> delete(String name, String group) {
		return success(null,"ok");
	}

}
