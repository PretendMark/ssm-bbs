package com.fly.web.util;


import com.fly.web.controller.BaseController;
import redis.clients.jedis.*;


/**
 * Jedis 连接池
 * @author YuLF
 */
public class JedisPoolFactory extends BaseController {
    /* redis连接池 */
    private static volatile JedisPool jedisPool = null;
    /* redis的一些配置信息 */
    private static JedisPoolConfig poolConfig = null;
    /* redis哨兵,方便主服宕机之后随时获取最新的主服master ip端口 */
    private static JedisSentinelPool jedisSentinelPool = null;


    /**
     * 当对象初始化后从spring中取出Properties对象 赋值Properties对象给当前对象内得静态对象字段
     */
    public static JedisPool getJedisPoolInstance() {
        if (null == jedisPool) {
            synchronized (JedisPoolFactory.class) {
                if (null == jedisPool) {
                    String config = "constant.properties";
                    /* 值可以放到配置文件动态读取 */
                    poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxIdle(CharacterConverter.parseInt(PropertiesHandler.getProperty("redis.MaxIdle", config), 32));                    /* 空闲连接 */
                    poolConfig.setMaxTotal(CharacterConverter.parseInt(PropertiesHandler.getProperty("redis.MaxTotal", config), 999));                  /* 最大jedis连接数 */
                    poolConfig.setMaxWaitMillis(CharacterConverter.parseInt(PropertiesHandler.getProperty("redis.MaxWaitMillis", config), 100 * 1000));      /* 最大等待时间100秒 */
                    poolConfig.setTestOnBorrow(CharacterConverter.parseBoolean(PropertiesHandler.getProperty("redis.TestOnBorrow", config)));             /* 检查有效的连通 */
                    poolConfig.setTestOnCreate(false);
                    jedisPool = new JedisPool(poolConfig, PropertiesHandler.getProperty("redis.ip", config), CharacterConverter.parseInt(PropertiesHandler.getProperty("redis.port", config), 6379));
                }
            }
        }
        return jedisPool;
    }

    public static void release(Jedis jedis) {
        if (jedis != null) {
            /*
             * 2.8之后returnResources(放回到连接池中的方法删掉了)
             */
            jedis.close();
        }
    }
}
