package uk.ac.cam.cl.dtg.teaching;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.ClientRequestFactory;

@Path("/example")
@Produces("application/json")
public class ExampleController {

	@GET
	@Path("/load/{type}")
	public Fruit load(@PathParam("type") String type) throws Exception {
		if (type.equals("chocolate")) {
			throw new Exception("Not a fruit");
		}
		return new Fruit(type);
	}

	@GET
	@Path("/loadList")
	public List<Fruit> loadList() {
		return Arrays.asList(new Fruit[] { new Fruit("apple"), new Fruit("orange"), new Fruit("pear") } );
	}

	@GET
	@Path("/exception")
	public Map<String, ?> exception() throws Exception {
		throw new Exception("Test");
	}

	@GET
	@Path("/proxy/{type}")
	public Fruit proxy(@PathParam("type") String type) throws Exception {
		ClientRequestFactory c = new ClientRequestFactory(UriBuilder.fromUri(
				"http://localhost:8080/api-template/api/example/")
				.build());
		Fruit apple = c.createProxy(ControllerApi.class).getFruit(type);
		return apple;
	}

}
