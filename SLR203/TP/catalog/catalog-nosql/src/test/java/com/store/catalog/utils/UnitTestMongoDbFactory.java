package com.store.catalog.utils;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class UnitTestMongoDbFactory implements MongoDbFactory {

	
	@Autowired
	MongoClient mongoClient;
	
	@Override
	public DB getDb() throws DataAccessException {
		throw new NotImplementedException("");
		
	}

	@Override
	public DB getDb(String dbName) throws DataAccessException {
		return mongoClient.getDB(dbName);
	}

	@Override
	public PersistenceExceptionTranslator getExceptionTranslator() {
		throw new NotImplementedException("");
	}

}
