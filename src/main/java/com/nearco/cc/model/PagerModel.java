package com.nearco.cc.model;

import java.util.List;

import javax.ws.rs.QueryParam;

public class PagerModel {

	private int limit = 100;
	private int page = 0;
	private List data;
	
	public int getLimit() {
		return limit;
	}
	@QueryParam("limit")
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	@QueryParam("page")
	public void setPage(int page) {
		this.page = page;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}	
	
}
