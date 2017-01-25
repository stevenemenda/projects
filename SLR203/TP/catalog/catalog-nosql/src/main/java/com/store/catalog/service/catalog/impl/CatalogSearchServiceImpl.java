package com.store.catalog.service.catalog.impl;

import com.store.catalog.search.ItemSearchDao;
import com.store.catalog.service.catalog.CatalogSearchService;
import com.store.catalog.model.SearchableItem;
import com.store.catalog.mongo.ItemMongoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZCadi on 04/11/2015.
 */
@Service
public class CatalogSearchServiceImpl implements CatalogSearchService {


    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ItemSearchDao itemSearchDao;

    @Autowired
    private ItemMongoDao itemMongoDao;



    public CatalogSearchServiceImpl() {
    }

    public CatalogSearchServiceImpl(ItemSearchDao itemSearchDao) {
        this.itemSearchDao = itemSearchDao;
    }


    public void setItemSearchDao(ItemSearchDao itemSearchDao) {
        this.itemSearchDao = itemSearchDao;
    }

    public void setItemMongoDao(ItemMongoDao itemMongoDao) {
        this.itemMongoDao = itemMongoDao;
    }

    @Override
    public List<SearchableItem> searchItems(String keyword) throws Exception {
        return itemSearchDao.searchItems(keyword);
    }


    @Override
    public List<SearchableItem> matchPhrasePrefixeItems(String keyword, int from, int page) throws Exception {
        return itemSearchDao.matchPhrasePrefixItems(keyword, from, page);
    }
}
