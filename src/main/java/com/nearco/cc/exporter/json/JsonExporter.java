package com.nearco.cc.exporter.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.nearco.cc.exporter.ESDataExporter;

public class JsonExporter extends ESDataExporter {

	private static final Logger logger = Logger.getLogger(JsonExporter.class);
	protected Writer writer;
	private long currentCount = 0;

	public JsonExporter(Client client, HttpServletRequest request) {
		super(client, request);
	}

	@Override
	public void write(File outputFile, SearchResponse response) {
		final String scrollId = response.getScrollId();
		if (isFirstScan()) {
		}
		getWriter(outputFile);
		final SearchHits hits = response.getHits();

        final int size = hits.getHits().length;
        currentCount += size;
        logger.info("scrollId: " + scrollId + ", totalHits: "
                + hits.totalHits() + ", hits: " + size + ", current: "
                + currentCount);

        try {
            for (final SearchHit hit : hits) {
                final String source = hit.sourceAsString();
                writer.append(source).append('\n');
            }
            writer.flush();
            close();
        } catch (final Exception e) {
        }
		
	}
	
	private Writer getWriter(File outputFile){
		if(writer!=null){
			return writer;
		}
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
	                  new FileOutputStream(outputFile), "UTF-8"));
			return writer;
		} catch (final Exception e) {
			e.printStackTrace();
			return writer;
		}
	}
	
	  private void close() {
          if (writer != null) {
              try {
                  writer.close();
              } catch (final IOException e) {
              }
          }
      }

}
