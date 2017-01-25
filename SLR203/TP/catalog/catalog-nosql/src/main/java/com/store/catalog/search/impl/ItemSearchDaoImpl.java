package com.store.catalog.search.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.catalog.search.ItemSearchDao;
import com.store.catalog.model.SearchableItem;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZCadi on 05/11/2015.
 */
@Component
public class ItemSearchDaoImpl implements ItemSearchDao {


    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Value("${es.index.name.catalogdb}")
    protected String indexCatalog;


    @Value("${es.index.type.phone}")
    protected String typePhone;



    protected String timeout;

    @Autowired
    protected Client esClient;


    @Autowired
    protected ObjectMapper jacksonMapper;


    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }


    public void setEsClient(Client esClient) {
        this.esClient = esClient;
    }

    @Override
    public String create(SearchableItem searchableItem) throws Exception {

        String json = jacksonMapper.writeValueAsString(searchableItem);
        IndexResponse response = esClient.prepareIndex(indexCatalog,typePhone,searchableItem.get_id())
                .setSource(json)
                .execute()
                .actionGet();

        esClient.admin().indices().prepareRefresh().execute().actionGet();

        return response.getId();
    }

    @Override
    public SearchableItem update(SearchableItem searchableItem) throws Exception {
        String json = jacksonMapper.writeValueAsString(searchableItem);

        esClient.prepareUpdate(indexCatalog,typePhone,searchableItem.get_id()).setDoc(json).get();

        esClient.admin().indices().prepareRefresh().execute().actionGet();

        GetResponse resultResponse = esClient.prepareGet(indexCatalog,typePhone,searchableItem.get_id())
                .execute()
                .actionGet();


        SearchableItem result = jacksonMapper.readValue(resultResponse.getSourceAsString(), SearchableItem.class);

        return result;
    }


    @Override
    public SearchableItem get(String id) throws Exception {
        GetResponse resultResponse = esClient.prepareGet(indexCatalog,typePhone,id)
                .execute()
                .actionGet();


        SearchableItem result = null;
        if (resultResponse!= null && resultResponse.isExists()) {
            result = jacksonMapper.readValue(resultResponse.getSourceAsString(), SearchableItem.class);
        }

        return result;

    }


    @Override
    public boolean delete(String id) throws Exception {

        DeleteResponse response = esClient.prepareDelete(indexCatalog,typePhone, id)
                .execute()
                .actionGet();

        return response.isFound();
    }


    public List<SearchableItem> searchItems(String keyword) throws Exception{
        List<SearchableItem> list = new ArrayList<SearchableItem>();

        QueryBuilder qb = QueryBuilders.matchQuery("name", keyword);

        //QueryBuilder qb = QueryBuilders.matchQuery("name", keyword).operator(MatchQueryBuilder.Operator.AND);
        //QueryBuilder qb = QueryBuilders.matchQuery("name", keyword);


        SearchResponse response = esClient.prepareSearch(indexCatalog)
                .setTypes(typePhone)
                .setQuery(qb)
                .setSize(20)
                .execute()
                .actionGet();


        for (SearchHit hit : response.getHits().getHits()) {

            SearchableItem item = jacksonMapper.readValue(hit.getSourceAsString(), SearchableItem.class);

            list.add(item);

            //nothing found
            if (hit.isSourceEmpty()) {
                log.info("nothing found in index {} -  type {}",indexCatalog,typePhone);
            }
        }

        return list;
    }


    public List<SearchableItem> matchPhrasePrefixItems(String keyword, Integer from, Integer page) throws Exception{

        if (from == null){
            from = Integer.valueOf(0);
        }

        if (page == null){
            page = Integer.valueOf(10);
        }

        List<SearchableItem> list = new ArrayList<SearchableItem>();

        QueryBuilder qb = QueryBuilders.matchPhrasePrefixQuery("name", keyword);

        SearchResponse response = esClient
                .prepareSearch(indexCatalog)
                .setTypes(typePhone)
                .setQuery(qb)
                .setFrom(from.intValue())
                .setSize(page.intValue())
                .execute()
                .actionGet();


        for (SearchHit hit : response.getHits().getHits()) {

            SearchableItem item = jacksonMapper.readValue(hit.getSourceAsString(), SearchableItem.class);

            list.add(item);

            //nothing found
            if (hit.isSourceEmpty()) {
                log.info("nothing found in index {} -  type {}",indexCatalog,typePhone);
            }
        }

        return list;
    }



    @Override
    public Long countAll() throws Exception {

        CountResponse response = esClient.prepareCount(indexCatalog)
                .setQuery(QueryBuilders.termQuery("_type", typePhone))
                .execute()
                .actionGet();

        if (response != null) {
            return Long.valueOf(response.getCount())  ;
        } else {
            return null;
        }
    }

}
