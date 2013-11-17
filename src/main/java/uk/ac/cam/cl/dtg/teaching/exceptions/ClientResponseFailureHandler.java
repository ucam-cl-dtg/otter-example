package uk.ac.cam.cl.dtg.teaching.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ClientResponseFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ClientResponseFailureHandler implements
		ExceptionMapper<ClientResponseFailure> {

	private static Logger log = LoggerFactory
			.getLogger(ClientResponseFailure.class);

	@Override
	public Response toResponse(ClientResponseFailure t) {
		Throwable message = readException(t);
		message.printStackTrace();
		return Response.serverError().entity("API fail: " + message)
				.type(MediaType.APPLICATION_JSON).build();
	}

	public static Throwable readException(ClientResponseFailure e) {
		ClientResponse<?> clientResponse = e.getResponse();
		String contentType = clientResponse.getHeaders().getFirst(
				"Content-Type");
		if (contentType.startsWith("application/json")) {
			// if they've sent us json then we'll assume they stuck with the API
			// contract and have sent an ApiFailureMessage

			Throwable message = (Throwable) clientResponse
					.getEntity(SerializableException.class);
			return message;
		}

		@SuppressWarnings("unchecked")
		String message = (String) e.getResponse().getEntity(String.class);
		if (contentType.startsWith("text/html")) {
			// we've got back some html from the server - something has gone
			// wrong since it should be giving us JSON
			// the best we can do is load it as a string, strip out everything
			// except the body of the document and rethrow that

			int openBodyTag = message.indexOf("<body>");
			int closeBodyTag = message.indexOf("</body>");
			if (openBodyTag != -1 && closeBodyTag != -1) {
				message = message.substring(openBodyTag + 6, closeBodyTag);
			}
		} else {
			log.error("Unexpected Content-Type {} in error message",
					contentType);
		}

		return new SerializableException(new Exception(message));
	}

}
