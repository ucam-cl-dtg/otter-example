package uk.ac.cam.cl.dtg.teaching.exceptions;

class SerializableStackTraceElement {
	private String className;
	private String methodName;
	private String fileName;
	private int lineNumber;

	public SerializableStackTraceElement(StackTraceElement e) {
		className = e.getClassName();
		methodName = e.getMethodName();
		fileName = e.getFileName();
		lineNumber = e.getLineNumber();
	}

	public SerializableStackTraceElement() {
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public boolean isNativeMethod() {
		return lineNumber == -2;
	}

	public String toString() {
		StringBuilder b = new StringBuilder(className + "." + methodName);
		if (isNativeMethod())
			b.append("(Native Method");
		else if (fileName != null && lineNumber >= 0)
			b.append("(" + fileName + ":" + lineNumber + ")");
		else
			b.append("(Unknown Source)");
		return b.toString();
	}
}