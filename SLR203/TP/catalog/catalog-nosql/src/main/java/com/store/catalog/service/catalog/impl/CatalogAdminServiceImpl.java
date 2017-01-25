package com.store.catalog.service.catalog.impl;

import com.store.catalog.search.ItemSearchDao;
import com.store.catalog.service.catalog.CatalogAdminService;
import com.store.catalog.model.SearchableItem;
import com.store.catalog.mongo.ItemMongoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zouheir on 11/11/15.
 */
@Service
public class CatalogAdminServiceImpl implements CatalogAdminService {


    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ItemSearchDao itemSearchDao;


    @Autowired
    private ItemMongoDao itemMongoDao;


    public CatalogAdminServiceImpl() {
    }



    public void setItemSearchDao(ItemSearchDao itemSearchDao) {
        this.itemSearchDao = itemSearchDao;
    }


    public void setItemMongoDao(ItemMongoDao itemMongoDao) {
        this.itemMongoDao = itemMongoDao;
    }

    @Override
    public String createItem(SearchableItem searchableItem) throws Exception {
        String id = itemMongoDao.create(searchableItem);

        itemSearchDao.create(searchableItem);

        return id;
    }

    @Override
    public SearchableItem updateItem(SearchableItem searchableItem) throws Exception {
        SearchableItem item = itemMongoDao.update(searchableItem);
        itemSearchDao.update(searchableItem);

        return item;
    }


    @Override
    public SearchableItem getItem(String id) throws Exception {
        SearchableItem item = itemSearchDao.get(id);

        return item;
    }


    @Override
    public void deleteItem(String id) throws Exception {
        itemMongoDao.delete(id);
        itemSearchDao.delete(id);

    }

}
