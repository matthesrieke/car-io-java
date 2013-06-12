/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.car.io;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ServiceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Preferences {
	
	private static Preferences instance;
	private Configuration config;
	private URL server;
	private Access access;
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
		
		initCommonParameters();
	}
	
	private void initCommonParameters() {
		try {
			this.server = new URL(getProperty(Strings.CAR_IO_SERVER.toString()));
			this.access = instantiateAccess();
		} catch (MalformedURLException e) {
			logger.warn(e.getMessage(), e);
		}
	}

	private Access instantiateAccess() {
		ServiceLoader<Access> loader = ServiceLoader.load(Access.class);
		for (Access access : loader) {
			access.initialize(instantiateConnection());
			return access;
		}
		return null;
	}

	private Connection instantiateConnection() {
		ServiceLoader<Connection> loader = ServiceLoader.load(Connection.class);
		for (Connection connection : loader) {
			connection.initialize(getServer());
			return connection;
		}
		return null;
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

	public URL getServer() {
		return this.server;
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access ac) {
		this.access = ac;
	}

}
