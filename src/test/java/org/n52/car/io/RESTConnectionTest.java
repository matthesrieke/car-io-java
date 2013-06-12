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

import java.io.IOException;
import java.io.Reader;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.n52.car.io.Connection;
import org.n52.car.io.Preferences;
import org.n52.car.io.rest.RESTConnection;
import org.n52.oxf.util.web.HttpClient;
import org.n52.oxf.util.web.HttpClientException;
import org.n52.oxf.util.web.ProxyAwareHttpClient;
import org.n52.oxf.util.web.SimpleHttpClient;

import static org.hamcrest.CoreMatchers.*;

public class RESTConnectionTest {

	
	@Before
	public void ensureConnectionToServer() throws HttpClientException {
		HttpClient client = new ProxyAwareHttpClient(new SimpleHttpClient());
		HttpResponse response = client.executeGet(Preferences.getInstance().getServer().toExternalForm());
		
		Assume.assumeThat(response.getStatusLine().getStatusCode(), is(200));
	}
	
	@Test
	public void shouldRetrieveData() throws IOException {
		Connection conn = new RESTConnection();
		conn.initialize(Preferences.getInstance().getServer());
		
		assertReader(conn.getTracksAsStream());
		assertReader(conn.getUsersAsStream());
	}

	private void assertReader(Reader data) throws IOException {
		Assert.assertThat(data, is(notNullValue()));
		Assert.assertThat(data.read(), is(not(-1)));
		
		data.close();		
	}
	
}
