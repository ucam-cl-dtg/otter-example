package uk.ac.cam.cl.dtg.teaching.api;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import uk.ac.cam.cl.dtg.teaching.models.Fruit;

@Path("/example")
@Produces("application/json")
public interface ExampleApi {

	@GET
	@Path("/load/{type}")
	public abstract Fruit load(@PathParam("type") String type) throws Exception;

	@GET
	@Path("/loadList")
	public abstract List<Fruit> loadList();

	@GET
	@Path("/exception")
	public abstract Map<String, ?> exception() throws Exception;

	@GET
	@Path("/proxy/{type}")
	public abstract Fruit proxy(@PathParam("type") String type) throws Exception;

}