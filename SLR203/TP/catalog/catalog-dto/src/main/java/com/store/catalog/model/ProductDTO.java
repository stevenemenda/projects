package com.store.catalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import java.util.HashSet;
import java.util.Set;

/**
 * This class follows the Data Transfert Object design pattern and for that implements the
 * markup interface DataTransfertObject. It is a client view of a ProductDTO. This class only
 * transfers data from a distant service to a client.
 */
public final class ProductDTO implements AbstractBean {

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


    @JsonBackReference
    @XmlInverseReference(mappedBy="product")
    private CategoryDTO category;

    @JsonManagedReference
    private Set<ItemDTO> items ;
    
 

    // ======================================
    // =            Constructors            =
    // ======================================
    public ProductDTO() {
    }

    public ProductDTO(final Long id, final String name, final String description) {
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


    public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}


    public Set<ItemDTO> getItems() {
        if (items == null) {
            items = new HashSet<ItemDTO>(0);
        }
        return items;

    }   
    
	public void setItems(Set<ItemDTO> items) {
		this.items = items;
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
