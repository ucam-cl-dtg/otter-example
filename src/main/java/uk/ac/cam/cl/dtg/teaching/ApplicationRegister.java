package uk.ac.cam.cl.dtg.teaching;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import uk.ac.cam.cl.dtg.teaching.controllers.ExampleController;
import uk.ac.cam.cl.dtg.teaching.exceptions.ClientResponseFailureHandler;
import uk.ac.cam.cl.dtg.teaching.exceptions.ExceptionHandler;

/**
 * This class registers the resteasy handlers. The name is important since it is
 * used as a String in HttpServletDispatcherV3
 * 
 * @author acr31
 * 
 */
public class ApplicationRegister extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> result = new HashSet<Class<?>>();
		result.add(ExampleController.class);
		result.add(ClientResponseFailureHandler.class);
		result.add(ExceptionHandler.class);
		return result;
	}

}
