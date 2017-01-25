package com.store.catalog.service.catalog.impl;

import com.store.catalog.model.*;
import com.store.catalog.service.catalog.CatalogService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ContextConfiguration({ "classpath:/applicationContext-rdbms-configuration-it-test.xml", "classpath:/applicationContext-rdbms-jpa-it-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ITCatalogServiceImplTest {



	private CategoryDTO categoryDto;
	
	private ProductDTO productDto;
	
	private ItemDTO itemDto;
	
	
	@Autowired
	private CatalogService catalogService;
	
	private Long id;
	
	@Before
	public void setUp() throws Exception {

		itemDto = getItemDto();

		categoryDto = getCategoryDto();
				
		productDto = getProductDto();
		
		id = Long.valueOf(1L);
	}

	@After
	public void tearDown() throws Exception {
		catalogService = null;
		itemDto = null;
	}

	
	
	/* ---------------------------------- */
	/* ---------------------------------- */
	/* ---------------------------------- */
	
	@Test 
	public void saveCategoryTest() throws Exception {
		CategoryDTO aCategoryDto = catalogService.createCategory(getCategoryDtoWithNullId());

		assertNotNull(aCategoryDto);

		CategoryDTO createdCategoryDto = catalogService.findCategory(aCategoryDto.getId());
		
		assertNotNull(createdCategoryDto);
		assertEquals(aCategoryDto.getId(), createdCategoryDto.getId());

		//catalogService.findCategories().forEach(category -> System.out.println(aCategoryDto.get_id()));

	}


	@Test
	public void saveProductTest() throws Exception {


		ProductDTO aProductDTO = getProductDtoWithNullId();
		CategoryDTO aCategoryDto = catalogService.createCategory(getCategoryDtoWithNullId());
		aProductDTO.setCategory(aCategoryDto);

		aProductDTO = catalogService.createProduct(aProductDTO);



		assertNotNull(aProductDTO);

		ProductDTO createdProductDto = catalogService.findProduct(aProductDTO.getId());

		assertNotNull(createdProductDto);
		assertEquals(aProductDTO.getId(), createdProductDto.getId());

		//catalogService.findCategories().forEach(category -> System.out.println(aCategoryDto.get_id()));

	}


	@Test
	public void saveItemTest() throws Exception {


		CategoryDTO aCategoryDto = catalogService.createCategory(getCategoryDtoWithNullId());

		ProductDTO aProductDTO = getProductDtoWithNullId();

		aProductDTO.setCategory(aCategoryDto);

		aProductDTO = catalogService.createProduct(aProductDTO);

		ItemDTO anItemDto = getItemDto() ;
		aProductDTO.setCategory(aCategoryDto);
		anItemDto.setProduct(aProductDTO);

		anItemDto =  catalogService.createItem(anItemDto);

		ItemDTO createdItemDto = catalogService.findItem(anItemDto.getId());


		assertNotNull(createdItemDto);
		assertEquals(anItemDto.getId(), createdItemDto.getId());


	}


	@Test
	public void searchItemsTest() throws Exception {


		CategoryDTO aCategoryDto = catalogService.createCategory(getCategoryDtoWithNullId());

		ProductDTO aProductDTO = getProductDtoWithNullId();

		aProductDTO.setCategory(aCategoryDto);

		aProductDTO = catalogService.createProduct(aProductDTO);
		aProductDTO.setCategory(aCategoryDto);

		ItemDTO itemDtoOne = getItemDtoWithNullId();
		itemDtoOne.setProduct(aProductDTO);
		catalogService.createItem(itemDtoOne);

		ItemDTO itemDtoTwo = getItemTwoDtoWithNullId();
		itemDtoTwo.setProduct(aProductDTO);
		catalogService.createItem(itemDtoTwo);

		ItemDTO itemDtoThree = getItemThreeDtoWithNullId()   ;
		itemDtoThree.setProduct(aProductDTO);
		catalogService.createItem(itemDtoThree);


		List<ItemDTO> list = catalogService.searchItems("ARTICLE");

		assertNotNull(list);
		assertEquals(3, list.size());


	}
	

	
	/* ---------------------------------- */
	/* --------- Private methods -------- */
	/* ---------------------------------- */

	private CategoryDTO getCategoryDtoWithNullId(){
		CategoryDTO category = new CategoryDTO("categoryName", "categoryDesc");
		return category;
	}

	private CategoryDTO getCategoryDto(){
		CategoryDTO category = new CategoryDTO(Long.valueOf(6L),"categoryName", "categoryDesc");
		return category;
	}

	private Category getCategory(){
		Category category = new Category(Long.valueOf(1L), "categoryName", "categoryDesc");
		return category;
	}
	

	private Category getCategory2(){
		Category category = new Category(Long.valueOf(2L), "categoryName2", "categoryDesc2");
		return category;
	}	

	
	private ProductDTO getProductDto(){
		ProductDTO product = new ProductDTO(Long.valueOf(1L), "productName", "productDesc");
		return product;
	}


	private ProductDTO getProductDtoWithNullId(){
		ProductDTO product = new ProductDTO(null, "productName", "productDesc");
		return product;
	}



	private Product getProduct(){
		Product product = new Product(Long.valueOf(1L), "productName", "productDesc");
		return product;
	}	
	
	private Product getProduct2(){
		Product product = new Product(Long.valueOf(2L), "productName2", "productDesc2");
		return product;
	}
	
	
	
	private ItemDTO getItemDto(){
		ItemDTO itemDto = new ItemDTO(Long.valueOf(1L),"ARTICLE_ID ONE",20.0);
		return itemDto;
	}



	private ItemDTO getItemDtoWithNullId(){
		ItemDTO itemDto = new ItemDTO(null,"ARTICLE ID",20.0);
		return itemDto;
	}

	private ItemDTO getItemTwoDtoWithNullId(){
		ItemDTO itemDto = new ItemDTO(null,"ARTICLE ID TWO",20.0);
		return itemDto;
	}

	private ItemDTO getItemThreeDtoWithNullId(){
		ItemDTO itemDto = new ItemDTO(null,"ARTICLE ID THREE",20.0);
		return itemDto;
	}


	private ItemDTO getItemDto(Item item){
		Mapper mapper = new DozerBeanMapper();
		ItemDTO itemDto = mapper.map(item, ItemDTO.class);
		ProductDTO productDto = mapper.map(item.getProduct(), ProductDTO.class);
		//itemDto.setProduct(productDto);
		return itemDto;
	}
	

	private Item getItem(){
		Item item = new Item(Long.valueOf(1L),"articleID",20.0);
		item.setProduct(getProduct());
		return item;
	}
	
	private Item getItem2(){
		Item item = new Item(Long.valueOf(2L),"articleID2",30.0);
		
		return item;
	}	
	
}
