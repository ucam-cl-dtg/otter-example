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
		t.printStackTrace();
		Throwable message = appendStackTrace(readException(t), t);
		return Response.serverError().entity(message)
				.type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Append the local stack trace onto the remote one. This then captures the
	 * sequence of operations that happened before we caused the error on the
	 * remote machine. This is different to using setCause because the remote
	 * exception could well already have cause information which arose at the
	 * remote site. There is no message to add from the local exception because
	 * this handler only deals with ClientResponseFailures.
	 * 
	 * @param remoteException
	 * @param localException
	 * @return
	 */
	public static SerializableException appendStackTrace(
			SerializableException remoteException,
			ClientResponseFailure localException) {

		SerializableStackTraceElement[] remoteStack = remoteException
				.getSerializableStackTrace();
		StackTraceElement[] localStack = localException.getStackTrace();
		SerializableStackTraceElement[] newStack = new SerializableStackTraceElement[remoteStack.length
				+ localStack.length];
		int ptr = 0;
		for (SerializableStackTraceElement s : remoteStack) {
			newStack[ptr++] = s;
		}
		for (StackTraceElement s : localStack) {
			newStack[ptr++] = new SerializableStackTraceElement(s);
		}
		remoteException.setSerializableStackTrace(newStack);
		return remoteException;
	}

	/**
	 * If a remote error occurs then the remote end should send a
	 * SerializableException out over json. This method tries to recover that
	 * object. If the content type isn't application/json we assume something
	 * went very wrong at the other end and just package up the response in a
	 * new Exception
	 * 
	 * @param e the ClientResponseFailure which we've caught
	 * @return a SerializableException collected from the remote server
	 */
	public static SerializableException readException(ClientResponseFailure e) {
		ClientResponse<?> clientResponse = e.getResponse();
		String contentType = clientResponse.getHeaders().getFirst(
				"Content-Type");
		if (contentType.startsWith("application/json")) {
			// if they've sent us json then we'll assume they stuck with the API
			// contract and have sent an ApiFailureMessage

			SerializableException message = (SerializableException) clientResponse
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
