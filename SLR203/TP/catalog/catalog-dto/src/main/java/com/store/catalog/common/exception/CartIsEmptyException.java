package com.store.catalog.common.exception;

/**
 * Created with IntelliJ IDEA.
 * User: alexandre_godet
 * Date: 24/02/2014
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class CartIsEmptyException extends CheckException{
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7186210644472260131L;

	public CartIsEmptyException(String message) {
        super(message);
    }
}
