package org.iproduct.rest.hateoas.jaxrs.polling.repository.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.iproduct.rest.hateoas.jaxrs.polling.common.EntityDoesNotExistException;
import org.iproduct.rest.hateoas.jaxrs.polling.model.Identifiable;
import org.iproduct.rest.hateoas.jaxrs.polling.repository.Repository;

/**
 * Implements {@link org.iproduct.rest.hateoas.jaxrs.polling.model.Poll Poll} repository defined by 
 * {@link org.iproduct.rest.hateoas.jaxrs.polling.repository.PollRepository PollRepository} interfce 
 * 
 * @author Trayan Iliev
 * @author IPT [http://iproduct.org]
 * 
 */
public abstract class AbstarctRepository<K extends Comparable<K>, V extends Identifiable<K>> implements Repository<K, V>{
	private Map<K,V> store = new ConcurrentHashMap<>();
	
	
	/**
	 * Get a poll by its identifier or null if such poll does not exist
	 * @param id poll identifier
	 * @return poll instance
	 */
	@Override
	public V getItemById(K id) {
		return store.get(id);
	}

	/**
	 * The method returns unmodifiable collection of all polls
	 * @return the unmodifiable collection of all polls
	 */
	@Override
	public Collection<V> getAllItems() {
//		return ImmutableList.copyOf(store.values()); //defensive copy
		return Collections.unmodifiableCollection(store.values());
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Collection<V> getRangeItems(K fromId, Integer maxSize) {
		return Collections.unmodifiableCollection(
			store.keySet().stream().sorted()
				.filter(k -> k.compareTo(fromId) >= 0)
				.limit(maxSize)
				.map(store::get)
				.collect(Collectors.toList()));
	}
	
	/**
	 * {@inheritDoc}
	 * The id of the poll is being assigned using auto increment sequence
	 * @return a new Poll instance with id assigned using auto-increment sequence
	 */
	@Override
	public V addItem(V item) {
		K nextId = getNextId();
		V newItem = makeItemWithModifiedId(nextId, item); //defensive copy with auto-increment id
		store.put(nextId, newItem);
		return newItem;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public V updateItem(V item, String eTag)
			throws EntityDoesNotExistException, ConcurrentModificationException {
		V newItem = store.computeIfPresent(item.getId(), 
				(key, oldVal) -> {
					String oldETag = Integer.toString(oldVal.hashCode());
					if(eTag != null && eTag != oldETag)
						throw new ConcurrentModificationException("Item " + item +" has been modified concurrently - server value is: "
								+ oldVal + ". ETags does not match for client: " 
								+ eTag + " & server: " + oldETag);
					return item;
				});
//		V oldItem = store.get(item.getId());
		if (newItem == null)
			throw new EntityDoesNotExistException("with id = " + item.getId());
//		String oldETag = Integer.toString(oldItem.hashCode());
//		if(eTag != oldETag)
//			throw new ConcurrentModificationException("Item " + item +" has been modified concurrently - ETags does not match: " 
//					+ eTag + " & " + oldETag);
//		store.put(item.getId(), value)
		return newItem;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public V deleteItem(K itemId) throws EntityDoesNotExistException {
		V oldItem = store.get(itemId);
		if (oldItem == null)
			throw new EntityDoesNotExistException("with itemId = " + itemId);	
		return store.remove(itemId);
	}
	
	

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public String getItemsCount() {
		return Integer.toString(store.size());
	}

	/**
	 * This abstract method provides auto-increment sequence used by the repository 
	 * to guarantee the uniqueness of assigned item identifiers. It should be appropriately
	 * overridden by concrete descendants.
	 * @return the next unique identifier for an item
	 */
	protected abstract K getNextId();
	
	/**
	 * This abstract method should be overriden by descendants in order to
	 * provide copy-on-write factory method for (preferably immutable) items.
	 * It is used by {@ #updateItem(Identifiable, String)} method in order to
	 * return new instance with auto-assigned item identifier.
	 * @param newId the identifier of the new instance (preferably immutable) to be created 
	 * @param oldItem the old instance state data to be copied from
	 * @return the new instance containing the same data as {@code oldItem} but with 
	 * newly assigned identifier
	 */
	protected abstract V makeItemWithModifiedId(K newId, V oldItem);

}
