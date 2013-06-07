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
package org.n52.car.io.gson;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.n52.car.io.Access;
import org.n52.car.io.AccessException;
import org.n52.car.io.Connection;
import org.n52.car.io.ConnectionException;
import org.n52.car.io.types.Group;
import org.n52.car.io.types.Measurement;
import org.n52.car.io.types.Phenomenon;
import org.n52.car.io.types.Sensor;
import org.n52.car.io.types.Statistic;
import org.n52.car.io.types.Track;
import org.n52.car.io.types.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

public class GSONAccess implements Access {
	
	private static final Logger logger = LoggerFactory.getLogger(GSONAccess.class);

	private Connection connection;
	private Gson gson;

	@Override
	public void initialize(Connection conn) {
		this.connection = conn;
		this.gson = initGson();
	}

	private Gson initGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Users.class, new Users.Adapter());
		builder.registerTypeAdapter(Tracks.class, new Tracks.Adapter());
		return builder.create();
	}

	@Override
	public List<User> getUsers() throws AccessException {
		Reader reader = null;
		try {
			reader = this.connection.getUsersAsStream();
			return gson.fromJson(reader, Users.class);
		} catch (JsonParseException e) {
			throw new AccessException(e);
		} catch (ConnectionException e) {
			throw new AccessException(e);
		} finally {
			closeReader(reader);
		}
	}

	@Override
	public List<Group> getGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Track> getTracks() throws AccessException {
		Reader reader = null;
		try {
			reader = this.connection.getTracksAsStream();
			return gson.fromJson(reader, Tracks.class);
		} catch (JsonParseException e) {
			throw new AccessException(e);
		} catch (ConnectionException e) {
			throw new AccessException(e);
		} finally {
			closeReader(reader);
		}
	}

	private void closeReader(Reader reader) {
		try {
			if (reader != null)
				reader.close();
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}		
	}

	@Override
	public List<Sensor> getSensors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Phenomenon> getPhenomena() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Measurement> getMeasurements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Statistic> getStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

}
