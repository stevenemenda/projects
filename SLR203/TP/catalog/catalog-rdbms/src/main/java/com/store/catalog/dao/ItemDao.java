package com.store.catalog.dao;




import com.store.catalog.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao extends CrudRepository<Item, Long> {

    public List<Item> findByProductId(Long productId);

    //Containing
    public List<Item> findByNameContaining(String name);
	
}
