package com.store.catalog.ws.rest.view.rdbms;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.store.catalog.model.CategoryDTO;
import com.store.catalog.model.ItemDTO;
import com.store.catalog.model.ProductDTO;
import com.store.catalog.utils.ConstantUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static com.store.catalog.utils.ConstantUtils.ITEM_IMAGE_PATH;
import static com.store.catalog.utils.ConstantUtils.ITEM_NAME;
import static com.store.catalog.utils.ConstantUtils.ITEM_PRICE;
import static com.store.catalog.utils.ConstantUtils.PRODUCT_DESCRIPTION;
import static com.store.catalog.utils.ConstantUtils.PRODUCT_NAME;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class CatalogResourceIntegrationTest{

    private ObjectMapper mapper = new ObjectMapper();
    static private WebTarget target;

    List<CategoryDTO> lsCats;
    List<ProductDTO> lsPros;
    List<ItemDTO> lsItes;
    @BeforeClass
    static public void setup() {
        Client client = ClientBuilder.newClient();
//        target = client.target("http://localhost:8080/catalog-rest-jersey/catalog");
        target = client.target("http://localhost:8080/catalog/catalog");
    }
    // category
    
    /*
    curl -i http://localhost:8080/catalog/catalog/category -H "Content-Type: application/json" -X POST -d '
{"id":20,"name":"Dinosorus","description":"prehistoric animals",
	"products":null}'


curl -i http://localhost:8080/catalog/catalog/category/20 -H "Content-Type: application/json" -X GET

curl -i http://localhost:8080/catalog/catalog/category -H "Content-Type: application/json" -X PUT -d '{"id":20,"name":"Dinosorus","description":"prehistoric animals updated",
	"products":null}'


curl -i http://localhost:8080/catalog/catalog/category/20 -H "Content-Type: application/json" -X DELETE


     */
    @Before
    public void initInfo() throws JsonParseException, JsonMappingException, IOException{
    	String res = target.path("categories").request().accept(MediaType.APPLICATION_JSON).get(String.class);
        lsCats = mapper.readValue(res, TypeFactory.defaultInstance().constructCollectionType(List.class, CategoryDTO.class));
        
        res = target.path("products").request().accept(MediaType.APPLICATION_JSON).get(String.class);
        lsPros = mapper.readValue(res, TypeFactory.defaultInstance().constructCollectionType(List.class, ProductDTO.class));
        
        res = target.path("items").request().accept(MediaType.APPLICATION_JSON).get(String.class);
        lsItes = mapper.readValue(res, TypeFactory.defaultInstance().constructCollectionType(List.class, ItemDTO.class));
    }
    
    @After
    public void clearObjs(){
    	
    	lsCats = null;
    	lsPros = null;
    	lsItes = null;
    	
    }
    
    @Test
    public void request_on_categories_should_success() throws IOException {
    	
        String res = target.path("categories").request().accept(MediaType.APPLICATION_JSON).get(String.class);
        List<CategoryDTO> categories = mapper.readValue(res, TypeFactory.defaultInstance().constructCollectionType(List.class, CategoryDTO.class));

        assertEquals(categories.size(), lsCats.size());

    }

    @Test
    public void request_on_category_should_success() throws IOException {
    	CategoryDTO cat = lsCats.get(0);
        CategoryDTO category = target.path("category/" + cat.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(CategoryDTO.class);

        assertEquals(category.getId(), cat.getId());
    }

    @Test
    public void create_on_category_should_success() throws IOException {

        CategoryDTO category = createCategory();

        target.path("category").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(category, MediaType.APPLICATION_JSON), CategoryDTO.class);

        CategoryDTO res = target.path("category/" + category.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(CategoryDTO.class);

        assertEquals(res, category);
        assertEquals(res.getDescription(), category.getDescription());
        assertEquals(res.getName(), category.getName());

    }


    @Test
    public void update_on_category_should_success() throws IOException {

        CategoryDTO category = createCategory();
        
        target.path("category").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(category, MediaType.APPLICATION_JSON), CategoryDTO.class);
        CategoryDTO res = target.path("category/" + category.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(CategoryDTO.class);

        res.setDescription("cool");

        target.path("category").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(res, MediaType.APPLICATION_JSON));

        res = target.path("category/" + category.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(CategoryDTO.class);

        assertEquals(res.getDescription(), "cool");
    }

    // product
    
    @Test
    public void request_on_products_should_success() throws IOException {
    	
        String res = target.path("products").request().accept(MediaType.APPLICATION_JSON).get(String.class);
        List<ProductDTO> products = mapper.readValue(res, TypeFactory.defaultInstance().constructCollectionType(List.class, ProductDTO.class));
        ;

        assertEquals(products.size(), lsPros.size());

        assertEquals(products.get(0).getId(), lsPros.get(0).getId());
        assertEquals(products.get(0).getItems().size(), lsPros.get(0).getItems().size());
    }

    @Test
    public void request_on_product_should_success() throws IOException {
    	ProductDTO product = lsPros.get(0);
        ProductDTO product1 = target.path("product/" + product.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(ProductDTO.class);

        assertEquals(product.getId(), product1.getId());
    }

    @Test
    public void create_on_product_should_success() throws IOException {

        ProductDTO product = createProduct();

        target.path("product").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(product, MediaType.APPLICATION_JSON), ProductDTO.class);

        ProductDTO res = target.path("product/" + product.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(ProductDTO.class);

        assertEquals(res, is(notNullValue()));
        assertEquals(res.getDescription(), product.getDescription());
        assertEquals(res.getName(), product.getName());

    }


    @Test
    public void update_on_product_should_success() throws IOException {

        ProductDTO product = createProduct();
        target.path("product").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(product, MediaType.APPLICATION_JSON), ProductDTO.class);
        ProductDTO res = target.path("product/" + product.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(ProductDTO.class);

        res.setDescription("cool");

        target.path("product").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(res, MediaType.APPLICATION_JSON));

        res = target.path("product/" + product.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(ProductDTO.class);

        assertEquals(res.getDescription(), "cool");
    }
    
    // item
    
    @Test
    public void request_on_items_should_success() throws IOException {
        String res = target.path("items").request().accept(MediaType.APPLICATION_JSON).get(String.class);
        List<ItemDTO> items = mapper.readValue(res, TypeFactory.defaultInstance().constructCollectionType(List.class, ItemDTO.class));
        

        assertEquals(items.size(), lsItes.size());

        assertEquals(items.get(0).getId(), lsItes.get(0).getId());
    }

    @Test
    public void request_on_item_should_success() throws IOException {
    	ItemDTO item = lsItes.get(0);
        ItemDTO item1 = target.path("item/" + item.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(ItemDTO.class);

        assertEquals(item1.getId(), item1.getId());
    }

    @Test
    public void create_on_item_should_success() throws IOException {

        ItemDTO item = createItem();

        target.path("item").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(item, MediaType.APPLICATION_JSON), ItemDTO.class);

        ItemDTO res = target.path("item/" + item.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(ItemDTO.class);

        assertEquals(res, is(notNullValue()));
        assertEquals(res.getName(), item.getName());

    }


    @Test
    public void update_on_item_should_success() throws IOException {

        ItemDTO item = createItem();
        target.path("item").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(item, MediaType.APPLICATION_JSON), ItemDTO.class);
        ItemDTO res = target.path("item/" + item.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(ItemDTO.class);
        
        res.setImagePath("/lguo");

        target.path("item").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(res, MediaType.APPLICATION_JSON));

        res = target.path("item/" + item.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(ItemDTO.class);
        
        assertEquals(res.getImagePath(), equalTo("cool"));
        
    }
    /* ---------------------------------- */
    /* --------- Private methods -------- */
	/* ---------------------------------- */
    
    
    private CategoryDTO createCategory() {
        CategoryDTO category = new CategoryDTO();
        category.setId(new Random().nextLong() + 10000);
		category.setName(ConstantUtils.CATEGOY_NAME);
		category.setDescription(ConstantUtils.CATEGORY_DESCRIPTION);
        return category;
    }
    
    private ProductDTO createProduct() {
        ProductDTO product = new ProductDTO();
        product.setId(new Random().nextLong() + 1000);
    	product.setName(PRODUCT_NAME);
    	product.setDescription(PRODUCT_DESCRIPTION);
    	product.setCategory(lsCats.get(0));
    	product.setItems(null);
        return product;
    }
    
    private ItemDTO createItem() {
        ItemDTO item = new ItemDTO();            
        item.setId(new Random().nextLong() + 100);
        item.setName(ITEM_NAME);
        item.setImagePath(ITEM_IMAGE_PATH);
        item.setUnitCost(ITEM_PRICE);
        item.setProduct(lsPros.get(0));
        return item;
    }
    
}