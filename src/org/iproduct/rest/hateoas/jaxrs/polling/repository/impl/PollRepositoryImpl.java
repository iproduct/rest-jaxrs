package org.iproduct.rest.hateoas.jaxrs.polling.repository.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.ManagedBean;
import javax.inject.Singleton;

import jersey.repackaged.com.google.common.collect.ImmutableList;

import org.iproduct.rest.hateoas.jaxrs.polling.cdi.Repository;
import org.iproduct.rest.hateoas.jaxrs.polling.model.Poll;
import org.iproduct.rest.hateoas.jaxrs.polling.repository.PollRepository;

/**
 * Implements {@link org.iproduct.rest.hateoas.jaxrs.polling.model.Poll Poll} repository defined by 
 * {@link org.iproduct.rest.hateoas.jaxrs.polling.repository.PollRepository PollRepository} interfce 
 * providing separate command and query operations
 * 
 * @author Trayan Iliev
 * @author IPT [http://iproduct.org]
 * 
 */
public class PollRepositoryImpl implements PollRepository{
	private static long sequence = 0; 
	private static PollRepositoryImpl repositoryInstance = new PollRepositoryImpl();
	private Map<Long, Poll> store = new ConcurrentHashMap<>();
	
	public static PollRepository getInstance() {
		return repositoryInstance;
	}
	
	public PollRepositoryImpl(){
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Bean Constructed>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	/**
	 * Get a poll by its identifier or null if such poll does not exist
	 * @param id poll identifier
	 * @return poll instance
	 */
	@Override
	public Poll getPollById(Long id) {
		return store.get(id);
	}

	/**
	 * The method returns unmodifiable collection of all polls
	 * @return the unmodifiable collection of all polls
	 */
	@Override
	public Collection<Poll> getAllPolls() {
//		return ImmutableList.copyOf(store.values()); //defensive copy
		return Collections.unmodifiableCollection(store.values());
	}

	/**
	 * {@inheritDoc}
	 * The id of the poll is being assigned using auto increment sequence
	 * @return a new Poll instance with id assigned using auto-increment sequence
	 */
	@Override
	public Poll addPoll(Poll poll) {
		long pollId = getAutoIncrementId();
		Poll newPoll = new Poll(pollId, poll.getTitle(), poll.getQuestion()); //defensive copy with auto-increment id
		store.put(pollId, newPoll);
		return newPoll;
	}
	
	synchronized protected long getAutoIncrementId(){
		return ++sequence;
	}
}
