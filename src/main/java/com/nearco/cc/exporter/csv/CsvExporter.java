package com.nearco.cc.exporter.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import com.nearco.cc.exporter.ESDataExporter;
import com.nearco.cc.util.MapUtils;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.CsvWriter;

public class CsvExporter extends ESDataExporter {

	private static final ESLogger logger = Loggers.getLogger(CsvExporter.class);

	private final String charsetName;

	private final CsvConfig csvConfig;

	private boolean appnedHeader;

	private Set<String> headerSet;

	private boolean modifiableFieldSet;

	private long currentCount = 0;
	
	private CsvWriter writer;

	public CsvExporter(Client client, HttpServletRequest request) {
		super(client, request);
		csvConfig = new CsvConfig(',', '"', '"');
		csvConfig.setQuoteDisabled(false);
		csvConfig.setEscapeDisabled(false);
		csvConfig.setNullString("");
		csvConfig.setIgnoreLeadingWhitespaces(true);
		csvConfig.setIgnoreTrailingWhitespaces(true);

		appnedHeader = true;
		charsetName = request.getCharacterEncoding()!=null ? request.getCharacterEncoding() :"UTF-8";

		headerSet = new LinkedHashSet<String>();
		modifiableFieldSet = true;
	}

	private CsvWriter getWriter(File outputFile){
		if(writer!=null){
			return writer;
		}
		try {
			writer = new CsvWriter(
			        new BufferedWriter(new OutputStreamWriter(
			                new FileOutputStream(outputFile), charsetName)),
			        csvConfig);
			return writer;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
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
                final Map<String, Object> sourceMap = hit.sourceAsMap();
                final Map<String, Object> dataMap = new HashMap<String, Object>();
                MapUtils.convertToFlatMap("", sourceMap, dataMap);
                for (final String key : dataMap.keySet()) {
                    if (modifiableFieldSet && !headerSet.contains(key)) {
                        headerSet.add(key);
                    }
                }
                if (appnedHeader) {
                    final List<String> headerList = new ArrayList<String>(
                            headerSet.size());
                    headerList.addAll(headerSet);
                    writer.writeValues(headerList);
                    appnedHeader = false;
                }

                final List<String> dataList = new ArrayList<String>(
                        dataMap.size());
                for (final String name : headerSet) {
                    final Object value = dataMap.get(name);
                    dataList.add(value != null ? value.toString() : null);
                }
                writer.writeValues(dataList);
            }

			writer.flush();
            close();
		} catch (final Exception e) {

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
