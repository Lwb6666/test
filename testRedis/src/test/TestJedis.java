package test;

import redis.clients.jedis.Jedis;

public class TestJedis {
	
	 public static void main(String[] args) {
	        Jedis jedis = new Jedis("192.168.109.128", 6379);
	        System.out.println(jedis.ping());
	        
	        //1.String��������
	        //jedis.set("name", "Tom");
	        //jedis.set("age", "18");
	        //System.out.println(jedis.get("name"));
	        
	        
	        
	        //2.hash��������
	        //jedis.hset("myhash", "username", "jerry");
	        //jedis.hset("myhash", "age", "24");
	        //jedis.hset("myhash2", "username", "rose");
	        //jedis.hset("myhash2", "age", "21");
	        //���ָ��key��ָ������ֵ
	        System.out.println(jedis.hget("myhash", "username"));
	        
	        
	        //���hash����ָ������ֵ����ֵ
	        System.out.println(jedis.hmget("myhash", "username","age"));
	        System.out.println(jedis.hmget("myhash2", "username","age"));
	        System.out.println(jedis.hgetAll("myhash"));
	        System.out.println("����ֵ"+jedis.keys("*"));
	        jedis.hdel("myhash2", "username");
	        System.out.println(jedis.hget("myhash2", "username"));
	        jedis.del("myhash2");
	        System.out.println(jedis.get("myhash2"));
	        System.out.println(jedis.get("myhash2"));
		/*
		 * jedis.lpush("list", "a"); jedis.lpush("list", "b"); jedis.lpush("list", "c");
		 * jedis.lpush("list", "d");
		 */
//	        System.out.println(jedis.lrange("list", 0, -1));
//	        System.out.println("ɾ��ǰ"+jedis.keys("*"));
//	        jedis.del("name");
//	        jedis.del("hello");
//	        jedis.del("list");
//	        jedis.del("age");
//	        jedis.del("sex");
//	        System.out.println("ɾ����"+jedis.keys("*"));
	        
	        
	        
	        
	        
	        
	    }
}