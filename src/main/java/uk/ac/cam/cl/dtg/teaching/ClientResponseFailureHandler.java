package uk.ac.cam.cl.dtg.teaching;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.client.ClientResponseFailure;

@Provider
public class ClientResponseFailureHandler implements ExceptionMapper<ClientResponseFailure> {

	@Override
	public Response toResponse(ClientResponseFailure t) {
		@SuppressWarnings("unchecked")
		Throwable message = (Throwable)t.getResponse().getEntity(SerializableException.class);
		message.printStackTrace();
		return Response.serverError().entity("API fail: "+message)
				.type(MediaType.APPLICATION_JSON).build();
	}

}
