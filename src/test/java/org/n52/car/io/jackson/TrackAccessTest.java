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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.n52.car.io.Access;
import org.n52.car.io.AccessException;
import org.n52.car.io.Connection;
import org.n52.car.io.ConnectionException;
import org.n52.car.io.Preferences;
import org.n52.car.io.jackson.GenericJacksonAccess;
import org.n52.car.io.types.Track;

import static org.hamcrest.CoreMatchers.*;

public class TrackAccessTest {
	
	@Mock
	Connection connection;
	
	@Before
	public void init() throws ConnectionException {
		MockitoAnnotations.initMocks(this);
		String href = "http://giv-car.uni-muenster.de:8080/dev/rest/tracks/51af5861e4b014f3e6f06097";
		Mockito.when(connection.getTracksAsStream()).thenReturn(readTracks());
		Mockito.when(connection.getResource(href)).thenReturn(readReferencedTrack());
	}
	
	private Reader readReferencedTrack() {
		InputStream is = getClass().getResourceAsStream("track.json");
		return new BufferedReader(new InputStreamReader(is));
	}

	private Reader readTracks() {
		InputStream is = getClass().getResourceAsStream("tracks.json");
		return new BufferedReader(new InputStreamReader(is));
	}

	@Test
	public void testAccess() throws AccessException {
		Access ac = new GenericJacksonAccess();
		ac.initialize(connection);
		
		Preferences.getInstance().setAccess(ac);
		
		List<Track> tracks = ac.getTracks();
		
		Assert.assertThat(tracks, is(notNullValue()));
		Assert.assertThat(tracks.size(), is(1));
		Assert.assertThat(tracks.get(0).getName(), is("LazyTrack #1 of testuser1"));
	}


}
