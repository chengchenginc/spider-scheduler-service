package com.nearco.cc.model;

import java.io.Serializable;

public class SimpleAttribute implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String selector;// 选择器
	private SelectType selectType;// 类型
	private SelectMethod selectMethod;// 获取内容方法

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public SelectType getSelectType() {
		return selectType;
	}

	public void setSelectType(SelectType selectType) {
		this.selectType = selectType;
	}

	public SelectMethod getSelectMethod() {
		return selectMethod;
	}

	public void setSelectMethod(SelectMethod selectMethod) {
		this.selectMethod = selectMethod;
	}

}
