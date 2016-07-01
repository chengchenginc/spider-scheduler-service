package com.nearco.cc.rest.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nearco.cc.exporter.ESDataExporter;
import com.nearco.cc.exporter.ExportType;
import com.nearco.cc.model.PagerModel;
import com.nearco.cc.rest.APIService;
import com.nearco.cc.rest.DataService;
import com.nearco.cc.search.service.SearchService;
import com.nearco.cc.util.RequestUtil;
import com.nearco.cc.util.ResponseUtil;

@Service
public class DataServiceImpl extends APIService implements DataService {

	@Autowired
	private SearchService searchServiceImpl;

	@Override
	public PagerModel getPagerList(String name, String group, PagerModel pager) {
		if (!searchServiceImpl.isExists(name, group)) {
			return pager;
		}
		return searchServiceImpl.search(name, group, pager);
	}

	@Override
	public Map<?, ?> delete(String name, String group) {
		searchServiceImpl.clearData(name, group);
		return success(null, "ok");
	}

	@Override
	public void export(String name, String group, String format, HttpServletRequest request,
			HttpServletResponse response) {
		final ExportType exportType = RequestUtil.getExportType(request);
		
		try{
			if (exportType == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "unsupport format:" + format);
				return;
			}
			final ESDataExporter dataExporter = exportType.dataExporter(searchServiceImpl.getClient(), request);
			TimeValue scrollId = TimeValue.timeValueMinutes(1);
			SearchRequestBuilder prepareSearch = searchServiceImpl.getClient().prepareSearch(name).setTypes(group);
			prepareSearch.setScroll(scrollId);
			prepareSearch.setSearchType("scan");
			SearchResponse scrollResponse = prepareSearch.execute().actionGet();
			Long count = scrollResponse.getHits().getTotalHits();// 第一次不返回数据
			String fileName = exportType.fileName(request);
			File outputFile = new File(fileName);
			for (int i = 0, sum = 0; sum < count; i++) {
				scrollResponse = searchServiceImpl.getClient().prepareSearchScroll(scrollResponse.getScrollId())
						.setScroll(TimeValue.timeValueMinutes(1)).execute().actionGet();
				sum += scrollResponse.getHits().hits().length;
				dataExporter.write(outputFile, scrollResponse);
			}
			ResponseUtil.writeResponse(request, response, outputFile);
			outputFile.delete();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
