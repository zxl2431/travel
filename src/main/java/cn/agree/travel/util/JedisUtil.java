package cn.agree.travel.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * 包名:com.itheima.utils
 * 作者:Leevi
 * 日期2018-09-10  17:41
 */
public class JedisUtil {
    private static JedisPool pool;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        //配置连接池
        //获取properties配置文件中的信息
        ResourceBundle bundle = ResourceBundle.getBundle("jedisconfig");
        Integer maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
        Integer maxIdle = Integer.parseInt(bundle.getString("maxIdle"));
        config.setMaxTotal(maxTotal);//最大连接数
        config.setMaxIdle(maxIdle);//最大闲置连接数

        String host = bundle.getString("host");
        Integer port = Integer.parseInt(bundle.getString("port"));
        pool = new JedisPool(config,host,port);
    }
    public static Jedis getJedis(){
        Jedis jedis = pool.getResource();
        return jedis;
    }
    public static void close(Jedis jedis){
        jedis.close();
    }
}
