package com.nearco.cc.exporter;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.client.Client;

import com.nearco.cc.exporter.csv.CsvExporter;
import com.nearco.cc.exporter.json.JsonExporter;
import com.nearco.cc.exporter.xsl.XlsExporter;

public enum ExportType {
	EXCEL(1) {
		@Override
		public String exportType() {
			return "application/vnd.ms-excel";
		}

		@Override
		public ESDataExporter dataExporter(final Client client, final HttpServletRequest request) {
			return new XlsExporter(client, request, false);
		}

		@Override
		public String fileName(final HttpServletRequest request) {
			final String index = request.getParameter("index") != null ? request.getParameter("index")
					: request.getParameter("name");
			if (index == null) {
				return "_all.xls";
			}
			final String type = request.getParameter("type") != null ? request.getParameter("type")
					: request.getParameter("group");
			if (type == null) {
				return index + ".xls";
			}
			return index + "_" + type + ".xls";
		}
	},
	EXCEL2007(2) {
		@Override
		public String exportType() {
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		}

		@Override
		public ESDataExporter dataExporter(final Client client, final HttpServletRequest request) {
			return new XlsExporter(client, request, true);
		}

		@Override
		public String fileName(final HttpServletRequest request) {
			final String index = request.getParameter("index") != null ? request.getParameter("index")
					: request.getParameter("name");
			if (index == null) {
				return "_all.xlsx";
			}
			final String type = request.getParameter("type") != null ? request.getParameter("type")
					: request.getParameter("group");
			if (type == null) {
				return index + ".xlsx";
			}
			return index + "_" + type + ".xlsx";
		}
	},
	CSV(3) {
		@Override
		public String exportType() {
			return "text/csv";
		}

		@Override
		public ESDataExporter dataExporter(final Client client, final HttpServletRequest request) {
			return new CsvExporter(client, request);
		}

		@Override
		public String fileName(final HttpServletRequest request) {
			final String index = request.getParameter("index") != null ? request.getParameter("index")
					: request.getParameter("name");
			if (index == null) {
				return "_all.csv";
			}
			final String type = request.getParameter("type") != null ? request.getParameter("type")
					: request.getParameter("group");
			if (type == null) {
				 return index + ".csv";
			}
	        return index + "_" + type + ".csv";
		}
	},
	JSON(4) {
		@Override
		public String exportType() {
			return "application/json";
		}

		@Override
		public ESDataExporter dataExporter(final Client client, final HttpServletRequest request) {
			return new JsonExporter(client, request);
		}

		@Override
		public String fileName(final HttpServletRequest request) {
			final String index = request.getParameter("index") != null ? request.getParameter("index")
					: request.getParameter("name");
			if (index == null) {
				return "_all.json";
			}
			final String type = request.getParameter("type") != null ? request.getParameter("type")
					: request.getParameter("group");
			if (type == null) {
				 return index + ".json";
			}
	        return index + "_" + type + ".json";
		}
	};
	

	private int index;

	ExportType(final int index) {
		this.index = index;
	}

	public int index() {
		return index;
	}

	public abstract String exportType();

	public abstract String fileName(HttpServletRequest request);

	public abstract ESDataExporter dataExporter(Client client, HttpServletRequest request);
}
