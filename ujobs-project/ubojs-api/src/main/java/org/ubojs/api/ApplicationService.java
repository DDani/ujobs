package org.ubojs.api;


import org.bson.types.ObjectId;
import org.ubojs.api.utils.CustomSerializationModule;
import org.ubojs.api.utils.ObjectIdDeSerializer;
import org.ubojs.api.utils.ObjectIdSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;



public class ApplicationService extends Service<ApplicationConfiguration> {
    public static void main(String[] args) throws Exception {
        new ApplicationService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
    	 bootstrap.setName("ujobs");
    	 
    	 GuiceBundle<Configuration> guiceBundle = GuiceBundle.newBuilder()
         .addModule(new ApplicationModule())
         .enableAutoConfig(getClass().getPackage().getName())
        
         .build();
    	
    	
         bootstrap.addBundle(guiceBundle);
       
         //TODO: Perhaps there is anyway to do this with Guice? 
         bootstrap.getObjectMapperFactory().registerModule(new CustomSerializationModule());
        
    }

    @Override
    public void run(ApplicationConfiguration configuration,
                    Environment environment) {
   
    }
}
