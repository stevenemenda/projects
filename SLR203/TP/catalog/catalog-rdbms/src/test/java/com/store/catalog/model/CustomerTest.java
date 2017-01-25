package com.store.catalog.model;

import org.junit.Test;

import com.store.catalog.common.exception.CheckException;

import static org.junit.Assert.*;

/**
 * Created by zouheir on 04/12/2016.
 */
public class CustomerTest {
	
	
	
    @Test(expected = CheckException.class)
    public void testMatchPasswordNull() throws Exception {
    	
    	Customer cs = new Customer();
    	cs.matchPassword(null);
    	
    }
    
    @Test(expected = CheckException.class)
    public void testMatchPasswordVide() throws Exception {
    	
    	Customer cs = new Customer();
    	cs.matchPassword("");
    	
    }
    
    @Test(expected = CheckException.class)
    public void testMatchPasswordNotMatch() throws Exception {
    	
    	Customer cs = new Customer();
    	cs.setPassword("lguo");    	
    	cs.matchPassword("lgup");
    	
    }
    
    @Test(expected = CheckException.class)
    public void testAnotherMatchPasswordNull() throws Exception {
    	
    	Customer cs = new Customer();
    	cs.anotherMatchPassword(null);
    	
    }
    
    @Test(expected = CheckException.class)
    public void testAnotherMatchPasswordVide() throws Exception {
    	
    	Customer cs = new Customer();
    	cs.anotherMatchPassword("");
    	
    }
    
    @Test
    public void testAnotherMatchPasswordNotMatch() throws Exception {
    	
    	Customer cs = new Customer();
    	cs.setPassword("lguo");    	
    	assertFalse("it will return false",cs.anotherMatchPassword("lgup"));
    	
    }
    
    @Test
    public void testAnotherMatchPasswordMatch() throws Exception {
    	
    	Customer cs = new Customer();
    	cs.setPassword("lguo");    
    	assertTrue("it will return True",cs.anotherMatchPassword("lguo"));
    	 
    	String str = "He llo ";
    	str = str.replaceAll(" ", "");
    	System.out.println( str );
    	
      
    }
    
}