package com.nearco.cc.exporter.xsl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.SpecialPermission;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import com.nearco.cc.exporter.ESDataExporter;
import com.nearco.cc.util.MapUtils;

public class XlsExporter extends ESDataExporter {
	private static final Logger logger = Logger.getLogger(XlsExporter.class);

	private static final int SXSSF_FLUSH_COUNT = 1000;

	private static final String DEFAULT_HEADER_COLUMN = "-";

	private boolean appnedHeader;

	private Set<String> headerSet;

	private boolean modifiableFieldSet;

	private final boolean isExcel2007;

	private Workbook workbook;

	private Sheet sheet;

	private int currentCount = 0;

	public XlsExporter(final Client client, final HttpServletRequest request, final boolean isExcel2007) {
		super(client, request);
		headerSet = new LinkedHashSet<String>();
		modifiableFieldSet = true;
		appnedHeader = true;
		this.isExcel2007 = isExcel2007;
	}

	@Override
	public void write(final File outputFile, final SearchResponse response) {
		final String scrollId = response.getScrollId();
		if (isFirstScan()) {
			// 创建excel
			createSheet();
		}
		try {
			final SearchHits hits = response.getHits();
			final int size = hits.getHits().length;
			logger.info("scrollId: " + scrollId + ", totalHits: " + hits.totalHits() + ", hits: " + size + ", current: "
					+ (currentCount + size));

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
					final Row headerRow = sheet.createRow(currentCount);
					int count = 0;
					for (final String value : headerSet) {
						final Cell cell = headerRow.createCell(count);
						cell.setCellValue(value);
						count++;
					}
					appnedHeader = false;
				}

				currentCount++;
				final Row row = sheet.createRow(appnedHeader ? currentCount + 1 : currentCount);

				int count = 0;
				for (final String name : headerSet) {
					final Object value = dataMap.get(name);
					final Cell cell = row.createCell(count);
					if (value != null && value.toString().trim().length() > 0) {
						cell.setCellValue(value.toString());
					} else {
						cell.setCellValue(DEFAULT_HEADER_COLUMN);
					}
					count++;
				}

				flushSheet(currentCount, sheet);
			}

			// 写入文件
			try (OutputStream stream = new BufferedOutputStream(new FileOutputStream(outputFile))) {
				final SecurityManager sm = System.getSecurityManager();
				if (sm != null) {
					sm.checkPermission(new SpecialPermission());
				}
				AccessController.doPrivileged(new PrivilegedAction<Void>() {
					@Override
					public Void run() {
						try {
							workbook.write(stream);
						} catch (IOException e) {
							throw new ElasticsearchException(e);
						}
						return null;
					}
				});

				stream.flush();
			} finally {
				disposeWorkbook(workbook);
			}
		} catch (final Exception e) {
			// deleteOutputFile(outputFile);
		}
	}

	private void createSheet() {
		if (isExcel2007) {
			final SecurityManager sm = System.getSecurityManager();
			if (sm != null) {
				sm.checkPermission(new SpecialPermission());
			}
			workbook = AccessController.doPrivileged(new PrivilegedAction<Workbook>() {
				@Override
				public Workbook run() {
					return new SXSSFWorkbook(-1); // turn off auto-flushing and
													// accumulate all rows in
													// memory
				}
			});
			sheet = AccessController.doPrivileged(new PrivilegedAction<Sheet>() {
				@Override
				public Sheet run() {
					return workbook.createSheet();
				}
			});
		} else {
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet();
		}
	}

	private void flushSheet(final int currentCount, final Sheet sheet) throws IOException {
		if (sheet instanceof SXSSFSheet) {
			if (currentCount % SXSSF_FLUSH_COUNT == 0) {
				((SXSSFSheet) sheet).flushRows(0);
			}
		}
	}

	private void disposeWorkbook(final Workbook workbook) {
		if (workbook instanceof SXSSFWorkbook) {
			((SXSSFWorkbook) workbook).dispose();
		}
	}

	private void deleteOutputFile(File outputFile) {
		if (outputFile != null && !outputFile.delete()) {
			logger.warn("Failed to delete: " + outputFile.getAbsolutePath());
		}
	}

}
