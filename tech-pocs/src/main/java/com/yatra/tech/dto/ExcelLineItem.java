package com.yatra.tech.dto;

import java.util.Map;

import com.ebay.xcelite.annotations.AnyColumn;

public class ExcelLineItem extends BaseDTO {

	@AnyColumn
	private Map<String, Object> dynamicCols;

	public Map<String, Object> getDynamicCols() {
		return dynamicCols;
	}

	public void setDynamicCols(Map<String, Object> dynamicCols) {
		this.dynamicCols = dynamicCols;
	}
}
