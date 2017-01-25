package com.store.catalog.common.exception;

/**
 * Created with IntelliJ IDEA.
 * User: alexandre_godet
 * Date: 24/02/2014
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */
public class CartIsNullException extends CheckException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5147772511726583030L;

	public CartIsNullException(String message) {
        super(message);
    }
}
