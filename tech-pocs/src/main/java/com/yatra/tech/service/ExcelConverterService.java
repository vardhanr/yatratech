package com.yatra.tech.service;

import java.io.File;
import java.util.List;

public interface ExcelConverterService {

	public <T> List<T> readExcelData(Class<T> objectType, File excel, String sheetName);

	public <T> File writeExcelData(Class<T> objectType, String fileName, String sheetName, List<T> data);
}
