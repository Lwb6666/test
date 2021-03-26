package test;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JunitTestRedis {

	
	
	
	@Test
	public void  demo1() {
		Jedis jedis = new Jedis("192.168.109.128", 6379);
		jedis.set("money", "1个亿");
		String money=jedis.get("money");
		System.out.println("demo1的值"+money);
	}
	
	
	@Test
	public void demo2() {
		   // 比较特殊的是，redis连接池的配置首先要创建一个连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 当然这里还有设置属性的代码
       
         config.setMaxIdle(10);
        // 创建Jedis连接池对象
        JedisPool jedisPool = new JedisPool(config,"192.168.109.128",6379);

        // 获取连接
        Jedis jedis = jedisPool.getResource();
        String money=jedis.get("money");
        // 使用
        System.out.println("demo2的值"+money);
       
    }
	
}
