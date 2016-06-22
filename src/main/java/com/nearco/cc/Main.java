package com.nearco.cc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nearco.cc.model.CrawlerType;
import com.nearco.cc.model.SelectMethod;
import com.nearco.cc.model.SelectType;
import com.nearco.cc.model.SimpleAttribute;
import com.nearco.cc.model.SimplePage;
import com.nearco.cc.processor.SimplePageProcessor;

import us.codecraft.webmagic.Spider;

public class Main {

	public static void main(String[] args) throws IOException {
		com.alibaba.dubbo.container.Main.main(args);
		
		/* Document doc = Jsoup.connect("http://127.0.0.1/backend/image").get();
		 Elements trs = doc.select("table.table_list  tr.table_item");
		  for (Element src : trs) {
	            System.out.println(src);
	     }
		 */
		
		 
		/*
		 SimplePageProcessor pageProcessor =  new SimplePageProcessor();
		 SimplePage simplePage = new SimplePage();
		 
		 simplePage.setStartUrl("http://127.0.0.1/backend/image");
		 List<SimpleAttribute> properties = new ArrayList<SimpleAttribute>();
		 SimpleAttribute attribute = new SimpleAttribute();
		 attribute.setName("title");
		 attribute.setSelectType(SelectType.css);
		 attribute.setSelectMethod(SelectMethod.text);
		 attribute.setSelector("td:eq(0)");
		 
		 properties.add(attribute);
		 simplePage.setAttributes(properties);
		 simplePage.setCrawlerType(CrawlerType.list);
		 simplePage.setListNodeSelector("table.table_list  tr.table_item");
		 simplePage.addRequestRegex("http://localhost/backend/image\\?page=\\d+");
		 pageProcessor.setSimplePage(simplePage);
		 
		 Spider.create(pageProcessor).addUrl(simplePage.getStartUrl()).thread(5).run();
		 
		*/
	/*	 SimplePageProcessor pageProcessor =  new SimplePageProcessor();
		 SimplePage simplePage = new SimplePage();
		 
		 
		 List<SimpleAttribute> properties = new ArrayList<SimpleAttribute>();
		 SimpleAttribute attribute = new SimpleAttribute();
		 attribute.setName("title");
		 attribute.setSelectType(SelectType.css);
		 attribute.setSelectMethod(SelectMethod.text);
		 attribute.setSelector(".repohead-details-container h1.public strong a");
		 
		 SimpleAttribute attribute2 = new SimpleAttribute();
		 attribute2.setName("watch");
		 attribute2.setSelectType(SelectType.css);
		 attribute2.setSelectMethod(SelectMethod.text);
		 attribute2.setSelector("ul.pagehead-actions .social-count");
		 
		 properties.add(attribute);
		 properties.add(attribute2);
		 simplePage.setAttributes(properties);
		 
		 simplePage.addRequestRegex("https://github\\.com/code4craft/\\w+");
		 pageProcessor.setSimplePage(simplePage);
		 
		 Spider.create(pageProcessor).addUrl(simplePage.getStartUrl()).thread(5).run();*/
	}

}
