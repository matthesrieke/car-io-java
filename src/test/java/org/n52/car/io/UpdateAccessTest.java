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

import java.io.BufferedReader;
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
import org.n52.car.io.UpdateAccess;
import org.n52.car.io.jackson.GenericJacksonAccess;

import static org.hamcrest.CoreMatchers.*;

public class UpdateAccessTest {

	@Mock
	Connection connection;
	
	@Before
	public void init() throws ConnectionException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(connection.getTracksAsStream()).thenReturn(readTracks()).
				thenReturn(readUpdatedTracks()).
				thenReturn(readUpdatedTracks());
		
		GenericJacksonAccess ac = new GenericJacksonAccess();
		ac.initialize(this.connection);
		Preferences.getInstance().setAccess(ac);
	}

	@Test
	public void shouldReceiveUpdatedTracks() throws AccessException {
		UpdateAccess ua = new UpdateAccess();
		
		Access ac = Preferences.getInstance().getAccess();
		
		List<?> firstTracks = ua.getNewResources(ac.getTracks());
		
		Assert.assertThat(firstTracks.size(), is(1));
		
		List<?> secondTracks = ua.getNewResources(ac.getTracks());
		
		Assert.assertThat(secondTracks.size(), is(1));
		
		List<?> thirdTracks = ua.getNewResources(ac.getTracks());
		
		Assert.assertThat(thirdTracks.size(), is(0));
	}

	private Reader readTracks() {
		return new BufferedReader(
				new InputStreamReader(
						getClass().getResourceAsStream("jackson/tracks.json")));
	}
	
	private Reader readUpdatedTracks() {
		return new BufferedReader(
				new InputStreamReader(
						getClass().getResourceAsStream("jackson/tracks-updated.json")));
	}
	
}
