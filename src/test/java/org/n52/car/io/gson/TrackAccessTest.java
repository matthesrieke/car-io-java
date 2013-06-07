package org.n52.car.io.gson;

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
import org.n52.car.io.types.Track;

public class TrackAccessTest {
	
	@Mock
	Connection connection;
	
	@Before
	public void init() throws ConnectionException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(connection.getTracksAsStream()).thenReturn(readTracks());
	}
	
	private Reader readTracks() {
		InputStream is = getClass().getResourceAsStream("tracks.json");
		return new BufferedReader(new InputStreamReader(is));
	}

	@Test
	public void testAccess() throws AccessException {
		Access ac = new GSONAccess();
		ac.initialize(connection);
		
		List<Track> tracks = ac.getTracks();
		
		Assert.assertTrue("tracks are empty or null", tracks != null && !tracks.isEmpty());
	}


}
