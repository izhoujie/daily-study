package com.izhoujie.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisTest {

	// 非切片额客户端链接
	private Jedis jedis;
	// 非切片连接池
	private JedisPool jedisPool;
	// 切片额客户端链接
	private ShardedJedis sharedJedis;
	// 切片连接池
	private ShardedJedisPool sharedJedisPool;

	public RedisTest() {

		initialPool();
		initialSharedPool();
		jedis = jedisPool.getResource();
		sharedJedis = sharedJedisPool.getResource();

	}

	/**
	 * 初始化非切片池
	 */
	private void initialPool() {

		// 池的基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(3);
		config.setMaxWaitMillis(10001);
		config.setTestOnBorrow(false);

		jedisPool = new JedisPool(config, "******", 6379, 0);
	}

	/**
	 * 初始化切片池
	 */
	private void initialSharedPool() {

		// 池的基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(3);
		config.setMaxWaitMillis(10001);
		config.setTestOnBorrow(false);

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

		shards.add(new JedisShardInfo("******", 6379, "master"));

		sharedJedisPool = new ShardedJedisPool(config, shards);

	}

	private void keyTest() {

		System.out.println(jedis.get("name"));

		System.out.println();
		jedis.set("test", "fromJava");
		Long size = jedis.dbSize();
		System.out.println(size);

		Long db = jedis.getDB();
		System.out.println(db);

		String info = jedis.info();
		System.out.println(info);

		Long lastsave = jedis.lastsave();
		System.out.println(lastsave);

		String ping = jedis.ping();
		System.out.println(ping);

		String randomKey = jedis.randomKey();
		System.out.println(randomKey);

		List<String> time = jedis.time();

		for (String string : time) {
			System.err.println(string);
		}
		jedis.lpush("mylist", "00");
		jedis.rpush("mylist", "67");
		jedis.rpush("mylist", "fds");
		jedis.rpush("mylist", "s8y3");
		jedis.lpush("mylist", "22");
		jedis.lpush("mylist", "0-0");
		jedis.lpush("mylist", "---");
		jedis.lpush("mylist", "09");
		Long lpush = jedis.lpush("mylist", "9-9");

		System.out.println("返回值：" + lpush);

		List<String> lrange = jedis.lrange("mylist", 0, -1);

		for (String string : lrange) {
			System.out.println(string);
		}

		Jedis shard1 = sharedJedis.getShard("");
		int numIdle = sharedJedisPool.getNumIdle();

		Long llen = jedis.llen("mylist");
		System.out.println(llen);

		for (int i = 0; i < jedis.llen("mylist"); i++) {

			System.out.println("list-" + i + "):" + jedis.lindex("mylist", i));

		}

		System.out.println(numIdle);

		System.out.println(shard1);

		// List<String> configlist = jedis.configGet("*");
		// for (String str : configlist) {
		// System.out.println(str);
		// }

	}

	public static void main(String[] args) {
		RedisTest redisTest = new RedisTest();

		redisTest.keyTest();
	}
}
