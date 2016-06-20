package com.nearco.cc.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Site;

public class SimplePage {

	private Site site;
	// 浏览器设置
	private String domain;
	private String userAgent;
	private String cookies;
	private String charset;

	private int retryTimes = 3;
	private int sleepTime = 10;

	private String startUrl;
	private List<String> targetRequests;
	private List<String> requestRegexes;
	private List<SimpleAttribute> attributes;
	private CrawlerType crawlerType = CrawlerType.single;// 爬取类型
	private String listNodeSelector;// 默认css选择器

	public String getStartUrl() {
		return startUrl;
	}

	@FormParam("startUrl")
	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public String getListNodeSelector() {
		return listNodeSelector;
	}

	@FormParam("listNodeSelector")
	public void setListNodeSelector(String listNodeSelector) {
		this.listNodeSelector = listNodeSelector;
	}

	public CrawlerType getCrawlerType() {
		return crawlerType;
	}

	public void setCrawlerType(CrawlerType crawlerType) {
		this.crawlerType = crawlerType;
	}

	@FormParam("crawlerType")
	public void setCrawlerType(int crawlerType) {
		this.crawlerType = CrawlerType.valueOf(crawlerType);
	}

	public List<String> getTargetRequests() {
		return targetRequests;
	}

	public void setTargetRequests(List<String> urls) {
		this.targetRequests = urls;
	}

	@FormParam("urls")
	public void setTargetRequests(String urls) {
		this.targetRequests = JSON.parseArray(urls, String.class);
	}

	public List<String> getRequestRegexes() {
		return requestRegexes;
	}

	public void setRequestRegexes(List<String> requestRegexes) {
		this.requestRegexes = requestRegexes;
	}

	@FormParam("requestRegexes")
	public void setRequestRegexes(String requestRegexes) {
		this.requestRegexes = JSON.parseArray(requestRegexes, String.class);
	}

	public void addRequestRegex(String requestRegex) {
		if (this.requestRegexes == null) {
			this.requestRegexes = new ArrayList<String>();
		}
		this.requestRegexes.add(requestRegex);
	}

	public List<SimpleAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<SimpleAttribute> attributes) {
		this.attributes = attributes;
	}

	@FormParam("attributes")
	public void setAttributes(String attributes) {
		if (!attributes.isEmpty()) {
			this.attributes = JSON.parseArray(attributes, SimpleAttribute.class);
		}
	}

	public Site getSite() {
		if (this.site == null) {
			Site newSite = Site.me();
			if (this.domain != null) {
				newSite.setDomain(this.domain);
			}
			if (this.userAgent != null) {
				newSite.setUserAgent(userAgent);
			}
			if (this.charset != null) {
				newSite.setCharset(this.charset);
			}
			newSite.setSleepTime(this.sleepTime);
			newSite.setCycleRetryTimes(this.retryTimes);
			this.site = newSite;
		}
		return site;
	}

	public String getDomain() {
		return domain;
	}

	@FormParam("domain")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUserAgent() {
		return userAgent;
	}

	@FormParam("userAgent")
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public String getCharset() {
		return charset;
	}

	@FormParam("charset")
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

}
