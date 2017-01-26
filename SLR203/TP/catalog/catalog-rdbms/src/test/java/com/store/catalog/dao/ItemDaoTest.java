package com.store.catalog.dao;

import com.store.catalog.model.Product;
import com.store.catalog.utils.ConstantUtils;

import com.store.catalog.model.Category;
import com.store.catalog.model.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.store.catalog.utils.ConstantUtils.*;

import static org.junit.Assert.*;

public class ItemDaoTest extends AbstractBaseDaoTestCase {
    


	private Item item = null;

    @Autowired
    private ItemDao itemDao ;
    
    @Autowired
    private ProductDao productDao ;
    
    @Autowired
    private CategoryDao categoryDao ;    
   
    @Before
    public void setUp(){
    	loadItem();
    }
    
    @After
    public void tearDown(){
    	categoryDao = null;
    	productDao = null;
    	itemDao  = null;
    	item = null;
    }


    @Test
    public void testCreateItem() throws Exception {
    	itemDao.save(item);
    	assertTrue("primary key assigned", item.getId() != null);
    }


    @Test
    public void testUpdateItem() throws Exception {
    	itemDao.save(item);
    	
    	item.setName(ConstantUtils.ITEM_NAME + "MDF");
    	item.setImagePath(ConstantUtils.ITEM_IMAGE_PATH + "MDF");
    	item.setUnitCost(ConstantUtils.ITEM_PRICE + 20d);
    	item.setProduct(getProduct());
    	
    	itemDao.save(item);
    	
    	Item itemMdf = itemDao.findOne(item.getId());
    	assertEquals(item, itemMdf);
    }

    @Test
    public void testGetItem() throws Exception {
    	
    	itemDao.save(item);
    	
    	Item item2 = itemDao.findOne(item.getId());
    	
    	assertNotNull(item2);
    	assertEquals(item,  item2);
    }


    @Test
    public void testRemoveItem() throws Exception {
    	
    	itemDao.save(item);
    	
    	Item item2 = itemDao.findOne(item.getId());
    	
    	assertNotNull(item2.getId());
    	assertEquals(item,  item2);
    	
    	itemDao.delete(item.getId());
    	
    	assertTrue(getIterableSize(itemDao.findAll()) == 0);
    }


    @Test
    public void testGetItems() throws Exception {

    	itemDao.save(item);
    	
    	Iterable<Item> lst = itemDao.findAll();
    	
    	assertTrue(getIterableSize(itemDao.findAll()) == 1);
    	
    	Item item2 = new Item();
    	item2.setId(new Random().nextLong());
    	item2.setName(ConstantUtils.ITEM_NAME + "MDF");
    	item2.setImagePath(ConstantUtils.ITEM_IMAGE_PATH + "MDF");
    	item2.setUnitCost(ConstantUtils.ITEM_PRICE + 20d);
        item2.setProduct(getProduct());
    	itemDao.save(item2);
    	
    	assertTrue(getIterableSize(itemDao.findAll()) == 2);
    }


    @Test
    public void testGetItemsWithProductId() throws Exception {
        
    	itemDao.save(item);
    	
    	Item item2 = new Item();
    	item2.setId(new Random().nextLong());
    	item2.setName(ConstantUtils.ITEM_NAME + "MDF");
    	item2.setImagePath(ConstantUtils.ITEM_IMAGE_PATH + "MDF");
    	item2.setUnitCost(ConstantUtils.ITEM_PRICE + 20d);
        item2.setProduct(item.getProduct());
    	itemDao.save(item2);
    	
    	Iterable<Item> iter = itemDao.findByProductId(item.getProduct().getId());
    	List<Item> lst = new ArrayList<Item>();
    	for(Item obj:iter){
    		lst.add(obj);
    	}
    	assertTrue(getIterableSize(iter) == 2);
    	assertEquals(item, lst.get(0));
    	assertEquals(item2, lst.get(1));
    	
    }


    @Test
    public void testSearchItem() throws Exception {
    	
		itemDao.save(item);
    	
    	Item item2 = new Item();
    	item2.setId(new Random().nextLong());
    	item2.setName(ConstantUtils.ITEM_NAME + "MDF1");
    	item2.setImagePath(ConstantUtils.ITEM_IMAGE_PATH + "MDF");
    	item2.setUnitCost(ConstantUtils.ITEM_PRICE + 20d);
        item2.setProduct(item.getProduct());
    	itemDao.save(item2);
    	
    	Item item3 = new Item();
    	item3.setId(new Random().nextLong());
    	item3.setName(ConstantUtils.ITEM_NAME + "MDF2");
    	item3.setImagePath(ConstantUtils.ITEM_IMAGE_PATH + "MDF");
    	item3.setUnitCost(ConstantUtils.ITEM_PRICE + 20d);
        item3.setProduct(item.getProduct());
    	itemDao.save(item3);
    	
    	Iterable<Item> iter = itemDao.findByNameContaining(ConstantUtils.ITEM_NAME);
    	List<Item> lst = new ArrayList<Item>();
    	for(Item obj:iter){
    		lst.add(obj);
    	}
    	assertTrue(getIterableSize(iter) == 3);
    	assertEquals(item.getProduct(), lst.get(0).getProduct());
    	assertEquals(item2.getProduct(), lst.get(1).getProduct());
    	assertEquals(item3.getProduct(), lst.get(2).getProduct());
    	
    }
    
    
    /**
     * 
     * @return an instanciated object. The one declared as private field in the test class
     */    
	private void loadItem() {
	   	item = new Item();
        item.setId(new Random().nextLong());
        item.setName(ITEM_NAME);
        item.setImagePath(ITEM_IMAGE_PATH);
        item.setUnitCost(ITEM_PRICE);
        item.setProduct(getProduct());
	}
	
	
	private Item getAnotherItem() {
        Item item2 = new Item();
        item2.setId(new Random().nextLong());
        item2.setName(ITEM_NAME + "2");
        item2.setImagePath(ITEM_IMAGE_PATH + "2");
        item2.setUnitCost(ITEM_PRICE + 10d); 
        item2.setProduct(getProduct());

        return item2;
	}	
    
    
	private Product getProduct() {
	   	Product product = new Product();
        product.setId(new Random().nextLong());
        product.setName(PRODUCT_NAME);
        product.setDescription(PRODUCT_DESCRIPTION);

        Category category = getCategory();
        product.setCategory(category);

        productDao.save(product);        
        
        return product;
	}    
	
	private Product getProduct(String name, String desc) {
	   	Product product = new Product();
        product.setId(new Random().nextLong());
        product.setName(name);
        product.setDescription(desc);

        Category category = getCategory();
        product.setCategory(category);

        productDao.save(product);        
        
        return product;
	}	
	
	private Category getCategory() {
		Category category = new Category();
        category.setId(new Random().nextLong());
        category.setName(CATEGOY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);

        categoryDao.save(category);
		return category;
	}    	
}
