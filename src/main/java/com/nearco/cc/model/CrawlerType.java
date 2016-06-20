package com.nearco.cc.model;

public enum CrawlerType {

	/**
	 * single 爬去单页面型 list 爬去列表数据 mix 列表页获取列表信息 +详细页获取详细信息 目前支持：1，2
	 */
	single(1), list(2), mix(3);

	private int value;

	private CrawlerType(int value) {
		this.value = value;
	}

	public static CrawlerType valueOf(int value) { // 手写的从int到enum的转换函数
		switch (value) {
		case 1:
			return single;
		case 2:
			return list;
		case 3:
			return mix;
		default:
			return single;// 默认单页面
		}
	}

	public int value() {
		return this.value;
	}

}
