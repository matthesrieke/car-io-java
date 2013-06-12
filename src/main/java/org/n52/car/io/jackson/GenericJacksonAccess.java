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
package org.n52.car.io.jackson;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.n52.car.io.Access;
import org.n52.car.io.AccessException;
import org.n52.car.io.Connection;
import org.n52.car.io.jackson.transform.MapToObject;
import org.n52.car.io.jackson.transform.MapToTrack;
import org.n52.car.io.jackson.transform.MapToUser;
import org.n52.car.io.types.Group;
import org.n52.car.io.types.Measurement;
import org.n52.car.io.types.Phenomenon;
import org.n52.car.io.types.Sensor;
import org.n52.car.io.types.Statistic;
import org.n52.car.io.types.Track;
import org.n52.car.io.types.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericJacksonAccess implements Access {

	private Connection connection;
	private MapToObject<Track> tracks = new MapToTrack();
	private MapToObject<User> users = new MapToUser();

	@Override
	public void initialize(Connection conn) {
		this.connection = conn;
	}

	@Override
	public List<User> getUsers() throws AccessException {
		try {
			Map<?, ?> node = createNode(this.connection.getUsersAsStream());
			return this.users.fromMap(node, "users");
		} catch (JsonProcessingException e) {
			throw new AccessException(e);
		} catch (IOException e) {
			throw new AccessException(e);
		}
	}

	@Override
	public List<Group> getGroups() throws AccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Track> getTracks() throws AccessException {
		try {
			Map<?, ?> node = createNode(this.connection.getTracksAsStream());
			return this.tracks.fromMap(node, "tracks");
		} catch (JsonProcessingException e) {
			throw new AccessException(e);
		} catch (IOException e) {
			throw new AccessException(e);
		}
	}

	@Override
	public List<Sensor> getSensors() throws AccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Phenomenon> getPhenomena() throws AccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Measurement> getMeasurements() throws AccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Statistic> getStatistics() throws AccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Map<?, ?> createNode(Reader json) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, Map.class);
	}

	@Override
	public Map<?, ?> getResource(String href) throws AccessException {
		try {
			Reader reader = this.connection.getResource(href);
			return createNode(reader);
		} catch (JsonProcessingException e) {
			throw new AccessException(e);
		} catch (IOException e) {
			throw new AccessException(e);
		}
	}

}
