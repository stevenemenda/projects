package com.store.catalog.dao;

import com.store.catalog.utils.ConstantUtils;
import static com.store.catalog.utils.ConstantUtils.*;
import com.store.catalog.model.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static org.junit.Assert.*;

public class CategoryDaoTest  extends AbstractBaseDaoTestCase{

	
	
	@Autowired
    private CategoryDao categoryDao;
    
	
    private Category category = null;
    
    @Before
    public void setUp(){
    	loadCategory();
    }

    
    @After
    public void tearDown(){
    	category = null;
    	categoryDao = null;
    }


    @Test
    public void testCreateCategory() throws Exception {
        throw new Exception("not yet implemented");
    }

    @Test
    public void testUpdateCategory() throws Exception {
        throw new Exception("not yet implemented");
    }

    @Test
    public void testGetCategory() throws Exception {
        throw new Exception("not yet implemented");
    }

    @Test
    public void testRemoveCategory() throws Exception {
        throw new Exception("not yet implemented");
    }


    @Test
    public void testGetCategories() throws Exception {
        throw new Exception("not yet implemented");
    }

    
    /**
     * 
     * create an instanciated object. The one declared as private field in the test class
     */   
	private void  loadCategory() {
		category = new Category();
        category.setId(new Random().nextLong());
		category.setName(ConstantUtils.CATEGOY_NAME);
		category.setDescription(ConstantUtils.CATEGORY_DESCRIPTION);
	}


    
}
