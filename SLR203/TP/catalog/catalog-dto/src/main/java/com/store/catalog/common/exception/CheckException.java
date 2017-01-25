package com.store.catalog.common.exception;

/**
 * This exception is thrown when some checking validation error is found.
 */
public class CheckException extends ApplicationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2780857914094021626L;

	
	public CheckException(final String message) {
        super(message);
    }
}
