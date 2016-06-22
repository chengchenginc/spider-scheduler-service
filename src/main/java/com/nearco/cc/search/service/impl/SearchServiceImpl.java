package com.nearco.cc.search.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.DisposableBean;

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

	protected Client getClient() {
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

			}).setBulkActions(100).build();
		}
		return this.bulkProcessor;
	}

	@Override
	public PagerModel search(String index, String type, PagerModel pager) {
		return null;
	}

	@Override
	public void clearData(String index, String type) {
		DeleteRequest request = new DeleteRequest();
		request.index(index).type(type);
		this.client.delete(request);
	}

	@Override
	public void bulkIndex(String index, String type, ResultItems resultItems) {
		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			this.getBulkProcessor().add(new IndexRequest(index, type).source(entry));
		}
	}

	@Override
	public void destroy() throws Exception {
		this.client.close();
	}

}
