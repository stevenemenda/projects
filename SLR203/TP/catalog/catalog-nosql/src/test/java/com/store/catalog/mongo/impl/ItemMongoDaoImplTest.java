package com.store.catalog.mongo.impl;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.store.catalog.model.SearchableItem;
import com.store.catalog.mongo.ItemMongoDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mong.conf-test.xml" })
public class ItemMongoDaoImplTest {


    //@Value("#{appProperties['mongo.db.catalog.collection.phones']}")
    @Value("${mongo.db.catalog.collection.phones}")
    private String collectionPhones;


    //@Value("#{appProperties['mongo.db.name']}")
    @Value("${mongo.db.name}")
    private String dbName;





    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    ItemMongoDao itemMongoDao;

    @Autowired MongoDbFactory mongoDbFactory;


    protected DBCollection collection ;

    public void setItemMongoDao(ItemMongoDao itemMongoDao) {
        this.itemMongoDao = itemMongoDao;
    }

    public void setMongoDbFactory(MongoDbFactory mongoDbFactory) {
        this.mongoDbFactory = mongoDbFactory;
    }

    @Before
    public void setUp(){
        mongoDbFactory = (MongoDbFactory)applicationContext.getBean("mongoDbFactory");
        collection =   mongoDbFactory.getDb(dbName).getCollection(collectionPhones);
    }

    @After
    public void tearDown(){
        collection.drop();
    }




    @Test
    public void testCreate() throws Exception {

        SearchableItem item = getSearchableItem(1);
        String id = itemMongoDao.create(item);

        BasicDBObject query = new BasicDBObject("_id",id);
        DBObject obj = collection.findOne(query);

        Assert.assertNotNull(obj);
        Assert.assertEquals(id,obj.get("_id").toString());

    }


   @Test
   public void anotherCreateTest() throws  Exception {
       SearchableItem item = getSearchableItem(1);
       String id = itemMongoDao.create(item);

       BasicDBObject query = new BasicDBObject("_id",id);
       DBObject obj = collection.findOne(query);
       Assert.assertNotNull(obj);
       Assert.assertEquals(id,obj.get("_id").toString());
   }



    @Test
    public void testUpdate() throws Exception {
        String id = itemMongoDao.create(getSearchableItem(1));

        SearchableItem foundItem = itemMongoDao.get(id);

        foundItem.setName(foundItem.getName() + "MDF");

        SearchableItem searchableItemMdf = itemMongoDao.update(foundItem);

        Assert.assertNotNull(searchableItemMdf);
        Assert.assertEquals(foundItem.getName(), searchableItemMdf.getName());

    }


    @Test
    public void testDelete() throws Exception {

        //create product
        String id = itemMongoDao.create(getSearchableItem(1));

        //then delete it
        itemMongoDao.delete(id);

        BasicDBObject query = new BasicDBObject("_id", getSearchableItem(1).get_id());
        DBObject obj =  collection.findOne(query);

        Assert.assertNull(obj);

    }




    @Test
    public void testCountAll() throws Exception {
        itemMongoDao.create(getSearchableItem(1));
        itemMongoDao.create(getSearchableItem(2));


        Long count = itemMongoDao.countAll();

        Assert.assertNotNull(count);
        Assert.assertEquals(2,count.longValue());

    }


    // --------------------------------------------------------------- //
    // ----------------    PRIVATE METHODS    ------------------------ //
    // --------------------------------------------------------------- //
    private SearchableItem getSearchableItem(int prdNumber){
        SearchableItem searchableItem = new SearchableItem("id1",Integer.valueOf(1),"imageUrl1","name" + prdNumber,"snippet1") ;


        return searchableItem;
    }


}
