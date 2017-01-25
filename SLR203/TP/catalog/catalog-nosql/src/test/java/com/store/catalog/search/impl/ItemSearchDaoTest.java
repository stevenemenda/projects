package com.store.catalog.search.impl;

import com.store.catalog.AbstractSearchTestCase;
import com.store.catalog.model.SearchableItem;
import com.store.catalog.search.ItemSearchDao;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ItemSearchDaoTest  extends AbstractSearchTestCase {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemSearchDao itemSearchDao;



    @Before
    public void setUp() throws Exception {

        createIndexReferential();
    }






    @Test
    public void testSearchItems() throws  Exception {

        //search two xoom
        assertNotNull(itemSearchDao);

        List<SearchableItem> items = itemSearchDao.searchItems("xoom");

        assertNotNull(items);
        assertEquals(2,items.size());

    }

    @Test
    public void testCountAll() throws  Exception {
        assertNotNull(itemSearchDao);

        Long countAll =  itemSearchDao.countAll();

        assertNotNull(countAll);
        assertEquals(Long.valueOf(9),countAll);
    }



    @Test
    public void testSearchItemsOne() throws  Exception {
        assertNotNull(itemSearchDao);

        List<SearchableItem> items =  itemSearchDao.searchItems("samsung");

        assertNotNull(items);
        assertEquals(1,items.size());
    }


    @Test
    public void testSearchItemsTwo() throws  Exception {
        assertNotNull(itemSearchDao);

        List<SearchableItem> items =  itemSearchDao.searchItems("xoom");

        assertNotNull(items);
        assertEquals(2,items.size());
    }




    @Test
    public void testMatchPrefixFirstPageWithTwoElements() throws  Exception {
        assertNotNull(itemSearchDao);

        List<SearchableItem> items =  itemSearchDao.matchPhrasePrefixItems("sony", 0, 2);

        assertNotNull(items);
        assertEquals(2,items.size());
    }

    @Test
    public void testMatchPrefixSecondPageWithTwoElements() throws  Exception {
        assertNotNull(itemSearchDao);

        List<SearchableItem> items =  itemSearchDao.matchPhrasePrefixItems("sony", 1, 2);

        assertNotNull(items);
        assertEquals(2,items.size());
    }

    @Test
    public void testMatchPrefixThirdPageWithOneElements() throws  Exception {
        assertNotNull(itemSearchDao);

        List<SearchableItem> items =  itemSearchDao.matchPhrasePrefixItems("sony", 1, 2);

        assertNotNull(items);
        assertEquals(2,items.size());
    }

}