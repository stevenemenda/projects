package com.store.catalog.model.supervision;



import com.store.catalog.model.AbstractBean;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VersionDTO implements AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7366527470740889038L;

	
	private Long id;
    private String name;


    // ======================================
    // =            Constructors            =
    // ======================================
    public VersionDTO() {
    }

    public VersionDTO(final Long id, final String name) {
        this.id = id;
        this.name = name;
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


	public String toString() {
		return ToStringBuilder.reflectionToString(this);
    }
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public int hashCode(Object obj) {
		return new HashCodeBuilder().append(id) .append(name).hashCode();
	}	
	
	
}
