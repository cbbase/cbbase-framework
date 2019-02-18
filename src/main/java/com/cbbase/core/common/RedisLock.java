package com.cbbase.core.common;

import java.time.Duration;
import java.util.Arrays;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * 使用redis实现锁
 * @author changbo
 *
 */
public class RedisLock {
	
	public static boolean tryLock(String lockKey, long timeout) {
		try {
			StringRedisTemplate redisTemplate = ServiceFactory.getBean(StringRedisTemplate.class);
			return redisTemplate.opsForValue().setIfAbsent(lockKey, "1", Duration.ofMillis(timeout));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void release(String lockKey) {
		try {
			StringRedisTemplate redisTemplate = ServiceFactory.getBean(StringRedisTemplate.class);
			redisTemplate.delete(lockKey);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static boolean tryLock(String lockKey, String requestId, long timeout) {
		try {
			StringRedisTemplate redisTemplate = ServiceFactory.getBean(StringRedisTemplate.class);
			return redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, Duration.ofMillis(timeout));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void release(String lockKey, String requestId) {
		try {
			String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 0 end";
			StringRedisTemplate redisTemplate = ServiceFactory.getBean(StringRedisTemplate.class);
			RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
			redisTemplate.execute(redisScript, Arrays.asList(lockKey), requestId);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
