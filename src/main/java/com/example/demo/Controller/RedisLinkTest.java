package com.example.demo.Controller;

import com.example.demo.pojo.RedisUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedisLinkTest {
    //@Test表示这个方法是单元测试的方法
    //连接并添加String类型数据
    @Test
    public static  void saveSession(String key, String value) {
        //直接连接redis数据库
        Jedis jedis = new Jedis("101.132.157.149",6379);
        //设置连接密码
        jedis.auth("root");
        //添加String类型数据
        jedis.set(key, value);
        //输出添加的数据（根据键，输出对应的值）
        System.out.println(jedis.get(key));
        //删除String类型数据（根据键删除）
        //jedis.del("field1");
        //输出数据，查看是否删除成功
        //System.out.println(jedis.get("field1"));
    }

    @Test
    public static String getSessionValue(String key) {
        //直接连接redis数据库
        Jedis jedis = new Jedis("101.132.157.149",6379);
        //设置连接密码
        jedis.auth("root");
        //添加String类型数据
        //删除String类型数据（根据键删除）
        //jedis.del("field1");
        //输出数据，查看是否删除成功
        return jedis.get(key);
    }

}