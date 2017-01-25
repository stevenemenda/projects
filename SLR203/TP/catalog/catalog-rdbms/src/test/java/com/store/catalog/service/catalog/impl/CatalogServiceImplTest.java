package com.store.catalog.service.catalog.impl;

import com.store.catalog.dao.CategoryDao;
import com.store.catalog.dao.ItemDao;
import com.store.catalog.dao.ProductDao;
import com.store.catalog.model.*;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.plugins.MockMaker;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatalogServiceImplTest {
	@Mock
	private CategoryDao categoryDaoMock;	
	
	@Mock
	private ProductDao productDaoMock;	
	
	@Mock
	private ItemDao itemDaoMock;

	@Mock
    protected Mapper mockedMapper;
	
	private CatalogServiceImpl catalogService;
	

	@Before
	public void setUp() throws Exception {

		//init implementation CatalogServiceImpl
		catalogService = new CatalogServiceImpl();
				
		//set dependencies for Daos
		catalogService.setCategoryDao(categoryDaoMock);
		
		
		catalogService.setProductDao(productDaoMock);
		
		catalogService.setItemDao(itemDaoMock);
		
		//set dependency for dozer mapper
		catalogService.setDozerMapper(mockedMapper);
		
	}

	@After
	public void tearDown() throws Exception {
		catalogService = null;
	}

	
	
	/* ---------------------------------- */
	/* ---------------------------------- */
	/* ---------------------------------- */
	
	@Test 
	public void saveCategoryTest() throws Exception {
		// get required instances
        Category category = getCategory();
        CategoryDTO categoryDTO = getCategoryDto();
        // set mock for some functions which are not completed yet!
        when(categoryDaoMock.save(category)).thenReturn(category);
        when(mockedMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);
        when(mockedMapper.map(categoryDTO, Category.class)).thenReturn(category);
        // call the function create*
		CategoryDTO aCategoryDto = catalogService.createCategory(categoryDTO);
		// make sure that function save has been called!
        verify(categoryDaoMock).save(category);
        // check the results with conditions
		assertNotNull(aCategoryDto);
		assertEquals(categoryDTO, aCategoryDto);
	}
	
	@Test 
	public void udapteCategoryTest() throws Exception {
        Category category = getCategory();
        CategoryDTO categoryDTO = getCategoryDto();

        when(categoryDaoMock.findOne(category.getId())).thenReturn(category);
        when(mockedMapper.map(categoryDTO, Category.class)).thenReturn(category);

		catalogService.updateCategory(categoryDTO);
		
		verify(categoryDaoMock).save(category);
	}
	
	
	@Test 
	public void deleteCategoryTest() throws Exception {
        Long id = Long.valueOf(1);

        doNothing().when(categoryDaoMock).delete(id);

		catalogService.deleteCategory(id);
		
		verify(categoryDaoMock).delete(id);
	}
	

	@Test 
	public void findCategoriesTest() throws Exception {

        Category category = getCategory();
        Category category2 = getCategory2();

        CategoryDTO categoryDTO = getCategoryDto();
        CategoryDTO categoryDTO2 = getCategoryDto();

        List<Category> returnedLst = new ArrayList<Category>();
		
		returnedLst.add(category);
		returnedLst.add(category2);
		
		when(categoryDaoMock.findAll()).thenReturn(returnedLst);
        when(mockedMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);
        when(mockedMapper.map(category2, CategoryDTO.class)).thenReturn(categoryDTO2);

		List<CategoryDTO> categoriesDto = catalogService.findCategories();
		
		assertNotNull(categoriesDto);
		
		assertEquals(2, categoriesDto.size());

        CategoryDTO returnedCategoryDTO1 = categoriesDto.get(0);
        CategoryDTO returnedCategoryDTO2 = categoriesDto.get(1);
        assertNotNull(returnedCategoryDTO1);
        assertNotNull(returnedCategoryDTO2);
	}	

	@Test 
	public void findCategoryTest() throws Exception {
        Category category = getCategory();
        CategoryDTO categoryDTO = getCategoryDto();

        when(categoryDaoMock.findOne(category.getId())).thenReturn(category);
        when(mockedMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

		CategoryDTO myCategoryDto = catalogService.findCategory(category.getId());
		
		assertNotNull(myCategoryDto);
		
		assertEquals(Long.valueOf(1L), myCategoryDto.getId());
		assertEquals(categoryDTO, myCategoryDto);
	}	
	

	/* ---------------------------------- */
	/* ---------------------------------- */
	/* ---------------------------------- */
	@Test 
	public void saveProductTest() throws Exception {
		Product product = getProduct();
		ProductDTO productDTO = getProductDto();

        when(productDaoMock.save(product)).thenReturn(product);
        when(mockedMapper.map(product, ProductDTO.class)).thenReturn(productDTO);
        when(mockedMapper.map(productDTO, Product.class)).thenReturn(product);

        ProductDTO aProductDto = catalogService.createProduct(productDTO);

        verify(productDaoMock).save(product);

		assertNotNull(aProductDto);
		assertEquals(productDTO, aProductDto);
	}	
	
	@Test 
	public void udapteProductTest() throws Exception {
		
		Product product = getProduct();
		ProductDTO productDto = getProductDto();
		
		when(productDaoMock.findOne(product.getId())).thenReturn(product);
		when(mockedMapper.map(productDto, Product.class)).thenReturn(product);
		
		catalogService.updateProduct(productDto);
		
		verify(productDaoMock).save(product);
	}

	@Test 
	public void deleteProductTest() throws Exception {
		// create a id with type of long integer 
		Long id = Long.valueOf(1);
		// set the action of the function delete
        doNothing().when(productDaoMock).delete(id);
        // call the function
		catalogService.deleteProduct(id);
		// check the function delete has been called
		verify(productDaoMock).delete(id);
	}
	

	@Test 
	public void findProductsTest() throws Exception {
		long categoryId = Long.valueOf(1);
		String categoryName = "categoryXY";
		Product product01 = getProduct();
		Product product02 = getProduct2();
		
		ProductDTO productDto01 = getProductDto();
		ProductDTO productDto02 = getProductDto();
		
		List<Product> lst = new ArrayList<Product>();
		lst.add(product01);
		lst.add(product02);
		
		when(mockedMapper.map(product01, ProductDTO.class)).thenReturn(productDto01);
		when(mockedMapper.map(product02, ProductDTO.class)).thenReturn(productDto02);
		
		when(productDaoMock.findByCategoryId(categoryId)).thenReturn(lst);
		when(productDaoMock.findAll()).thenReturn(lst);
		when(productDaoMock.findByCategoryName(categoryName)).thenReturn(lst);
		
		List<ProductDTO> rListDto = catalogService.findProducts(categoryId);
		
		assertNotNull(rListDto);
		assertEquals(2, rListDto.size());
		
		ProductDTO rProductDto01 = rListDto.get(0);
		ProductDTO rProductDto02 = rListDto.get(1);
		assertNotNull(rProductDto01);
		assertNotNull(rProductDto02);
		
		assertEquals(productDto01, rProductDto01);
		assertEquals(productDto02, rProductDto02);
	}	

	@Test 
	public void findProductTest() throws Exception {
		Product product  = getProduct();
		ProductDTO productDto = getProductDto();
		when(productDaoMock.findOne(product.getId())).thenReturn(product);
		when(mockedMapper.map(product, ProductDTO.class)).thenReturn(productDto);
		
		ProductDTO rProductDto = catalogService.findProduct(product.getId());
		
		assertNotNull(rProductDto);
		assertEquals(Long.valueOf(1L), rProductDto.getId());
		assertEquals(productDto, rProductDto);
		
	}
	

	/* ---------------------------------- */
	/* ---------------------------------- */
	/* ---------------------------------- */
	
	@Test 
	public void saveItemTest() throws Exception {
		Item item  = getItem();
		ItemDTO itemDto = getItemDto();
		when(itemDaoMock.save(item)).thenReturn(item);
		when(mockedMapper.map(item, ItemDTO.class)).thenReturn(itemDto);
		when(mockedMapper.map(itemDto, Item.class)).thenReturn(item);
		ItemDTO rItemDto = catalogService.createItem(itemDto);
		verify(itemDaoMock).save(item);
		assertNotNull(rItemDto);
		assertEquals(itemDto, rItemDto);
	}	
	
	@Test 
	public void updateItemTest() throws Exception {
		Item item = getItem();
		ItemDTO itemDto = getItemDto();
		
		when(itemDaoMock.findOne(item.getId())).thenReturn(item);
		when(mockedMapper.map(itemDto, Item.class)).thenReturn(item);
		
		catalogService.updateItem(itemDto);
		
		verify(itemDaoMock).save(item);
	}
	
	
	@Test 
	public void deleteItemTest() throws Exception {
		// create a id with type of long integer 
		Long id = Long.valueOf(1);
		// set the action of the function delete
        doNothing().when(itemDaoMock).delete(id);
        // call the function
		catalogService.deleteItem(id);
		// check the function delete has been called
		verify(itemDaoMock).delete(id);
	}

    @Test
    public void findItemsTest() throws Exception {
		Item item01 = getItem();
		Item item02 = getItem2();
		
		ItemDTO itemDto01 = getItemDto();
		ItemDTO itemDto02 = getItemDto();
		
		List<Item> lst = new ArrayList<Item>();
		lst.add(item01);
		lst.add(item02);
		
		when(mockedMapper.map(item01, ItemDTO.class)).thenReturn(itemDto01);
		when(mockedMapper.map(item02, ItemDTO.class)).thenReturn(itemDto02);
		
		
		when(itemDaoMock.findAll()).thenReturn(lst);
		
		List<ItemDTO> rListDto = catalogService.findItems();
		
		assertNotNull(rListDto);
		assertEquals(2, rListDto.size());
		
		ItemDTO rItemDto01 = rListDto.get(0);
		ItemDTO rItemDto02 = rListDto.get(1);
		assertNotNull(rItemDto01);
		assertNotNull(rItemDto02);
		
		assertEquals(itemDto01, rItemDto01);
		assertEquals(itemDto02, rItemDto02);
    }
	

	@Test 
	public void findItemTest() throws Exception {
		Item item  = getItem();
		ItemDTO itemDto = getItemDto();
		when(itemDaoMock.findOne(item.getId())).thenReturn(item);
		when(mockedMapper.map(item, ItemDTO.class)).thenReturn(itemDto);
		
		ItemDTO rItemDto = catalogService.findItem(item.getId());
		
		assertNotNull(rItemDto);
		assertEquals(Long.valueOf(1L), rItemDto.getId());
		assertEquals(itemDto, rItemDto);
	}
	
	@Test 
	public void findItemsByProductIdTest() throws Exception {
		long productId = Long.valueOf(1);
		Item item01 = getItem();
		Item item02 = getItem2();
		
		ItemDTO itemDto01 = getItemDto();
		ItemDTO itemDto02 = getItemDto();
		
		List<Item> lst = new ArrayList<Item>();
		lst.add(item01);
		lst.add(item02);
		
		when(mockedMapper.map(item01, ItemDTO.class)).thenReturn(itemDto01);
		when(mockedMapper.map(item02, ItemDTO.class)).thenReturn(itemDto02);
		
		when(itemDaoMock.findByProductId(productId)).thenReturn(lst);
		when(itemDaoMock.findAll()).thenReturn(lst);
		
		List<ItemDTO> rListDto = catalogService.findItems(productId);
		
		assertNotNull(rListDto);
		assertEquals(2, rListDto.size());
		
		ItemDTO rItemDto01 = rListDto.get(0);
		ItemDTO rItemDto02 = rListDto.get(1);
		assertNotNull(rItemDto01);
		assertNotNull(rItemDto02);
		
		assertEquals(itemDto01, rItemDto01);
		assertEquals(itemDto02, rItemDto02);
	}	
	
	
	@Test 
	public void searchItemsTest() throws Exception {
		String keyword = "keyword";
		Item item01 = getItem();
		Item item02 = getItem2();
		
		ItemDTO itemDto01 = getItemDto();
		ItemDTO itemDto02 = getItemDto();
		
		List<Item> lst = new ArrayList<Item>();
		lst.add(item01);
		lst.add(item02);
		
		when(mockedMapper.map(item01, ItemDTO.class)).thenReturn(itemDto01);
		when(mockedMapper.map(item02, ItemDTO.class)).thenReturn(itemDto02);
		
		when(itemDaoMock.findByNameContaining(keyword)).thenReturn(lst);
		
		List<ItemDTO> rListDto = catalogService.searchItems(keyword);
		
		assertNotNull(rListDto);
		assertEquals(2, rListDto.size());
		
		ItemDTO rItemDto01 = rListDto.get(0);
		ItemDTO rItemDto02 = rListDto.get(1);
		assertNotNull(rItemDto01);
		assertNotNull(rItemDto02);
		
		assertEquals(itemDto01, rItemDto01);
		assertEquals(itemDto02, rItemDto02);
	}
	
	
	
	/* ---------------------------------- */
	/* --------- Private methods -------- */
	/* ---------------------------------- */
	
	private CategoryDTO getCategoryDto(){
		CategoryDTO category = new CategoryDTO(Long.valueOf(1L), "categoryName", "categoryDesc");
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
	
	

	private Product getProduct(){
		Product product = new Product(Long.valueOf(1L), "productName", "productDesc");
		return product;
	}	
	
	private Product getProduct2(){
		Product product = new Product(Long.valueOf(2L), "productName2", "productDesc2");
		return product;
	}
	
	
	
	private ItemDTO getItemDto(){
		ItemDTO itemDto = new ItemDTO(Long.valueOf(1L),"articleID",20.0);
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
