package com.izhoujie.jsoupSpider.export;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin@izhoujie.com
 * 
 *         2016年9月12日 15:50:18
 *
 *         数据导出
 */
public class IoHelper {

    // 获取要扫描的文件内容
    public static List<String> getIdList(String pathname) {
	File file = new File(pathname);

	String linestr = "";
	List<String> list = new ArrayList<String>();
	BufferedReader reader = null;
	try {
	    reader = new BufferedReader(new FileReader(file));
	    try {
		while ((linestr = reader.readLine()) != null) {
		    list.add(linestr);
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} finally {
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	return list;
    }

    // 保存结果
    public static void writeResult(String str) {

	FileWriter writer = null;
	try {
	    writer = new FileWriter("F:/CRM_result/result.csv", true);
	    writer.write(str);
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (writer != null) {
		try {
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    public static void writeResultByName(String str, String name) {

	String tag = (System.currentTimeMillis() + "").substring(10);

	String filePath = "F:/sina_weibo/" + name + "-" + tag + "-message.csv";
	BufferedWriter bw = null;
	try {
	    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "GBK"));
	    bw.write(str);
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (bw != null) {
		try {
		    bw.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    // 保存每个账号对应的关注的id列表
    public static void saveIdList(List<String> list, String filename) {
	FileWriter writer = null;
	BufferedWriter bfwriter = null;
	String id = "";
	try {
	    writer = new FileWriter(filename, false);
	    bfwriter = new BufferedWriter(writer);
	    for (int i = 0; i < list.size(); i++) {
		id = list.get(i) + "\n";
		bfwriter.write(id);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (bfwriter != null) {
		try {
		    bfwriter.close();
		} catch (IOException e) {
		}
	    }
	}
    }
}
