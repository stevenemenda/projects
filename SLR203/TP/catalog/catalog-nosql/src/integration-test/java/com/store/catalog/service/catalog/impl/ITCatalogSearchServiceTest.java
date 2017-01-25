package com.store.catalog.service.catalog.impl;

import com.store.catalog.service.catalog.CatalogAdminService;
import com.store.catalog.service.catalog.CatalogSearchService;
import com.store.catalog.model.SearchableItem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@ContextConfiguration({ "classpath:/applicationContext-properties-it.xml", "classpath:/applicationContext-nosql-configuration-it.xml","classpath:/applicationContext-search-it.xml","classpath:/applicationContext-mongo-it.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ITCatalogSearchServiceTest {




	
	@Autowired
	private CatalogSearchService catalogSearchService;

	@Autowired
	private CatalogAdminService catalogAdminService;
	
	private Long id;
	
	@Before
	public void setUp() throws Exception {


	}

	@After
	public void tearDown() throws Exception {
        catalogSearchService = null;
	}

	
	
	/* ---------------------------------- */
	/* ---------------------------------- */
	/* ---------------------------------- */
	
	@Test 
	public void searchItemsTest() throws Exception {
        assertNotNull(catalogSearchService);

		List<SearchableItem> items = catalogSearchService.searchItems("motorola");

		assertNotNull(items);
		assertTrue(items.size() > 1);
	}


	@Test
	public void searchMatchPhrasePrefixeitems() throws Exception {
		assertNotNull(catalogSearchService);

		List<SearchableItem> items = catalogSearchService.matchPhrasePrefixeItems("samsung",0,3);

		assertNotNull(items);
		assertTrue(items.size() ==  3);
	}

	@Test
	public void searchMatchPhrasePrefixeitemsWithPagination() throws Exception {
		assertNotNull(catalogSearchService);

		List<SearchableItem> items = catalogSearchService.matchPhrasePrefixeItems("samsung",2,1);

		assertNotNull(items);
		assertTrue(items.size() ==  1);
	}


	@Test
	public void createItem() throws Exception {
		assertNotNull(catalogAdminService);

		SearchableItem item = getSearchableItem(1);

		String id = catalogAdminService.createItem(item);

		assertNotNull(id);
		Assert.assertEquals(item.get_id(), id);
	}


	
	/* ---------------------------------- */
	/* --------- Private methods -------- */
	/* ---------------------------------- */

	private SearchableItem getSearchableItem(int prdNumber){
		SearchableItem searchableItem = new SearchableItem("id1",Integer.valueOf(1),"imageUrl1","name" + prdNumber,"snippet1") ;


		return searchableItem;
	}
}
