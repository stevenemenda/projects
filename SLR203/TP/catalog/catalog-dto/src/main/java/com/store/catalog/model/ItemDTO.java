package com.store.catalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 * This class follows the Data Transfert Object design pattern and for that implements the
 * markup interface DataTransfertObject. It is a client view of an ItemDTO. This class only
 * transfers data from a distant service to a client.
 */
public class ItemDTO implements AbstractBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3683094094531524261L;
	// ======================================
    // =             Attributes             =
    // ======================================
    private Long id;
    private String name;
    private double unitCost;
    private String imagePath;

    @JsonBackReference
    @XmlInverseReference(mappedBy="item")
    private ProductDTO product;

    // ======================================
    // =            Constructors            =
    // ======================================
    public ItemDTO() {
    }

    public ItemDTO(final Long id, final String name, final double unitCost) {
        setId(id);
        setName(name);
        setUnitCost(unitCost);
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
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

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(final double unitCost) {
    	this.unitCost = unitCost;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(final String imagePath) {
    	this.imagePath = imagePath;
    }


    public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
    }
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	
	public int hashCode(Object obj) {
		return new HashCodeBuilder().append(id) .append(name).append(imagePath).hashCode();
	}
}
