package com.store.catalog.model.supervision;

import com.store.catalog.model.AbstractBean;

public class ESVersionDTO  implements AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4876978074676480284L;
	
	
	private String index;
	private String stateName;
	private int numberOfReplicas;
	private int numberOfShards;
	private long version;
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public int getNumberOfReplicas() {
		return numberOfReplicas;
	}
	public void setNumberOfReplicas(int numberOfReplicas) {
		this.numberOfReplicas = numberOfReplicas;
	}
	public int getNumberOfShards() {
		return numberOfShards;
	}
	public void setNumberOfShards(int numberOfShards) {
		this.numberOfShards = numberOfShards;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
	
}
