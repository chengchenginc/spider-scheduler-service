package com.nearco.cc.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.nearco.cc.model.CrawlerType;
import com.nearco.cc.model.SelectType;
import com.nearco.cc.model.SimpleAttribute;
import com.nearco.cc.model.SimplePage;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.Selectable;

public class SimplePageProcessor implements PageProcessor {

	private Site site;

	private SimplePage simplePage;

	public SimplePage getSimplePage() {
		return simplePage;
	}

	public void setSimplePage(SimplePage simplePage) {
		this.simplePage = simplePage;
		this.site = simplePage.getSite();
	}

	@Override
	public void process(Page page) {
		if (simplePage != null) {
			if (simplePage.getRequestRegexes() != null && simplePage.getRequestRegexes().size() > 0) {
				for (String regex : simplePage.getRequestRegexes()) {
					page.addTargetRequests(page.getHtml().links().regex(regex).all());
				}
			}
			if (simplePage.getTargetRequests() != null && simplePage.getTargetRequests().size() > 0) {
				page.addTargetRequests(simplePage.getTargetRequests());
			}
			List<SimpleAttribute> attributes = simplePage.getAttributes();
			if (attributes == null || attributes.size() == 0) {
				page.setSkip(true);
			} else {
				if (simplePage.getCrawlerType() == CrawlerType.single) {
					processAttributes(page);
				} else if (simplePage.getCrawlerType() == CrawlerType.list
						&& !simplePage.getListNodeSelector().isEmpty()) {
					processNodes(page);
				} else {
					// 设置不正确 跳过解析
					page.setSkip(true);
				}
			}
		} else {
			page.setSkip(true);
		}
	}

	/**
	 * 单页面属性解析
	 * 
	 * @param page
	 */
	private void processAttributes(Page page) {
		List<SimpleAttribute> attributes = simplePage.getAttributes();
		for (SimpleAttribute attribute : attributes) {
			if (attribute.getSelectType() == SelectType.xpath) {
				page.putField(attribute.getName(),
						page.getHtml().xpath(attribute.getSelector() + attribute.getSelectMethod()).toString());
			} else if (attribute.getSelectType() == SelectType.regex) {
				page.putField(attribute.getName(),
						page.getHtml().regex(attribute.getSelector() + attribute.getSelectMethod()).toString());
			} else if (attribute.getSelectType() == SelectType.css) {
				page.putField(attribute.getName(),
						page.getHtml().$(attribute.getSelector(), attribute.getSelectMethod().value()).toString());
			}
		}
	}

	/**
	 * 单页面多接点列表解析
	 * 
	 * @param page
	 */
	private void processNodes(Page page) {
		List<SimpleAttribute> attributes = simplePage.getAttributes();
		List<Selectable> nodes = ((HtmlNode) page.getHtml().$(simplePage.getListNodeSelector())).nodes();
		List<ConcurrentHashMap<String, String>> list = new ArrayList<ConcurrentHashMap<String, String>>();
		for (Selectable node : nodes) {
			ConcurrentHashMap<String, String> items = new ConcurrentHashMap<String, String>();
			for (SimpleAttribute attribute : attributes) {
				if (attribute.getSelectType() == SelectType.xpath) {
					items.put(attribute.getName(),
							node.xpath(attribute.getSelector() + attribute.getSelectMethod()).toString());
				} else if (attribute.getSelectType() == SelectType.regex) {
					items.put(attribute.getName(),
							node.regex(attribute.getSelector() + attribute.getSelectMethod()).toString());
				} else if (attribute.getSelectType() == SelectType.css) {
					items.put(attribute.getName(),
							node.$(attribute.getSelector(), attribute.getSelectMethod().value()).toString());
				}
			}
			list.add(items);
		}
		page.putField("items", list);
	}

	@Override
	public Site getSite() {
		return site;
	}

}
