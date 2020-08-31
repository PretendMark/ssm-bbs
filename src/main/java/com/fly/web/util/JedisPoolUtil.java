package com.fly.web.util;

import java.util.HashSet;
import java.util.Set;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;


/**
 * Jedis 连接池
 * @author YuLF
 *
 */
public class JedisPoolUtil {
	/* redis连接池 */
	private static volatile JedisPool jedisPool = null;
	/* redis的一些配置信息 */
	private static JedisPoolConfig poolConfig = null;
	/* redis哨兵,方便主服宕机之后随时获取最新的主服master ip端口 */
	private static JedisSentinelPool jedisSentinelPool = null;

	private static final String REDIS_CONFIG = "redis.properties";

	/* 类加载时初始化配置 */
	static {
		/* 值可以放到配置文件动态读取 */
		poolConfig = new JedisPoolConfig();

		poolConfig.setMaxIdle( CharacterUtil.parseInt(PropertiesUtil.getProperty("redis.MaxIdle",REDIS_CONFIG),32) );                    /* 空闲连接 */
		poolConfig.setMaxTotal( CharacterUtil.parseInt(PropertiesUtil.getProperty("redis.MaxTotal",REDIS_CONFIG),999) );                  /* 最大jedis连接数 */
		poolConfig.setMaxWaitMillis( CharacterUtil.parseInt(PropertiesUtil.getProperty("redis.MaxWaitMillis",REDIS_CONFIG),100 * 1000) );      /* 最大等待时间100秒 */
		poolConfig.setTestOnBorrow( CharacterUtil.parseBoolean(PropertiesUtil.getProperty("redis.TestOnBorrow",REDIS_CONFIG)) );             /* 检查有效的连通 */
		/**
		 * 目前只有一台redis 所以哨兵模式相关的代码先注释掉
		 */
		//Set<String> set = new HashSet<>();
		/* set中放的是哨兵的Ip和端口,可以放到配置文件动态读取 */
		//set.add( "192.168.174.130:6399" );
		//jedisSentinelPool = new JedisSentinelPool( "master_redis", set, poolConfig, 60000 );
	}

	private JedisPoolUtil()
	{
	}



	public static JedisPool getJedisPoolInstance()
	{
		if ( null == jedisPool )
		{
			synchronized (JedisPoolUtil.class ) {
				if ( null == jedisPool )
				{
					//HostAndPort ipAndPort = jedisSentinelPool.getCurrentHostMaster();
					jedisPool = new JedisPool( poolConfig, PropertiesUtil.getProperty("redis.ip",REDIS_CONFIG), CharacterUtil.parseInt(PropertiesUtil.getProperty("redis.port",REDIS_CONFIG),6379) );
				}
			}
		}
		return(jedisPool);
	}


	public static void release( Jedis jedis )
	{
		if ( jedis != null )
		{
			/*
			 * 据说 2.8之后returnResources(放回到连接池中的方法删掉了)
			 * 现在jedis.close可以直接放回到连接池,不过我刚学测试没看源码
			 */
			jedis.close();
		}
	}
}
