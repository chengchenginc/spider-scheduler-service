package com.nearco.cc.util;

import static org.elasticsearch.common.unit.TimeValue.parseTimeValue;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.common.unit.TimeValue;

import com.nearco.cc.exporter.ExportType;

public class RequestUtil {
	private static final TimeValue DEFAULT_SCROLL = new TimeValue(60000);

	public static TimeValue getScroll(final HttpServletRequest request) {
		final String scroll = request.getParameter("scroll");
		if (scroll != null) {
			return parseTimeValue(scroll, DEFAULT_SCROLL, "");
		} else {
			return DEFAULT_SCROLL;
		}
	}

	public static ExportType getExportType(final HttpServletRequest request) {
		final String contentType = request.getParameter("format") == null ? request.getHeader("Content-Type")
				: request.getParameter("format");
		if ("text/csv".equals(contentType) || "text/comma-separated-values".equals(contentType)
				|| "csv".equalsIgnoreCase(contentType)) {
			return ExportType.CSV;
		} else if ("application/excel".equals(contentType) || "application/msexcel".equals(contentType)
				|| "application/vnd.ms-excel".equals(contentType) || "application/x-excel".equals(contentType)
				|| "application/x-msexcel".equals(contentType) || "xls".equalsIgnoreCase(contentType)) {
			return ExportType.EXCEL;
		} else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)
				|| "xlsx".equalsIgnoreCase(contentType)) {
			return ExportType.EXCEL2007;
		} else if ("text/javascript".equals(contentType) || "application/json".equals(contentType)
				|| "json".equalsIgnoreCase(contentType)) {
			return ExportType.JSON;
		}

		return null;
	}
}
