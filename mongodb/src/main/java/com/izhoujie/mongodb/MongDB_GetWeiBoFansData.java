package com.izhoujie.mongodb;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * @author admin@izhoujie.com
 * 
 *
 */
public class MongDB_GetWeiBoFansData {
    private static String host = "0.0.0.0";
    private static int port = 10086;
    private static String userName = "username";
    private static String passWord = "password";
    private static String dataBase = "database";

    public static void main(String[] args) {
	MongoClient client = null;
	try {
	    // 获得数据库对象
	    ServerAddress address = new ServerAddress(host, port);

	    // 进行身份认证
	    MongoCredential credential = MongoCredential.createMongoCRCredential(userName, dataBase,
		    passWord.toCharArray());
	    ArrayList<MongoCredential> mongoCredentialList = new ArrayList<MongoCredential>();
	    mongoCredentialList.add(credential);
	    // 获得数据库客户端链接对象，以client进行实际数据操作
	    client = new MongoClient(address, mongoCredentialList);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}

	// 获得目标数据库对象
	DB db = client.getDB("db1");
	// 获得目标数据库下的某一个集合对象
	DBCollection fansData = db.getCollection("set1");
	DBCollection media = db.getCollection("set2");
	DBCollection nameDB = db.getCollection("set3");
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String t1 = "2016-06-01 00:00:00";
	String t2 = "2016-06-29 00:00:00";
	Date date1 = null;
	Date date2 = null;
	try {
	    date1 = format.parse(t1);
	    date2 = format.parse(t2);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	// 创建查询条件对象
	BasicDBObject query = new BasicDBObject();
	BasicDBObject datequery = new BasicDBObject();
	String uid = "0000000000";
	datequery.append("$gte", date1).append("$lte", date2);
	query.put("officialId", uid);
	query.put("postTime", datequery);
	// 查询结果集
	DBCursor cursor = fansData.find(query);

	StringBuffer buffer = new StringBuffer();
	buffer.append("id,").append("userId,").append("nickName,").append("time,").append("type,").append("content,")
		.append("\n");
	// 结果集数据筛选导出
	while (cursor.hasNext()) {
	    DBObject next = cursor.next();
	    String id = (String) next.get("officialId");
	    String userId = (String) next.get("openId");
	    String ninckName = getNickName(nameDB, userId);
	    Date date = (Date) next.get("postTime");
	    String time = format.format(date);
	    String type = (String) next.get("msgType");
	    String content = (String) next.get("content");
	    content = content.replaceAll("MediaId", "start").replaceAll("Content", "start").replaceAll("Event",
		    "start");
	    int start = content.indexOf("<start><![CDATA[");
	    String substring = content.substring(start + 16);
	    int end = substring.indexOf("]");
	    content = substring.substring(0, end);
	    if ("image".equals(type) || "voice".equals(type)) {
		content = getImageUrl(media, content);
	    }
	    buffer.append(id).append(",").append(userId).append(",").append(ninckName).append(",").append(time)
		    .append(",").append(type).append(",").append(content).append(",").append("\n");
	}
	String result = buffer.toString();
	// 导出结果数据到文件
	// IoHelper.writeResultByName(result, uid);
    }

    public static String getNickName(DBCollection nameDB, String userId) {
	BasicDBObject query = new BasicDBObject("_id", userId);
	DBCursor cursor = nameDB.find(query);
	while (cursor.hasNext()) {
	    DBObject next = cursor.next();
	    String url = (String) next.get("nickName");
	    return url;
	}

	return null;
    }

    // 获取media对应的URL
    public static String getImageUrl(DBCollection media, String content) {
	BasicDBObject query = new BasicDBObject("media_id", content);
	DBCursor cursor = media.find(query);
	while (cursor.hasNext()) {
	    DBObject next = cursor.next();
	    String url = (String) next.get("url");
	    return url;
	}
	return null;
    }
}
