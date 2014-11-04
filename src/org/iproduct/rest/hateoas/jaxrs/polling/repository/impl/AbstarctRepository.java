package org.iproduct.rest.hateoas.jaxrs.polling.repository.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.iproduct.rest.hateoas.jaxrs.polling.common.EntityDoesNotExistException;
import org.iproduct.rest.hateoas.jaxrs.polling.model.Identifiable;
import org.iproduct.rest.hateoas.jaxrs.polling.model.Poll;
import org.iproduct.rest.hateoas.jaxrs.polling.repository.Repository;

/**
 * Implements {@link org.iproduct.rest.hateoas.jaxrs.polling.model.Poll Poll} repository defined by 
 * {@link org.iproduct.rest.hateoas.jaxrs.polling.repository.PollRepository PollRepository} interfce 
 * 
 * @author Trayan Iliev
 * @author IPT [http://iproduct.org]
 * 
 */
public abstract class AbstarctRepository<K, V extends Identifiable<K>> implements Repository<K, V>{
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
	
	
	/* (non-Javadoc)
	 * @see org.iproduct.rest.hateoas.jaxrs.polling.repository.Repository#updateItem(org.iproduct.rest.hateoas.jaxrs.polling.model.Identifiable, java.lang.String)
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
			throw new EntityDoesNotExistException(item);
//		String oldETag = Integer.toString(oldItem.hashCode());
//		if(eTag != oldETag)
//			throw new ConcurrentModificationException("Item " + item +" has been modified concurrently - ETags does not match: " 
//					+ eTag + " & " + oldETag);
//		store.put(item.getId(), value)
		return newItem;
	}

	/* (non-Javadoc)
	 * @see org.iproduct.rest.hateoas.jaxrs.polling.repository.Repository#deleteItem(org.iproduct.rest.hateoas.jaxrs.polling.model.Identifiable)
	 */
	@Override
	public V deleteItem(K itemId) throws EntityDoesNotExistException {
		V oldItem = store.get(itemId);
		if (oldItem == null)
			throw new EntityDoesNotExistException("with itemId = " + itemId);	
		return store.remove(itemId);
	}

	protected abstract K getNextId();
	protected abstract V makeItemWithModifiedId(K newId, V oldItem);

}
