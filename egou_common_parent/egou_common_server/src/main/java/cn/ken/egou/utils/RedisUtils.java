package cn.ken.egou.utils;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUtils {
    private static JedisPool jedisPool = null;

    static {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(30);//最大连接数
        genericObjectPoolConfig.setMaxIdle(1);//最小连接数
        genericObjectPoolConfig.setMaxWaitMillis(5 * 1000);
        genericObjectPoolConfig.setTestOnBorrow(true);
        String host = "127.0.0.1";
        int port = 6379;
        String password = "root";
        jedisPool = new JedisPool(genericObjectPoolConfig, host, port, port, password);
    }

    /**
     * 获取连接
     *
     * @return
     */
    private static Jedis getSource() {
        return jedisPool.getResource();
    }

    /**
     * 关闭redis资源
     *
     * @param jedis redis实例
     */
    private static void closeSource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 设值到redis中
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getSource();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSource(jedis);
        }
    }

    /**
     * 在redis中获取数据
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getSource();
            String s = jedis.get(key);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSource(jedis);
        }

        return null;
    }
}
