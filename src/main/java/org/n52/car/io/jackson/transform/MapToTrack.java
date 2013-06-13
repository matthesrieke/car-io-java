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
package org.n52.car.io.jackson.transform;

import java.util.Map;

import org.joda.time.DateTime;
import org.n52.car.io.jackson.lazy.HyperReferableInstantiation;
import org.n52.car.io.jackson.lazy.LazyTrack;
import org.n52.car.io.jackson.types.TrackImpl;
import org.n52.car.io.types.Track;

public class MapToTrack extends MapToObject<Track> implements HyperReferableInstantiation<Track> {

	@Override
	public Track createObjectFromMap(Map<?, ?> map) {
		TrackImpl result = new TrackImpl();
		readProperties((Map<?, ?>) map.get("properties"), result);
		return result;
	}

	private void readProperties(Map<?, ?> map, TrackImpl result) {
		result.setId((String) map.get("id"));
		result.setName((String) map.get("name"));
		result.setModified(new DateTime(map.get("modified")));
		result.setCreated(new DateTime(map.get("created")));
	}

	@Override
	public Track createHyperReferableInstance(String href, String name) {
		return (Track) createHyperReferable(new LazyTrack(href, name), Track.class);
	}

}
