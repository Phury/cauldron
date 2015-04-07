package be.phury.cauldron.provider.mongo.dao;

import be.phury.cauldron.model.Budget;
import be.phury.cauldron.provider.mongo.AbstractMongoDaoGson;
import be.phury.cauldron.provider.mongo.MongoDatabaseFactory;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class BudgetDao extends AbstractMongoDaoGson<Budget> {

	@Inject
	public BudgetDao(MongoDatabaseFactory mongoDatabaseFactory, Gson gson) {
		super(mongoDatabaseFactory.createMongoDatabase(), gson, "budgets");
	}

}
