package org.ubojs.api;

import java.util.Set;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.ujobs.model.Job;
import org.ujobs.model.User;
import org.ujobs.service.data.JobService;
import org.ujobs.service.data.JobServiceImpl;
import org.ujobs.service.data.UserService;
import org.ujobs.service.data.UserServiceImpl;
import org.ujobs.service.data.dao.Dao;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;

public class ApplicationModule extends AbstractModule {


	@Override
	protected void configure() {
		// The DAO's are configured manually
		bind(new TypeLiteral<Dao<User, ObjectId>>() {
		});
		bind(new TypeLiteral<Dao<Job, ObjectId>>() {
		});

		bind(UserService.class).to(UserServiceImpl.class);
		bind(JobService.class).to(JobServiceImpl.class);
	
	}

	@Provides
	@Singleton
	public ApplicationConfiguration provideApplicationConfiguration(Configuration configuration) {
		return ((ApplicationConfiguration) configuration);
	}

	@Provides
	@Singleton
	public MongoConfiguration provideMongoConfiguration(ApplicationConfiguration applicationConfiguration) {
		return applicationConfiguration.getMongo();
	}

	@Provides
	@Named("databaseName")
	public String provideDatabaseName(ApplicationConfiguration configuration) {
		return configuration.getMongo().getDatabase();

	}

	@Provides
	@Singleton
	public Mongo provideMongo(MongoConfiguration mongoConfiguration) {
		try {
		
			MongoClient mongoClient = new MongoClient(
					mongoConfiguration.getUri(),
					Integer.parseInt(mongoConfiguration.getPort()));

			DB db = mongoClient.getDB(mongoConfiguration.getDatabase());

			Set<String> colls = db.getCollectionNames();

			for (String s : colls) {
				System.out.println(s); // TODO inject logger
			}
			return db.getMongo();
		} catch (Exception e) {
			System.out.println(e); // TODO inject logger
			return null; // If mongo connection can't be established, then
							// application will can't start
		}
	}

	@Provides
	public Morphia provideMorphia() {
		Morphia morphia = new Morphia();

		/* morphia.mapPackage("com.makemyday.entities.stats"); */
		return morphia;
	}
	
	
}
