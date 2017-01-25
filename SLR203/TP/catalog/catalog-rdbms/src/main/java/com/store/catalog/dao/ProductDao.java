package com.store.catalog.dao;

import com.store.catalog.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends CrudRepository<Product, Long> {

    public List<Product> findByCategoryId(Long categoryId);

    
	public List<Product> findByCategoryName(String categoryName);
    
}
