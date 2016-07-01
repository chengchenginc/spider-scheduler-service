package com.nearco.cc.exporter;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.jboss.netty.buffer.ChannelBuffer;

public abstract class ESDataExporter {

    protected HttpServletRequest request;

    protected ChannelBuffer channelBuffer;

    protected Client client;

    private boolean firstScan = true;

    public ESDataExporter(final Client client, final HttpServletRequest request) {
        this.client = client;
        this.request = request;
    }

    protected boolean isFirstScan() {
        if (firstScan) {
            firstScan = false;
            return true;
        }
        return false;
    }

    public abstract void write(File outputFile,SearchResponse response);
    

}