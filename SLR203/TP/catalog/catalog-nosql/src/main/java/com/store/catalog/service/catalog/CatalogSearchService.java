package com.store.catalog.service.catalog;

import com.store.catalog.common.exception.CheckException;
import com.store.catalog.model.SearchableItem;

import java.util.List;
/**
 * This interface gives a remote view of the CatalogServiceBean. Any distant manualyLoadedClient that wants to call
 * a method on the CatalogServiceBean has to use this interface. Because it extends the EJBObject interface
 * (which extends Remote), every method must throw RemoteException.
 */
public interface CatalogSearchService {

	

    /**
     * This method return all the itemDTOs that match a given keyword. It uses the ItemDTO domain object
     * to get the data. It then transforms this collection of ItemDTO object into a
     * collection of ItemDTO and returns it.
     * 
     * @param keyword
     * @return
     * @throws CheckException
     */
	public List<SearchableItem> searchItems(String keyword) throws Exception;


    /**
     *
     * @param keyword
     * @param from
     * @param page
     * @return
     * @throws Exception
     */
    public List<SearchableItem> matchPhrasePrefixeItems(String keyword, int from, int page) throws Exception;

}
