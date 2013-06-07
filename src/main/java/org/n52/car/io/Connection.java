package org.n52.car.io;

import java.io.Reader;
import java.net.URL;

public interface Connection {

	public void initialize(URL host);
	
	public Reader getUsersAsStream() throws ConnectionException;

	public Reader getTracksAsStream() throws ConnectionException;

}
