package uk.ac.cam.cl.dtg.teaching;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface ControllerApi {

	@GET
	@Path("/load/{type}")
	public Fruit getFruit(@PathParam("type") String type);
	 
}
