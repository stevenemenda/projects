package com.store.catalog.mongo.config;

import org.springframework.data.mongodb.core.MongoFactoryBean;

public class CatalogMongoFactoryBean extends MongoFactoryBean {


	public CatalogMongoFactoryBean(){
	}
	
	
	private String portAsString;
	

	public void setPortAsString(String portAsString) {
		this.portAsString = portAsString;
		
		
		setPort(Integer.parseInt(portAsString));
	}

	
}
