package com.store.catalog.service.catalog;

import com.store.catalog.model.SearchableItem;

/**
 * This interface gives a remote view of the CatalogServiceBean. Any distant manualyLoadedClient that wants to call
 * a method on the CatalogServiceBean has to use this interface. Because it extends the EJBObject interface
 * (which extends Remote), every method must throw RemoteException.
 */
public interface CatalogAdminService {

	

	public String createItem(SearchableItem searchableItem) throws Exception;


    public SearchableItem updateItem(SearchableItem searchableItem) throws Exception;


    public SearchableItem getItem(String id) throws Exception ;


    public void deleteItem(String id) throws Exception ;


}
