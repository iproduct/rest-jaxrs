/**
 * 
 */
package org.iproduct.rest.hateoas.jaxrs.polling.common;

/**
 * Exception thrown when trying to update or delete non-existing {@link org.iproduct.rest.hateoas.jaxrs.polling.repository.PollRepository Poll}entity
 * 
 * @author Trayan Iliev
 * @author IPT [http://iproduct.org]
 * 
 */
public class EntityDoesNotExistException extends Exception {
	private static final long serialVersionUID = 1L;
	private Object entity;
	
	/**
	 * 
	 */
	public EntityDoesNotExistException(Object entity) {
		this.entity = entity;
	}

	/**
	 * @param message
	 */
	public EntityDoesNotExistException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}

	/**
	 * @param cause
	 */
	public EntityDoesNotExistException(Object entity, Throwable cause) {
		super(cause);
		this.entity = entity;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EntityDoesNotExistException(Object entity, String message, Throwable cause) {
		super(message, cause);
		this.entity = entity;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EntityDoesNotExistException(Object entity, String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.entity = entity;
	}
	
	@Override
	public String getMessage() {
		return "Entity " + entity + " does not exist.";
	}

}
