package uk.ac.cam.cl.dtg.teaching;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

public class SerializableException extends Exception {

	private static final long serialVersionUID = 759370006263359407L;

	private String wrappedClassName;
	private String message;
	private SerializableException cause;
	private SerializableStackTraceElement[] wrappedStackTrace;

	public SerializableException() {}
	
	public SerializableException(Throwable toSerialize) {
		this.message = toSerialize.getMessage();
		this.wrappedClassName = toSerialize.getClass().getName();
		if (toSerialize.getCause() != null) {
			this.cause = new SerializableException(toSerialize.getCause());
		}
		setStackTrace(toSerialize.getStackTrace());
	}

	
	
	public String getWrappedClassName() {
		return wrappedClassName;
	}

	public void setWrappedClassName(String wrappedClassName) {
		this.wrappedClassName = wrappedClassName;
	}

	public SerializableStackTraceElement[] getWrappedStackTrace() {
		return wrappedStackTrace;
	}

	public void setWrappedStackTrace(
			SerializableStackTraceElement[] wrappedStackTrace) {
		this.wrappedStackTrace = wrappedStackTrace;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCause(SerializableException cause) {
		this.cause = cause;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getLocalizedMessage() {
		return this.message;
	}

	@Override
	public synchronized Throwable getCause() {
		return this.cause;
	}

	@Override
	public synchronized Throwable initCause(Throwable cause) {
		this.cause = new SerializableException(cause);
		return this;
	}

	@Override
	public String toString() {
		String message = getLocalizedMessage();
		return this.wrappedClassName + (message == null ? "" : ": " + message);
	}

	@Override
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	@Override
	public void printStackTrace(PrintStream s) {
		printStackTrace(new PrintWriter(new OutputStreamWriter(s)));
	}

	@Override
	public void printStackTrace(PrintWriter writer) {
		synchronized (writer) {
			writer.println(toString());
			for (SerializableStackTraceElement s : wrappedStackTrace) {
				writer.println("\tat " + s);
			}
			writer.flush();
		}
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		StackTraceElement[] result = new StackTraceElement[wrappedStackTrace.length];
		for (int i = 0; i < result.length; ++i) {
			result[i] = new StackTraceElement(
					wrappedStackTrace[i].getClassName(),
					wrappedStackTrace[i].getMethodName(),
					wrappedStackTrace[i].getFileName(),
					wrappedStackTrace[i].getLineNumber());
		}
		return result;
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		wrappedStackTrace = new SerializableStackTraceElement[stackTrace.length];
		for (int i = 0; i < stackTrace.length; ++i) {
			wrappedStackTrace[i] = new SerializableStackTraceElement(
					stackTrace[i]);
		}
	}
}
