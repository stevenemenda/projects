package com.store.catalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


public  class Product implements AbstractBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6699161208358545698L;

	// ======================================
    // =             Attributes             =
    // ======================================
    private Long id;


    private String name;


    private String description;


    private Category category;


    private Set<Item> items = new HashSet<Item>();
    
 

    // ======================================
    // =            Constructors            =
    // ======================================
    public Product() {
    }

    public Product(final Long id, final String name, final String description) {
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


    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

    public Set<Item> getItems() {
        return items;
    }   
    
	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id) .append(name).append(description).hashCode();
    }


}
