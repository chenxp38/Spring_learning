package com.example.demo.pojo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
    //服务器IP地址
    private static String ADDR = "101.132.157.149";
    //端口
    private static int PORT = 6379;
    //连接超时的时间　　
    private static int TIMEOUT = 10000;
    //密码
    private static String AUTH = "root";
    //数据库模式是16个数据库（0~15），这里设置第一个为默认数据库
    public static final int DEFAULT_DATABASE = 0;
    //连接实例的最大连接数
    private static int MAX_ACTIVE = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
    private static int MAX_WAIT = 10000;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    //创建一个redis数据库连接池
    private static JedisPool jedisPool = null;

    //初始化Redis连接池（静态代码块）
    static {
        try {
            //新建连接池的配置参数
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            //指定连接实例的最大连接数
            jedisPoolConfig.setMaxTotal(MAX_ACTIVE);
            //指定一个pool最多有多少个状态为idle(空闲的)的jedis实例
            jedisPoolConfig.setMaxIdle(MAX_IDLE);
            //指定等待可用连接的最大时间，单位毫秒
            jedisPoolConfig.setMaxWaitMillis(MAX_WAIT);
            //在borrow一个jedis实例时，是否提前进行validate操作
            jedisPoolConfig.setTestOnBorrow(TEST_ON_BORROW);
            //初始化Redis连接池
            jedisPool = new JedisPool(jedisPoolConfig, ADDR, PORT, TIMEOUT,AUTH,DEFAULT_DATABASE);
            //jedisPool = new JedisPool(jedisPoolConfig, ADDR, PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取Jedis实例
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis jedis = jedisPool.getResource();
                System.out.println("redis--服务正在运行: "+jedis.ping());
                return jedis;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //释放资源
    public static void returnResource(final Jedis jedis) {
        if(jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
