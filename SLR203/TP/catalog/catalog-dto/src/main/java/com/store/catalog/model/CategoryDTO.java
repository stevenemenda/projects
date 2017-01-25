package com.store.catalog.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * This class follows the Data Transfert Object design pattern and for that implements the
 * markup interface DataTransfertObject. It is a client view of a CategoryDTO. This class only
 * transfers data from a distant service to a client.
 */

public  class CategoryDTO implements AbstractBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4648729478877163459L;
	// ======================================
    // =             Attributes             =
    // ======================================

	private Long id;
    private String name;
    private String description;

    //@JsonManagedReference
    private Set<ProductDTO> products ;

    // ======================================
    // =            Constructors            =
    // ======================================
    public CategoryDTO() {
    }

    public CategoryDTO(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public CategoryDTO(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
    	this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
    	this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
    	this.name = name;
    }


    public Set<ProductDTO> getProducts() {
        if (products == null) {
            products = new HashSet<ProductDTO>(0);
        }
        return products;
    }
    
  
    public void setProducts(Set<ProductDTO> products) {
		this.products = products;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
    }
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public int hashCode(Object obj) {
		return new HashCodeBuilder().append(id) .append(name).append(description).hashCode();
	}	
	
}
