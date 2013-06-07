package org.n52.car.io;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Preferences {
	
	private static Preferences instance;
	private Configuration config;
	private static final String defaultFile = "/car.io-java-config.xml";
	private static final Logger logger = LoggerFactory.getLogger(Preferences.class);
	
	public enum Strings {
		CAR_IO_SERVER
	}
	
	public enum Booleans {
		VALIDATE_JSON
	}
	
	public static synchronized Preferences getInstance() {
		if (instance == null)
			instance = new Preferences();
		
		return instance;
	}
	
	private Preferences() {
		XMLConfiguration xml = new XMLConfiguration();
	
		try {
			xml.load(getClass().getResource(defaultFile));
		} catch (ConfigurationException e) {
			logger.warn(e.getMessage(), e);
		}
		
		this.config = xml;
	}
	
	public String getProperty(String key) {
		return this.config.getString(key);
	}
	
	public int getInteger(String key) {
		return this.config.getInt(key);
	}
	
	public boolean getBoolean(String key) {
		return this.config.getBoolean(key);
	}
	
	public double getDouble(String key) {
		return this.config.getDouble(key);
	}

}
