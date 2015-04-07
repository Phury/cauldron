package be.phury.cauldron.provider.mongo;

import javax.annotation.PreDestroy;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDatabaseFactory {

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	
	@PreDestroy
	public void preDestroy() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}
	
	public MongoDatabase createMongoDatabase() {
		if (mongoClient == null) {
			mongoClient = new MongoClient(new MongoClientURI("mongodb://cauldron-user:cauldron-pass@ds059661.mongolab.com:59661/cauldron"));
			mongoDatabase = mongoClient.getDatabase("cauldron");	
		}
		return mongoDatabase;
	}
}
