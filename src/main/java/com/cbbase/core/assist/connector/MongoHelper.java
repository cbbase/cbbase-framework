package com.cbbase.core.assist.connector;

import com.cbbase.core.tools.PropertiesHelper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author changbo
 *
 */
public class MongoHelper {

	private static String CONFIG_FILE = "application.properties";
	

	public static MongoDatabase getMongoDatabase() {
		return getMongoDatabase("spring.data.mongodb");
	}
	
	public static MongoDatabase getMongoDatabase(String prefix) {
		PropertiesHelper helper = PropertiesHelper.getPropertiesHelper(CONFIG_FILE);
		String host = helper.getValue(prefix+".host");
		int port = helper.getValueAsInt(prefix+".port");
		String database = helper.getValue(prefix+".database");
		String username = helper.getValue(prefix+".username");
		String password = helper.getValue(prefix+".password");
		int timeout = helper.getValueAsInt(prefix+".timeout");
		timeout = (timeout == 0) ? 10000 : timeout;
		MongoClientOptions options = new MongoClientOptions
				.Builder()
				.connectTimeout(timeout)
				.build();
		ServerAddress addr = new ServerAddress(host, port);
		MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient(addr, credential, options);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
		return mongoDatabase;
	}

}
