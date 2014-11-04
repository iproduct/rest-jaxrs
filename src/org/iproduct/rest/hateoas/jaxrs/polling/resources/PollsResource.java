/**
 * 
 */
package org.iproduct.rest.hateoas.jaxrs.polling.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.util.Collection;
import java.util.ConcurrentModificationException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.iproduct.rest.hateoas.jaxrs.polling.common.EntityDoesNotExistException;
import org.iproduct.rest.hateoas.jaxrs.polling.model.Poll;
import org.iproduct.rest.hateoas.jaxrs.polling.repository.Repository;
import org.iproduct.rest.hateoas.jaxrs.polling.repository.impl.PollRepositorySingleton;

/**
 * Resource class for {@link org.iproduct.rest.hateoas.jaxrs.polling.model.Poll Poll} resources
 * 
 * @author Trayan Iliev
 * @author IPT [http://iproduct.org]
 * 
 */
@Path("/polls")
public class PollsResource {
	
	private Repository<Long, Poll> pollRepository = PollRepositorySingleton.getInstance();
	
	@Context
	UriInfo uriInfo;
	
	@GET
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public Collection<Poll> getAllPolls(){
		System.out.println(pollRepository);
		return pollRepository.getAllItems();
	}
	
	@POST
	@Consumes({APPLICATION_XML, APPLICATION_JSON})
	public Response addPoll(Poll poll){
		poll = pollRepository.addItem(poll);
		Response response = Response.created(uriInfo.getAbsolutePath().resolve("/" + poll.getId())).build();
		return response;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes({APPLICATION_XML, APPLICATION_JSON})
	public Response updtePoll(@PathParam("id") Long id, Poll poll){
		Response response;
		if(id.equals(poll.getId())){
			try{
				poll = pollRepository.updateItem(poll, null);
				response = Response.noContent().build(); //More appropriate than 200OK
			} catch (ConcurrentModificationException e){
				response = Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
			} catch (EntityDoesNotExistException e){
				response = Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
			}
		} else {
			response = Response.status(Response.Status.BAD_REQUEST).entity("Resource identifier " + id 
					+ " can not be changed to " + poll.getId() +". Resource identifiers are immutable.").build();
		}
		return response;
	}
	
	@DELETE
	@Path("/{id}")
	public Response updtePoll(@PathParam("id") Long id){
		Poll deletedItem;
		Response response;
		try{
			deletedItem = pollRepository.deleteItem(id);
			response = Response.ok().entity(deletedItem).build(); 
		} catch (EntityDoesNotExistException e){
			response = Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
		return response;
	}
	
}
