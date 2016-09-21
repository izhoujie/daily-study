package com.izhoujie.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportData {

    public static void main(String[] args) throws Exception {
	String uri = "F:/hcc/pants中奖-0913.xlsx";
	File file = new File(uri);
	InputStream is = new FileInputStream(file);

	// 以流创建excel文件对象
	XSSFWorkbook workbook = new XSSFWorkbook(is);
	// excel的sheet数量
	int num1 = workbook.getNumberOfSheets();
	// excel的sheet取值范围，跟getNumberOfSheets()基本一样，前者获取总数n，或者获取可达范围值n-1(0~n-1)
	int num2 = workbook.getNumberOfNames();
	System.out.println(num1);
	System.out.println(num2);
	int index = workbook.getActiveSheetIndex();
	System.out.println(index);
	// 获取第一个sheet的名字
	String name1 = workbook.getSheetAt(0).getSheetName();
	System.out.println(name1);
	String name2 = workbook.getSheetAt(1).getSheetName();
	System.out.println(name2);
	String name3 = workbook.getSheetAt(2).getSheetName();
	System.out.println(name3);

	// 获取第一个sheet
	XSSFSheet sheet = workbook.getSheetAt(0);
	// 获取sheet的最大行数
	int num = sheet.getLastRowNum();
	System.out.println(num);

	// 遍历sheet
	for (Row row : sheet) {
	    String value = "";
	    // 遍历行的各个cell值
	    for (Cell cell : row) {
		String cellValue = cell.getStringCellValue();
		value += cellValue + ",";
	    }
	    System.out.println(value);
	}
    }
}
