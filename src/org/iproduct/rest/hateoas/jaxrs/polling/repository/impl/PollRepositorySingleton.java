package org.iproduct.rest.hateoas.jaxrs.polling.repository.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class PollRepositorySingleton extends AbstarctRepository<Long, Poll>{
	private static long sequence = 0; 
	private static PollRepositorySingleton repositoryInstance = new PollRepositorySingleton();
	
	public static PollRepositorySingleton getInstance() {
		return repositoryInstance;
	}
	
	public PollRepositorySingleton() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Poll Repository Constructed>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	@Override
	synchronized
	protected Long getNextId() {
		return ++sequence;
	}

	@Override
	protected Poll makeItemWithModifiedId(Long newId, Poll oldItem) {
		Poll newItem = new Poll(newId, oldItem.getTitle(), oldItem.getQuestion());
		return newItem;
	}

}
