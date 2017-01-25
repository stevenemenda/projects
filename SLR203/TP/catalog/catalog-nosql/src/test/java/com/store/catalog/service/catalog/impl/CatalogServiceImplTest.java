package com.store.catalog.service.catalog.impl;

import com.store.catalog.AbstractSearchTestCase;
import com.store.catalog.model.SearchableItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-conf-test.xml"})
@Configuration
@PropertySources(value = { @PropertySource(value = "classpath:configuration-test.properties") })
public class CatalogServiceImplTest extends AbstractSearchTestCase {



    @Autowired
	private CatalogSearchServiceImpl catalogSearchService;
	

	@Before
	public void setUp() throws Exception {
		createIndexReferential();
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
		String searchText = "xoom";

		List<SearchableItem> returnedLst = new ArrayList<SearchableItem>();
		
		List<SearchableItem> searchableItems = catalogSearchService.searchItems(searchText);

        assertNotNull(searchableItems);
        assertEquals(2,searchableItems.size());
		
	}


	@Test
	public void searchItemsMatchPhrase() throws Exception {
		String searchText = "xoom";

		List<SearchableItem> returnedLst = new ArrayList<SearchableItem>();

		List<SearchableItem> searchableItems = catalogSearchService.matchPhrasePrefixeItems(searchText, 1, 1);

		assertNotNull(searchableItems);
		assertEquals(1,searchableItems.size());

	}

	@Test
	public void searchItemsMatchPhraseTwoElements() throws Exception {
		String searchText = "sony";

		List<SearchableItem> returnedLst = new ArrayList<SearchableItem>();

		List<SearchableItem> searchableItems = catalogSearchService.matchPhrasePrefixeItems(searchText, 1, 2);

		assertNotNull(searchableItems);
		assertEquals(2,searchableItems.size());

	}
	
	
	/* ---------------------------------- */
	/* --------- Private methods -------- */
	/* ---------------------------------- */
	



	
}
