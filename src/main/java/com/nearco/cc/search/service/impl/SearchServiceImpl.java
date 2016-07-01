package com.nearco.cc.search.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.DisposableBean;

import com.nearco.cc.Constants;
import com.nearco.cc.model.PagerModel;
import com.nearco.cc.search.service.SearchService;

import us.codecraft.webmagic.ResultItems;

public class SearchServiceImpl implements SearchService, DisposableBean {

	private Client client;
	private BulkProcessor bulkProcessor;
	private String hosts;
	private String cluster;

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public Client getClient() {
		if (this.client == null) {
			if (this.hosts.isEmpty()) {
				// throw new Exception("hosts未设置!");
				return null;
			}
			String[] hostArr = this.hosts.split(",");
			Settings settings = Settings.settingsBuilder().put("cluster.name", this.getCluster()).build();
			this.client = TransportClient.builder().settings(settings).build();
			for (String host : hostArr) {
				String[] address = host.split(":");
				try {
					((TransportClient) client).addTransportAddress(new InetSocketTransportAddress(
							InetAddress.getByName(address[0]), Integer.parseInt(address[1])));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}

		}
		return this.client;
	}

	protected BulkProcessor getBulkProcessor() {
		if (this.bulkProcessor == null) {
			this.bulkProcessor = BulkProcessor.builder(this.getClient(), new BulkProcessor.Listener() {
				@Override
				public void afterBulk(long arg0, BulkRequest arg1, BulkResponse arg2) {

				}

				@Override
				public void afterBulk(long arg0, BulkRequest arg1, Throwable arg2) {

				}

				@Override
				public void beforeBulk(long arg0, BulkRequest arg1) {

				}

			}).setBulkActions(1000).setFlushInterval(TimeValue.timeValueSeconds(5)).build();
		}
		return this.bulkProcessor;
	}

	@Override
	public PagerModel search(String index, String type, PagerModel pager) {
		int from = (pager.getPage() - 1) * pager.getLimit();
		List<Map<String, Object>> documents = new ArrayList<>();
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		if(pager.getQueryMap()!=null){
			queryBuilder = QueryBuilders.boolQuery();
			setQueryBuilder((BoolQueryBuilder) queryBuilder,pager.getQueryMap());
		}
		SearchResponse response = this.getClient().prepareSearch(index).setTypes(type).setFrom(from)
				.setQuery(queryBuilder).setSize(pager.getLimit()).execute().actionGet();
		for (SearchHit hit : response.getHits().getHits()) {
			documents.add(hit.getSource());
		}
		pager.setTotal(new Long(response.getHits().getTotalHits()).intValue());
		pager.setData(documents);
		return pager;
	}
	
	private void setQueryBuilder(BoolQueryBuilder queryBuilder,Map<String,?> query){
		Iterator<?> iterator = query.entrySet().iterator();
		while(iterator.hasNext()){
			@SuppressWarnings("unchecked")
			Entry<String,?> entry = (Entry<String, ?>) iterator.next();
			queryBuilder.must(QueryBuilders.termsQuery(entry.getKey(), entry.getValue().toString().toLowerCase()));
		}
	}

	@Override
	public void clearData(String index, String type) {
		DeleteRequest request = new DeleteRequest();
		request.index(index).type(type);
		this.getClient().delete(request);
	}

	@Override
	public void bulkIndex(String index, String type, boolean isList, ResultItems resultItems) {
		if (isList) {
			for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
				if (entry.getKey() == Constants.SPIDER_LIST_MUTI_FIELD) {
					@SuppressWarnings("unchecked")
					List<ConcurrentHashMap<String, String>> list = (List<ConcurrentHashMap<String, String>>) entry
							.getValue();
					for (ConcurrentHashMap<String, String> obj : list) {
						this.getBulkProcessor().add(new IndexRequest(index, type).source(obj));
					}
				}
			}
		} else {
			for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
				this.getBulkProcessor().add(new IndexRequest(index, type).source(entry));
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		this.client.close();
	}

	@Override
	public boolean isExists(String index, String type) {
		TypesExistsResponse  response = 
	            getClient().admin().indices()
	            .typesExists(new TypesExistsRequest(new String[]{index}, type)
	            ).actionGet();
		return response.isExists();
	}

}
