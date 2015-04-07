package be.phury.cauldron.provider.mongo.dao;

import be.phury.cauldron.model.Transaction;
import be.phury.cauldron.provider.mongo.AbstractMongoDaoGson;
import be.phury.cauldron.provider.mongo.MongoDatabaseFactory;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ExpenseDao extends AbstractMongoDaoGson<Transaction> {

	@Inject
	public ExpenseDao(MongoDatabaseFactory mongoDatabaseFactory, Gson gson) {
		super(mongoDatabaseFactory.createMongoDatabase(), gson, "expenses");
	}

}
