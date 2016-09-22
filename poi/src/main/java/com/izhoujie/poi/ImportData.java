package com.izhoujie.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportData {

    public static void main(String[] args) throws Exception {
	String uri = "F:/hhc/pants中奖-0913.xlsx";
	File file = new File(uri);
	InputStream is = new FileInputStream(file);

	POIFSFileSystem fs = new POIFSFileSystem();

	// 以流创建excel文件对象
	XSSFWorkbook workbook = new XSSFWorkbook(is);
	// excel的sheet数量
	int num1 = workbook.getNumberOfSheets();
	// excel的sheet取值范围，跟getNumberOfSheets()基本一样，前者获取总数n，或者获取可达范围值n-1(0~n-1)
	int num2 = workbook.getNumberOfNames();
	System.out.println(num1);
	System.out.println(num2);
	// 获取当前活跃/正在操作的sheet索引值
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
	// 获取第一行
	XSSFRow xssfRow = sheet.getRow(0);
	// 获取sheet的最大有效行数
	int lastRowNum = sheet.getLastRowNum();
	// 获取行样式
	XSSFCellStyle rowStyle = xssfRow.getRowStyle();
	// 当前行的有效单元格数量
	short cellNum = xssfRow.getLastCellNum();
	// 获取第一个单元格
	XSSFCell xssfCell = xssfRow.getCell(0);
	// 获取单元格样式
	XSSFCellStyle cellStyle = xssfCell.getCellStyle();

	// 遍历一个sheet
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
