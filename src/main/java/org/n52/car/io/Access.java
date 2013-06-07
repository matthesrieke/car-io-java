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

import java.util.List;

import org.n52.car.io.types.Group;
import org.n52.car.io.types.Measurement;
import org.n52.car.io.types.Phenomenon;
import org.n52.car.io.types.Sensor;
import org.n52.car.io.types.Statistic;
import org.n52.car.io.types.Track;
import org.n52.car.io.types.User;

public interface Access {

	public void initialize(Connection conn);
	
	public List<User> getUsers() throws AccessException;
	
	public List<Group> getGroups() throws AccessException;
	
	public List<Track> getTracks() throws AccessException;
	
	public List<Sensor> getSensors() throws AccessException;
	
	public List<Phenomenon> getPhenomena() throws AccessException;
	
	public List<Measurement> getMeasurements() throws AccessException;
	
	public List<Statistic> getStatistics() throws AccessException;
	
}
