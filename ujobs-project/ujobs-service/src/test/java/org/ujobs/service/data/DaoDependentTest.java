package org.ujobs.service.data;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.Morphia;
import org.ujobs.model.Job;
import org.ujobs.model.User;
import org.ujobs.service.data.dao.Dao;

import com.google.inject.TypeLiteral;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

// TODO: look for a Google Guice Test intializer

public abstract class DaoDependentTest {
	protected static MongoClient mongo;
	protected static MongodExecutable mongodExecutable;
	protected static Morphia morphia = new Morphia();
	protected static String TEST_DB_NAME = "test";
	
	@BeforeClass
	public static void initializeEmbeddedMongo() throws UnknownHostException, IOException 
	{
		int port = 12345;
		IMongodConfig mongodConfig = new MongodConfigBuilder()
		    .version(Version.Main.PRODUCTION)
		    .net(new Net(port, Network.localhostIsIPv6()))
		    .build();
		
		MongodStarter runtime = MongodStarter.getDefaultInstance();
	    mongodExecutable = runtime.prepare(mongodConfig);
	    MongodProcess mongod = mongodExecutable.start();
	    mongo = new MongoClient("localhost", port);
	    
	}
	
	@AfterClass
	public static void shutdownEmbeddedMongo() {
		 if (mongodExecutable != null)
		        mongodExecutable.stop();
	}
	
	public Dao<User,ObjectId> getUserDao() {
		 TypeLiteral<User> mapType = new TypeLiteral<User>() {};
		return new Dao<User,ObjectId>(mapType, mongo, morphia, TEST_DB_NAME);
	}
	
	public Dao<Job,ObjectId> getJobsDao() {
		 TypeLiteral<Job> mapType = new TypeLiteral<Job>() {};
		return new Dao<Job,ObjectId>(mapType, mongo, morphia, TEST_DB_NAME);
	}
}
