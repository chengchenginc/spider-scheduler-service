package com.nearco.cc.model;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.QueryParam;

public class PagerModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int total=0;
	private int limit = 100;
	private int page = 1;
	private List data;
	
	public int getLimit() {
		return limit;
	}
	@QueryParam("limit")
	public void setLimit(int limit) {
		if(limit!=0){
			this.limit = limit;
		}
	}
	public int getPage() {
		return page;
	}
	@QueryParam("page")
	public void setPage(int page) {
		if(page!=0){
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
	
}
