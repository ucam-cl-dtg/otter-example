package uk.ac.cam.cl.dtg.teaching;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This should be registered with resteasy in your application class. It will
 * capture exceptions and wrap them in an ApiFailureMessage instance and then
 * encode it appropriately (e.g. json)
 * 
 * @author acr31
 * 
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		return Response.serverError().entity(new SerializableException(exception))
				.type(MediaType.APPLICATION_JSON).build();
	}
}
