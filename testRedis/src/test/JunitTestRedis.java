package test;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JunitTestRedis {

	
	
	
	@Test
	public void  demo1() {
		Jedis jedis = new Jedis("192.168.109.128", 6379);
		jedis.set("money", "1����");
		String money=jedis.get("money");
		System.out.println("demo1��ֵ"+money);
	}
	
	
	@Test
	public void demo2() {
		   // �Ƚ�������ǣ�redis���ӳص���������Ҫ����һ�����ӳ����ö���
        JedisPoolConfig config = new JedisPoolConfig();
        // ��Ȼ���ﻹ���������ԵĴ���
       
         config.setMaxIdle(10);
        // ����Jedis���ӳض���
        JedisPool jedisPool = new JedisPool(config,"192.168.109.128",6379);

        // ��ȡ����
        Jedis jedis = jedisPool.getResource();
        String money=jedis.get("money");
        // ʹ��
        System.out.println("demo2��ֵ"+money);
       
    }
	
}
