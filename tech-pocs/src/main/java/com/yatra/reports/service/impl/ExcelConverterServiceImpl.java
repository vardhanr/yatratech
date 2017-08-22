package com.yatra.reports.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;
import com.yatra.reports.service.ExcelConverterService;

@Service("excelConverterService")
public class ExcelConverterServiceImpl implements ExcelConverterService {

	@Override
	public <T> List<T> readExcelData(Class<T> objectType, File excel, String sheetName) {
		Xcelite xcelite = new Xcelite(excel);
		XceliteSheet sheet = xcelite.getSheet(sheetName);
		SheetReader<T> reader = sheet.getBeanReader(objectType);
		return (List<T>) reader.read();
	}

	@Override
	public <T> File writeExcelData(Class<T> objectType, String fileName, String sheetName, List<T> data) {
		Xcelite xcelite = new Xcelite();
		XceliteSheet sheet = xcelite.createSheet(sheetName);
		SheetWriter<T> writer = sheet.getBeanWriter(objectType);
		writer.write(data);
		File excel = new File(fileName);
		xcelite.write(excel);
		return excel;
	}

}
