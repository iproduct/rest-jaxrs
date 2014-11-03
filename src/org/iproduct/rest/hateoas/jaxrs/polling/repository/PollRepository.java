package org.iproduct.rest.hateoas.jaxrs.polling.repository;

import java.util.Collection;
import java.util.List;

import org.iproduct.rest.hateoas.jaxrs.polling.model.Poll;

/**
 * Defines {@link org.iproduct.rest.hateoas.jaxrs.polling.model.Poll Poll} repository providing separate command and query operations
 * 
 * @author Trayan Iliev
 * @author IPT [http://iproduct.org]
 * 
 */
public interface PollRepository {
	
	/**
	 * Get a poll by its identifier or null if such poll does not exist
	 * @param id poll identifier
	 * @return poll instance
	 */
	public Poll getPollById(Long id);
	
	/**
	 * Get all polls
	 * @return list of all polls
	 */
	public Collection<Poll> getAllPolls();
	
	/**
	 * Add new poll to repository
	 * @param poll the new poll to add 
	 * @return 
	 */
	public Poll addPoll(Poll poll);
	
}
