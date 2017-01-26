package com.store.catalog.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.store.catalog.model.Category;
import com.store.catalog.model.Item;
import com.store.catalog.model.Product;
import com.store.catalog.utils.ConstantUtils;

import static com.store.catalog.utils.ConstantUtils.*;

public class ProductDaoTest extends AbstractBaseDaoTestCase {

    
    @Autowired
    private ProductDao productDao ;
    
    @Autowired
    private CategoryDao categoryDao ;    
    
    private Product product = null;
    
    
    @Before
    public void setUp(){
    	loadProduct();
    }
    
    @After
    public void tearDown(){
    	categoryDao = null;
    	productDao = null;
    }

    @Test
    public void testCreateProduct() throws Exception {
        productDao.save(product);
    	assertTrue("primary key assigned", product.getId() != null);
    }    
   
    @Test
    public void testUpdateProduct() throws Exception {
        productDao.save(product);
        
    	product.setName(PRODUCT_NAME + "MDF");
    	product.setDescription(PRODUCT_DESCRIPTION + "MDF");
    	product.setCategory(getCategory());
    	
    	productDao.save(product);
    	
    	Product productMdf = productDao.findOne(product.getId());
    	assertEquals(product, productMdf);
    }    
    
    
    @Test
    public void testGetProduct() throws Exception {

    	productDao.save(product);
    	
    	Product product2 = productDao.findOne(product.getId());
    	
    	assertNotNull(product2);
    	assertEquals(product,  product2);
    }   

    
    @Test
    public void testRemoveProduct() throws Exception {

    	productDao.save(product);
    	
    	Product product2 = productDao.findOne(product.getId());
    	
    	assertNotNull(product2.getId());
    	assertEquals(product,  product2);
    	
    	productDao.delete(product.getId());
    	
    	assertTrue(getIterableSize(productDao.findAll()) == 0);
    }

    
    
    @Test
    public void testGetProducts() throws Exception {
    	
    	productDao.save(product);
    	
    	Iterable<Product> lst = productDao.findAll();
    	
    	assertTrue(getIterableSize(lst) == 1);
    	
    	Product product2 = new Product();
    	product2.setId(new Random().nextLong());
    	product2.setName(PRODUCT_NAME + "MDF");
    	product2.setDescription(PRODUCT_DESCRIPTION + "MDF");
    	product2.setCategory(getCategory2());
    	productDao.save(product2);
    	
    	assertTrue(getIterableSize(productDao.findAll()) == 2);
    }    
    

    @Test
    public void testGetProductsWithCategoryId() throws Exception {
        
    	productDao.save(product);
    	
    	Product product2 = new Product();
    	product2.setId(new Random().nextLong());
    	product2.setName(PRODUCT_NAME + "MDF");
    	product2.setDescription(PRODUCT_DESCRIPTION + "MDF");
    	product2.setCategory(product.getCategory());
    	productDao.save(product2);
    	
    	Product product3 = new Product();
    	product3.setId(new Random().nextLong());
    	product3.setName(PRODUCT_NAME + "MDF");
    	product3.setDescription(PRODUCT_DESCRIPTION + "MDF");
    	product3.setCategory(getCategory2());
    	productDao.save(product3);
    	
    	Iterable<Product> iter = productDao.findByCategoryId(product.getCategory().getId());
    	List<Product> lst = new ArrayList<Product>();
    	for(Product obj:iter){
    		lst.add(obj);
    	}
    	assertTrue(getIterableSize(iter) == 2);
    	assertEquals(product, lst.get(0));
    	assertEquals(product2, lst.get(1));
    }    

    
    @Test
    public void testGetProductsByCategoryName() throws Exception {
    	
    	productDao.save(product);
    	
    	Product product2 = new Product();
    	product2.setId(new Random().nextLong());
    	product2.setName(PRODUCT_NAME + "MDF");
    	product2.setDescription(PRODUCT_DESCRIPTION + "MDF");
    	product2.setCategory(product.getCategory());
    	productDao.save(product2);
    	
    	Product product3 = new Product();
    	product3.setId(new Random().nextLong());
    	product3.setName(PRODUCT_NAME + "MDF3");
    	product3.setDescription(PRODUCT_DESCRIPTION + "MDF3");
    	product3.setCategory(product.getCategory());
    	productDao.save(product3);
    	
    	Iterable<Product> iter = productDao.findByCategoryName(product.getCategory().getName());
    	List<Product> lst = new ArrayList<Product>();
    	for(Product obj:iter){
    		lst.add(obj);
    	}
    	assertTrue(getIterableSize(iter) == 3);
    	assertEquals(product.getCategory(), lst.get(0).getCategory());
    	assertEquals(product2.getCategory(), lst.get(1).getCategory());
    	assertEquals(product3.getCategory(), lst.get(2).getCategory());
    }        
	
	
	private Category getCategory() {
		Category category = new Category();
        category.setId(new Random().nextLong());
        category.setName(CATEGOY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);

        categoryDao.save(category);
		return category;
	}    
	
	private Category getCategory2() {
		Category category = new Category();
        category.setId(new Random().nextLong());
        category.setName("catName2");
        category.setDescription("description2");

        categoryDao.save(category);
		return category;
	}    
    
	
    /**
     * 
     * create an instanciated object. The one declared as private field in the test class
     */	
	private void loadProduct() {
    	product = new Product();
        product.setId(new Random().nextLong());
    	product.setName(PRODUCT_NAME);
    	product.setDescription(PRODUCT_DESCRIPTION);
    	product.setCategory(getCategory());
	}
	
}
