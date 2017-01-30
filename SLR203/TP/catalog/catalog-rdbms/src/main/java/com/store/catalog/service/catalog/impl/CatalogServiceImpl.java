package com.store.catalog.service.catalog.impl;

import com.store.catalog.model.*;
import com.store.catalog.service.catalog.CatalogService;
import com.store.catalog.common.exception.CheckException;
import com.store.catalog.dao.CategoryDao;
import com.store.catalog.dao.ItemDao;
import com.store.catalog.dao.ProductDao;
import org.dozer.Mapper;
import org.hibernate.mapping.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

	Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class.getName());


	// the object which is responsible for data access
	@Autowired
	private CategoryDao categoryDao ;
	
	@Autowired
	protected ProductDao productDao ;
	
	@Autowired
	private ItemDao itemDao ;
	
	@Autowired
	private Mapper dozerMapper;



	public Mapper getDozerMapper() {
		return dozerMapper;
	}

	public void setDozerMapper(Mapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

    public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	



	// ======================================
	// = Product Business methods =
	// ======================================
	// DTO: data transferring object
//	@Transactional
	public ProductDTO createProduct(ProductDTO productDto) throws CheckException {
		if (productDto == null)
            throw new CheckException("Product object is null");
        
		logger.info("start");
		// we construct a product object from productDTO using dozerMapper and Product.class
		// we can get a product object from a productDto object
		Product product = dozerMapper.map(productDto, Product.class);
		// we save the product object to the productDao which is used to operate(CRUD) the storage of objects.
		productDao.save(product);
		// we get the workDto from the product which we got previous 
		ProductDTO productworkDto = dozerMapper.map(product, ProductDTO.class);
		
		logger.info("end");
		return productworkDto;
	}

	@Transactional(readOnly=true)
	public List<ProductDTO> findProducts() {
		Iterable<Product> products = productDao.findAll();
		List<ProductDTO> productsDto = new ArrayList<ProductDTO>();
		for(Product obj:products){
			productsDto.add(dozerMapper.map(obj, ProductDTO.class));
		}
		return productsDto;
	}

	@Transactional(readOnly=true)
	public List<ProductDTO>  findProducts(Long categoryId) throws CheckException {
		// if categoryId exists!
		if(categoryId == null && "".equals(categoryId)){
			throw new CheckException("Invalid category id!");
		}
		List<ProductDTO> productsDto = new ArrayList<ProductDTO>();	

		List<Product> products = productDao.findByCategoryId(categoryId);

		for(Product obj:products){
			productsDto.add(dozerMapper.map(obj, ProductDTO.class));
		}
		return productsDto;
	}

	@Transactional(readOnly=true)
	public ProductDTO findProduct(Long productId) throws CheckException {
		if(productId == null && "".equals(productId)){
			throw new CheckException("Invalid product Id!");
		}
		Product product = productDao.findOne(productId);
		ProductDTO productDto  = dozerMapper.map(product, ProductDTO.class);
		return productDto;
	}	
	

	@Transactional(readOnly=true)
 	public List<ProductDTO> findProducts(String categoryName) throws CheckException {
		if(categoryName == null && "".equals(categoryName)){
			throw new CheckException("Invalid categoryName");
		}
		List<Product> lst = productDao.findByCategoryName(categoryName);
		List<ProductDTO> lstDto = new ArrayList<ProductDTO>();
		for(Product product:lst){
			lstDto.add(dozerMapper.map(product, ProductDTO.class));
		}
		return lstDto;
	}	
	
	@Transactional
	public void updateProduct(ProductDTO productDto) throws CheckException {
		if(productDto == null){
			throw new CheckException("Product object is null");
		}
		if(productDto.getId() == null){
			throw new CheckException("Invalid product Id");
		}
		Product foundProduct = productDao.findOne(productDto.getId());
		if(foundProduct == null){
			throw new CheckException("Unknow Id");
		}
		
		Product product = dozerMapper.map(productDto, Product.class);
		
		productDao.save(product);
	}

	@Transactional
	public void deleteProduct(Long productId) throws CheckException {
		if(productId == null && "".equals(productId)){
			throw new CheckException("Invalid product id");
		}
		productDao.delete(productId);
	}
	
    // ======================================
    // =        Item Business methods       =
    // ======================================	
	
//	@Transactional
	public ItemDTO createItem(ItemDTO itemDto) throws CheckException {
		if(itemDto == null){
			throw new CheckException("Item object is null");
		}
		Item item = dozerMapper.map(itemDto, Item.class);		
		itemDao.save(item);		
		ItemDTO workItemDto = dozerMapper.map(item, ItemDTO.class);		
		return workItemDto;
		
	}

	@Transactional
	public void updateItem(ItemDTO itemDto) throws CheckException {
		if(itemDto == null){
			throw new CheckException("Item object is null");
		}
		if(itemDto.getId() == null){
			throw new CheckException("Invalid item Id");
		}
		Item foundItem = itemDao.findOne(itemDto.getId());
		if(foundItem == null){
			throw new CheckException("Unknow Id");
		}
		
		Item item = dozerMapper.map(itemDto, Item.class);
		
		itemDao.save(item);
		
	}


	@Transactional
	public void deleteItem(Long itemId) throws CheckException {
		if(itemId == null && "".equals(itemId)){
			throw new CheckException("Invalid item Id");
		}
		
		itemDao.delete(itemId);
	}

	@Transactional(readOnly=true)
	public List<ItemDTO> findItems() {
		
		Iterable<Item> iter = itemDao.findAll();
		List<ItemDTO> lstDto = new ArrayList<ItemDTO>();
		for(Item item:iter){
			lstDto.add(dozerMapper.map(item, ItemDTO.class));
		}
		return lstDto;
	}

	@Transactional(readOnly=true)
	public List<ItemDTO> findItems(Long productId) throws CheckException {
		if(productId == null && "".equals(productId)){
			throw new CheckException("Invalid product Id");
		}
		List<Item> lst = itemDao.findByProductId(productId);
		List<ItemDTO> lstDto = new ArrayList<ItemDTO>();
		for(Item item:lst){
			lstDto.add(dozerMapper.map(item, ItemDTO.class));
		}
		return lstDto;
	}

	
	@Transactional(readOnly=true)
	public List<ItemDTO> searchItems(String keyword) throws CheckException{
		if(keyword == null && "".equals(keyword)){
			throw new CheckException("Invalid keyword");
		}
		List<Item> lst = itemDao.findByNameContaining(keyword);
		List<ItemDTO> lstDto = new ArrayList<ItemDTO>();
		for(Item item:lst){
			lstDto.add(dozerMapper.map(item, ItemDTO.class));
		}
		return lstDto;

	}



	@Transactional(readOnly=true)
	public ItemDTO findItem(Long itemId) throws CheckException {
		if(itemId == null && "".equals(itemId)){
			throw new CheckException("Invalid item Id");
		}
		Item item = itemDao.findOne(itemId);
		ItemDTO itemDto = dozerMapper.map(item, ItemDTO.class);
		return itemDto;
		
	}

	
    // ======================================
    // =        Category Business methods       =
    // ======================================	
	
	
//	@Transactional
	public CategoryDTO createCategory(CategoryDTO categoryDto) throws CheckException {
		if (categoryDto == null)
            throw new CheckException("Category object is null");
        
		logger.info("start");
		Category category = dozerMapper.map(categoryDto, Category.class);
		
		categoryDao.save(category);
		
		CategoryDTO categoryworkDto = dozerMapper.map(category, CategoryDTO.class);
		
		logger.info("end");
		return categoryworkDto;
	}

	@Transactional
	public void deleteCategory(Long categoryId) throws CheckException {
		if (categoryId == null || "".equals(categoryId))
            throw new CheckException("Invalid id");
        
		categoryDao.delete(categoryId);		
	}

	@Transactional
	public void updateCategory(CategoryDTO categoryDto) throws CheckException {
		if (categoryDto == null)
            throw new CheckException("Category object is null");
        
		if (categoryDto.getId() == null)
            throw new CheckException("Invalid id");


		Category foundCategory =  categoryDao.findOne(categoryDto.getId());

		if(foundCategory == null){
			throw new CheckException("unkown id");
		}

		Category category = dozerMapper.map(categoryDto, Category.class);
		categoryDao.save(category);
	}


	@Transactional(readOnly=true)
	public CategoryDTO findCategory(Long categoryId) throws CheckException {
		if (categoryId == null || "".equals(categoryId))
            throw new CheckException("Invalid category id");


        Category category = categoryDao.findOne(categoryId);
		
		CategoryDTO categoryDto = dozerMapper.map(category,CategoryDTO.class);
		return categoryDto;
	}
	
	@Transactional(readOnly=true)
	public List<CategoryDTO> findCategories() {

        Iterable<Category> lst =  categoryDao.findAll();

		List<CategoryDTO> lstDto = new ArrayList<CategoryDTO>();

		for (Category obj:lst){
	        CategoryDTO categoryDto = dozerMapper.map(obj, CategoryDTO.class);
	        lstDto.add(categoryDto);
		}


		return lstDto;
	}


}
