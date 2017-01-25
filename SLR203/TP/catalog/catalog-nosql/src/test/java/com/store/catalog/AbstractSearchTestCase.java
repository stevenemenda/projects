package com.store.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.catalog.model.SearchableItem;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by ZCadi on 05/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/es-conf-test.xml"})
public class AbstractSearchTestCase {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Client esClient;

    //@Value("#{appProperties['es.index.name.catalogdb']}")
    @Value("${es.index.name.catalogdb}")
    protected String indexCatalog;


    //@Value("#{appProperties['es.index.type.phone']}")
    @Value("${es.index.type.phone}")
    protected String typePhone;

    @Autowired
    private ObjectMapper jacksonMapper;


    public void setJacksonMapper(ObjectMapper jacksonMapper) {
        this.jacksonMapper = jacksonMapper;
    }


    protected void createIndexReferential() throws Exception{


        //delete to provide a clean environment
        DeleteIndexResponse delete = esClient.admin().indices().delete(new DeleteIndexRequest(indexCatalog)).actionGet();
        if (!delete.isAcknowledged()) {
            log.error("Index wasn't deleted");
        }


        //create
        esClient.admin().indices().create(new CreateIndexRequest(indexCatalog)).actionGet();


        createPhoneMapping();

        BulkRequestBuilder brb1 = esClient.prepareBulk();
        addTypePhones(brb1);
        BulkResponse br1 = brb1.execute().actionGet();
        Assert.assertFalse(br1.hasFailures());

        esClient.admin().indices().prepareRefresh().execute().actionGet();
        esClient.admin().indices().flush(new FlushRequest(indexCatalog).force(true)).actionGet();

    }


    protected void createPhoneMapping() throws IOException {

        final XContentBuilder mappingBuilder = jsonBuilder().startObject()
                .startObject("properties")
                .startObject("age")
                .field("type", "integer")
                .field("store", "yes")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("name")
                .field("type", "string")
                //.field("store", "yes")
                .field("index", "analyzed")
                .endObject()
                .startObject("snippet")
                .field("type", "string")
                .field("store", "yes")
                .field("index", "analyzed")
                .endObject()
                .startObject("imageUrl")
                .field("type", "string")
                .field("store", "yes")
                .field("index", "analyzed")
                .endObject()
                .endObject()
                .endObject();

        esClient.admin().indices().preparePutMapping(indexCatalog).setType(typePhone).setSource(mappingBuilder).get();

    }


    protected void addTypePhones(BulkRequestBuilder brb) throws Exception {



        SearchableItem item1 = new SearchableItem(Integer.valueOf(1),"image.jpg","Xoom the best phone","snippets1");
        IndexRequest irq1 = new IndexRequest(indexCatalog, typePhone,item1.get_id());
        irq1.source(jacksonMapper.writeValueAsString(item1));
        brb.add(irq1);

        SearchableItem item2 = new SearchableItem(Integer.valueOf(2),"image2.jpg","Samsung from Korea","snippets2");
        IndexRequest irq2 = new IndexRequest(indexCatalog, typePhone,item2.get_id());
        irq2.source(jacksonMapper.writeValueAsString(item2));
        brb.add(irq2);

        SearchableItem item3 = new SearchableItem(Integer.valueOf(3),"image3.jpg","sony first item","snippets3");
        IndexRequest irq3 = new IndexRequest(indexCatalog,typePhone,item3.get_id());
        irq3.source(jacksonMapper.writeValueAsString(item3));
        brb.add(irq3);


        SearchableItem item4 = new SearchableItem(Integer.valueOf(4),"image4.jpg","Another xoom from the US","snippets");
        IndexRequest irq4 = new IndexRequest(indexCatalog, typePhone,item4.get_id());
        irq4.source(jacksonMapper.writeValueAsString(item4));
        brb.add(irq4);

        SearchableItem item5 = new SearchableItem(Integer.valueOf(5),"image5.jpg","sony second time","snippets");
        IndexRequest irq5 = new IndexRequest(indexCatalog, typePhone,item5.get_id());
        irq5.source(jacksonMapper.writeValueAsString(item5));
        brb.add(irq5);

        SearchableItem item6 = new SearchableItem(Integer.valueOf(6),"image6.jpg","sony the third","snippets");
        IndexRequest irq6 = new IndexRequest(indexCatalog, typePhone,item6.get_id());
        irq6.source(jacksonMapper.writeValueAsString(item6));
        brb.add(irq6);

        SearchableItem item7 = new SearchableItem(Integer.valueOf(7),"image7.jpg","this a not sony the third","snippets");
        IndexRequest irq7 = new IndexRequest(indexCatalog, typePhone,item7.get_id());
        irq7.source(jacksonMapper.writeValueAsString(item7));
        brb.add(irq7);

        SearchableItem item8 = new SearchableItem(Integer.valueOf(8),"image8.jpg","sony the forth","snippets");
        IndexRequest irq8 = new IndexRequest(indexCatalog, typePhone,item8.get_id());
        irq8.source(jacksonMapper.writeValueAsString(item8));
        brb.add(irq8);

        SearchableItem item9 = new SearchableItem(Integer.valueOf(9),"image9.jpg","windows phone","snippets");
        IndexRequest irq9 = new IndexRequest(indexCatalog, typePhone,item9.get_id());
        irq9.source(jacksonMapper.writeValueAsString(item9));
        brb.add(irq9);

    }


}
