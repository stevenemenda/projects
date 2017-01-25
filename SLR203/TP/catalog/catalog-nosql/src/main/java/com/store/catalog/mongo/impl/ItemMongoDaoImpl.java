package com.store.catalog.mongo.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.util.JSON;
import com.store.catalog.model.SearchableItem;
import com.store.catalog.mongo.ItemMongoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ItemMongoDaoImpl  implements ItemMongoDao {

	private final Logger log = LoggerFactory.getLogger(this.getClass());


	@Value("${mongo.db.catalog.collection.phones}")
	private String collectionPhones;


	@Value("${mongo.db.name}")
	private String dbNameCatalog;

	@Autowired
	protected MongoDbFactory mongoDbFactory;

	@Autowired
	protected ObjectMapper jacksonMapper;

	protected String host;

	protected Integer port;







	public void setCollectionPhones(String collectionPhones) {
		this.collectionPhones = collectionPhones;
	}


	public void setDbNameCatalog(String dbNameCatalog) {
		this.dbNameCatalog = dbNameCatalog;
	}


	public void setJacksonMapper(ObjectMapper jacksonMapper) {
		this.jacksonMapper = jacksonMapper;
	}


	public void setMongoDbFactory(MongoDbFactory mongoDbFactory) {
		this.mongoDbFactory = mongoDbFactory;
	}



	@Override
	public String create(SearchableItem searchableItem) throws Exception {

        searchableItem.set_id(searchableItem.createId());
		
		DBCollection collection = mongoDbFactory.getDb(dbNameCatalog).getCollection(collectionPhones);
		
		String json = jacksonMapper.writeValueAsString(searchableItem);
		
		if (log.isDebugEnabled()){
			log.debug("product as Json = {}", json);	
		}
		
		DBObject  dbObject = (DBObject) JSON.parse(json);
		
		collection.insert(dbObject);		

		return dbObject.get("_id").toString();
		
	}




	@Override
	public SearchableItem update(SearchableItem searchableItem) throws Exception {


		DBCollection collection = mongoDbFactory.getDb(dbNameCatalog).getCollection(collectionPhones);
		
		String json = jacksonMapper.writeValueAsString(searchableItem);
		DBObject  dbObject = (DBObject) JSON.parse(json);
		
		
		WriteResult writeResult = collection.update(new BasicDBObject("_id", dbObject.get("_id")), dbObject,false,false,WriteConcern.ACKNOWLEDGED);
		writeResult.isUpdateOfExisting();
		
		dbObject = collection.findOne(dbObject);

        SearchableItem result = jacksonMapper.readValue(dbObject.toString(), SearchableItem.class);
		
		return result;
	}


	@Override
	public SearchableItem get(String id) throws Exception {
		DBCollection collection = mongoDbFactory.getDb(dbNameCatalog).getCollection(collectionPhones);

		
		DBObject  dbObject = collection.findOne(new BasicDBObject("_id",id));


        SearchableItem result = null;
		if (dbObject != null) {
			result = jacksonMapper.readValue(dbObject.toString(), SearchableItem.class);
		}
	
		return result;
	}



	@Override
	public void delete(String id) throws Exception {
		DBCollection collection = mongoDbFactory.getDb(dbNameCatalog).getCollection(collectionPhones);

		DBObject dbObject = collection.findOne(new BasicDBObject("_id", id));
		
		collection.remove(dbObject) ;		
		
	}



	protected String getByDataSourceId(SearchableItem product) throws Exception {
		DBCollection collection = mongoDbFactory.getDb(dbNameCatalog).getCollection(collectionPhones);

		
		DBObject  dbObject = collection.findOne(new BasicDBObject("_id",product.createId()));

		if (dbObject != null) {
			return dbObject.get("_id").toString();			
		}else {
			return null;
		}


	}


	@Override
	public Long countAll() throws Exception {
		DBCollection collection = mongoDbFactory.getDb(dbNameCatalog).getCollection(collectionPhones);
		
		if (collection != null) {
			return Long.valueOf(collection.count());
		} else {
			return null;			
		}

	}




	
	
}



