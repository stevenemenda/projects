package com.store.catalog.mongo;

import com.store.catalog.model.SearchableItem;

/**
 * Created by ZCadi on 10/11/2015.
 */
public interface ItemMongoDao {


    public String create(SearchableItem searchableItem) throws Exception ;


    public SearchableItem update(SearchableItem searchableItem) throws Exception ;


    public SearchableItem get(String id) throws Exception ;


    public void delete(String id) throws Exception ;


    public Long countAll() throws Exception ;
}
