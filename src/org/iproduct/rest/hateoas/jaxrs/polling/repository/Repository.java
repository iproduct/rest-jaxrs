package org.iproduct.rest.hateoas.jaxrs.polling.repository;

import java.util.Collection;
import java.util.ConcurrentModificationException;

import org.iproduct.rest.hateoas.jaxrs.polling.common.EntityDoesNotExistException;
import org.iproduct.rest.hateoas.jaxrs.polling.model.Identifiable;

/**
 * Defines {@link org.iproduct.rest.hateoas.jaxrs.iteming.model.item item} repository providing command and query operations
 * 
 * @author Trayan Iliev
 * @author IPT [http://iproduct.org]
 * 
 */
public interface Repository<K, V extends Identifiable<K>> {
	
	/**
	 * Get a item by its identifier or null if such item does not exist
	 * @param id item identifier
	 * @return item instance
	 */
	public V getItemById(K id);
	
	/**
	 * Get all items
	 * @return list of all items
	 */
	public Collection<V> getAllItems();
	
	/**
	 * Add new item to repository updating its id by auto-increment sequence
	 * @param item the new item to add 
	 * @return a new item instance with updated id
	 */
	public V addItem(V item);
	
	/**
	 * Update existing item in the repository. If the item does not exist 
	 * {@link org.iproduct.rest.hateoas.jaxrs.iteming.common.EntityDoesNotExistException EntityDoesNotExistException}
	 * is thrown. If item has been concurently modified
	 * @param item the new item to add
	 * @return new instance representing the updated item state
	 */
	public V updateItem(V item, String eTag) throws EntityDoesNotExistException, ConcurrentModificationException;
	
	/**
	 * Delete existing item in the repository
	 * @param item the new item to add
	 * @return new instance representing the updated item state
	 */
	public V deleteItem(K itemId) throws EntityDoesNotExistException;
	
}
