package org.n52.car.io;

public class ConnectionException extends Exception {

	public ConnectionException(Throwable e) {
		super(e);
	}

	public ConnectionException(String format) {
		super(format);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
