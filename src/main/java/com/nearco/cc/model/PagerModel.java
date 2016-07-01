package com.nearco.cc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class PagerModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int total = 0;
	private int limit = 100;
	private int page = 1;
	private List data;
	private Map<String, ?> queryMap;

	public int getLimit() {
		return limit;
	}

	@QueryParam("limit")
	public void setLimit(int limit) {
		if (limit != 0) {
			this.limit = limit;
		}
	}

	public int getPage() {
		return page;
	}

	@QueryParam("page")
	public void setPage(int page) {
		if (page != 0) {
			this.page = page;
		}
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Map<String, ?> getQueryMap() {
		return queryMap;
	}

	@QueryParam("query")
	public void setQueryMap(String query) {
		if(StringUtils.isNotBlank(query)){
			this.queryMap = JSON.parseObject(query, new HashMap<String,Object>().getClass());
		}
		
	}

}
