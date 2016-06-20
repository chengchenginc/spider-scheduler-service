package com.nearco.cc;

import java.util.List;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.downloader.HttpClientDownloader;


public class AppTest 
    extends TestCase
{
	
	public static void main(String[] args) {
		Request request = new Request();
    	request.setUrl("http://www.qcxche.com/backend/image");
    	HttpClientDownloader downloader = new  HttpClientDownloader();
    	Page page =  downloader.download(request, null);
    	List<String> links = page.getHtml().links().regex("(http://www.qcxche\\.com/\\w+/\\w+)").all();
    	System.out.println(JSON.toJSON(links));
	}
   
	//class task implements task
}
