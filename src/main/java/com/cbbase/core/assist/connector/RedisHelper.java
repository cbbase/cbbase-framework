package com.cbbase.core.assist.connector;

import java.time.Duration;
import java.util.ArrayList;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.cbbase.core.tools.PropertiesHelper;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.support.ConnectionPoolSupport;

/**
 * 	
 * @author changbo
 *
 */
public class RedisHelper {
	
	private static String CONFIG_FILE = "application.properties";
	
	/**
	 * 	单机
	 * @return
	 */
	public static RedisCommands<String, String> getRedisCommands(){
		return getRedisCommands("spring.redis");
	}
	/**
	 * 	单机
	 * @return
	 */
	public static RedisCommands<String, String> getRedisCommands(String prefix){
		PropertiesHelper helper = PropertiesHelper.getPropertiesHelper(CONFIG_FILE);
		String host = helper.getValue(prefix+".host");
		int port = helper.getValueAsInt(prefix+".port");
		String password = helper.getValue(prefix+".password");
		long timeout = helper.getValueAsInt(prefix+".timeout");
		timeout = (timeout == 0) ? 10000 : timeout;
		RedisURI redisURI= RedisURI.Builder
				.redis(host, port)
				.withPassword(password)
				.withTimeout(Duration.ofMillis(timeout))
				.build();
		RedisClient client = RedisClient.create(redisURI);
		StatefulRedisConnection<String, String> connection = client.connect();
		//异步使用connection.async();
		RedisCommands<String, String> commands = connection.sync();
		return commands;
	}


	public static RedisAdvancedClusterCommands<String, String> getRedisClusterCommands(){
		return getRedisClusterCommands("spring.redis");
	}
	/**
	 *	集群
	 * @return
	 */
	public static RedisAdvancedClusterCommands<String, String> getRedisClusterCommands(String prefix){
		PropertiesHelper helper = PropertiesHelper.getPropertiesHelper(CONFIG_FILE);
		String nodes = helper.getValue(prefix+".cluster.nodes");
		long timeout = helper.getValueAsInt(prefix+".timeout");
		timeout = (timeout == 0) ? 10000 : timeout;
        ArrayList<RedisURI> list = new ArrayList<>();
        String[] nodeArray = nodes.split(",");
        for(String node : nodeArray) {
        	RedisURI redisURI = RedisURI.create("redis://"+node);
        	redisURI.setTimeout(Duration.ofMillis(timeout));
            list.add(redisURI);
        }
        //集群Redis  
        RedisClusterClient client = RedisClusterClient.create(list);
        GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool = ConnectionPoolSupport.createGenericObjectPool(() -> client.connect(), new GenericObjectPoolConfig<Object>());
    	StatefulRedisClusterConnection<String, String> connection = null;
		try {
			connection = pool.borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//异步使用connection.async();
        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
        return commands;
	}

}
