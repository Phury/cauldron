package be.phury.cauldron.provider.mongo;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public abstract class AbstractMongoDaoGson<T> {

	private MongoDatabase mongoDatabase;
	private Gson gson;
	private String collection;
	
	public AbstractMongoDaoGson(MongoDatabase mongoDatabase, Gson gson, String collection) {
		this.mongoDatabase = mongoDatabase;
		this.gson = gson;
		this.collection = collection;
	}
	
	@Inject
	public void postConstruct() {
		if (!isCollectionExists()) {
			mongoDatabase.createCollection(collection);
		}
	}
	
	private boolean isCollectionExists() {
		for (String collection : mongoDatabase.listCollectionNames()) {
			if (collection.equals(this.collection)) return true;
		}
		return false;
	}
	
	private MongoCollection<Document> getCollection() {
		return mongoDatabase.getCollection(collection);
	}
	
	public void insertOne(T t) {
		getCollection().insertOne(Document.parse(gson.toJson(t)));
	}

	public List<T> listAll(Class<T> typeOf) {
		List<T> all = new LinkedList<T>();
		MongoCursor<Document> cursor = getCollection().find().iterator();
		try {
		    while (cursor.hasNext()) {
		        all.add(gson.fromJson(cursor.next().toJson(), typeOf));
		    }
		} finally {
		    cursor.close();
		}
		return all;
	}
}
