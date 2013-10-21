package uk.ac.cam.cl.dtg.teaching;

import javax.ws.rs.WebApplicationException;

import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

public class CredentialsInterceptor implements PreProcessInterceptor {

	public CredentialsInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ServerResponse preProcess(HttpRequest arg0, ResourceMethod arg1)
			throws Failure, WebApplicationException {

		// TODO Auto-generated method stub
		return null;
	}

}
