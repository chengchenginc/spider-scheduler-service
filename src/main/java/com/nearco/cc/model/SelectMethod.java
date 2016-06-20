package com.nearco.cc.model;

public enum SelectMethod {

	text, allText, tidyText, html, outerHtml, regex;

	@Override
	public String toString() {
		return "/" + super.toString() + "()";
	}

	public String value() {
		return super.toString();
	}
}
