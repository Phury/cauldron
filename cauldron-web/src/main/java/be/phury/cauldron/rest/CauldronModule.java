package be.phury.cauldron.rest;

import be.phury.cauldron.CauldronFactory;
import be.phury.cauldron.CauldronService;
import be.phury.cauldron.CauldronServiceImpl;
import be.phury.cauldron.provider.CauldronProvider;
import be.phury.cauldron.provider.CauldronProviderMemory;
import be.phury.cauldron.provider.mongo.MongoDatabaseFactory;

import com.google.gson.Gson;
import com.google.inject.Binder;
import com.google.inject.Module;

public class CauldronModule implements Module {
	public void configure(final Binder binder) {
		binder.bind(MongoDatabaseFactory.class);
		binder.bind(TransactionConverter.class);
		binder.bind(CauldronResource.class);
		binder.bind(CauldronService.class).to(CauldronServiceImpl.class);
		binder.bind(CauldronProvider.class).to(CauldronProviderMemory.class);
		binder.bind(CauldronFactory.class);
		binder.bind(GsonMessageBodyHandler.class);
		binder.bind(Gson.class);
		binder.bind(Bootstrap.class);
	}
}
