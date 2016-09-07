package com.izhoujie.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * @author admin@izhoujie.com
 * 
 *         Map的一些测试
 *
 */
public class Map_test {
    public static void main(String[] args) {
	HashMap<Integer, String> map = new HashMap<Integer, String>();
	HashMap<Integer, String> mapmore = new HashMap<Integer, String>();

	map.put(1, "1");
	map.put(2, "2");
	map.put(3, "3");
	map.put(4, "4");
	map.put(5, "5");
	map.put(6, "6");

	mapmore.put(1, "11");
	mapmore.put(2, "22");
	mapmore.put(3, "33");
	mapmore.put(4, "4");
	mapmore.put(5, "5");
	mapmore.put(6, "6");
	mapmore.put(7, "7");
	mapmore.put(10, "10");

	map.putAll(mapmore);

	for (int i = 0; i < 10000000; i++) {
	    map.put(i, "" + i);
	}
	long star = System.currentTimeMillis();
	// EntrySet 方式遍历
	Iterator<Entry<Integer, String>> iterator = map.entrySet().iterator();
	while (iterator.hasNext()) {
	    Entry<Integer, String> entry = iterator.next();
	    entry.getKey();
	    entry.getValue();

	    // System.out.println(entry.getKey() + "-^-" + entry.getValue());
	}
	// KeySet 方式遍历
	// Iterator<Integer> iterator2 = map.keySet().iterator();
	// while (iterator2.hasNext()) {
	// Integer key = iterator2.next();
	//
	// map.get(key);
	// System.out.println(key + "-^-" + map.get(key));

	// }
	long end = System.currentTimeMillis();
	System.out.println(end - star);
    }
}
