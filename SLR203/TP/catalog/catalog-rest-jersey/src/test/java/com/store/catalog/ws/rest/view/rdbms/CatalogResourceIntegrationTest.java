package com.store.catalog.ws.rest.view.rdbms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.store.catalog.model.CategoryDTO;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;


@Ignore
public class CatalogResourceIntegrationTest {

    private ObjectMapper mapper = new ObjectMapper();
    static private WebTarget target;

    @BeforeClass
    static public void setup() {
        Client client = ClientBuilder.newClient();
//        target = client.target("http://localhost:8080/catalog-rest-jersey/catalog");
        target = client.target("http://localhost:8080/catalog/catalog");
    }

    /*
    curl -i http://localhost:8080/catalog/catalog/category -H "Content-Type: application/json" -X POST -d '
{"id":20,"name":"Dinosorus","description":"prehistoric animals",
	"products":null}'


curl -i http://localhost:8080/catalog/catalog/category/20 -H "Content-Type: application/json" -X GET

curl -i http://localhost:8080/catalog/catalog/category -H "Content-Type: application/json" -X PUT -d '{"id":20,"name":"Dinosorus","description":"prehistoric animals updated",
	"products":null}'


curl -i http://localhost:8080/catalog/catalog/category/20 -H "Content-Type: application/json" -X DELETE


     */

    @Test
    public void request_on_categories_should_success() throws IOException {
        String res = target.path("categories").request().accept(MediaType.APPLICATION_JSON).get(String.class);
        List<CategoryDTO> categories = mapper.readValue(res, TypeFactory.defaultInstance().constructCollectionType(List.class, CategoryDTO.class));
        ;

        assertThat(categories.size(), equalTo(1));

        assertThat(categories.get(0).getId(), equalTo(6L));
        assertThat(categories.get(0).getProducts().size(), equalTo(4));
    }

    @Test
    public void request_on_category_should_success() throws IOException {

        CategoryDTO category = target.path("category/6").request().accept(MediaType.APPLICATION_JSON_TYPE).get(CategoryDTO.class);

        assertThat(category, is(notNullValue()));
    }

    @Test
    public void create_on_category_should_success() throws IOException {

        CategoryDTO category = createCategory();

        target.path("category").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(category, MediaType.APPLICATION_JSON), CategoryDTO.class);

        CategoryDTO res = target.path("category/" + category.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(CategoryDTO.class);

        assertThat(res, is(notNullValue()));
        assertEquals(res.getDescription(), category.getDescription());
        assertEquals(res.getName(), category.getName());

    }

    /* ---------------------------------- */
    /* --------- Private methods -------- */
	/* ---------------------------------- */


    @Test
    public void update_on_category_should_success() throws IOException {

        CategoryDTO category = createCategory();
        target.path("category").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(category, MediaType.APPLICATION_JSON), CategoryDTO.class);
        CategoryDTO res = target.path("category/" + category.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(CategoryDTO.class);

        res.setDescription("cool");

        target.path("category").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(res, MediaType.APPLICATION_JSON));

        res = target.path("category/" + category.getId()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(CategoryDTO.class);

        assertThat(res.getDescription(), equalTo("cool"));
    }


    private CategoryDTO createCategory() {
        CategoryDTO category = new CategoryDTO();
        category.setId(new Random().nextLong());
        category.setName("name");
        category.setDescription("description");
        return category;
    }
}