package org.n52.car.io.rest;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.Channels;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.n52.car.io.Connection;
import org.n52.car.io.ConnectionException;
import org.n52.oxf.util.web.HttpClientException;
import org.n52.oxf.util.web.ProxyAwareHttpClient;
import org.n52.oxf.util.web.SimpleHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTConnection implements Connection {

	private static final Logger logger = LoggerFactory
			.getLogger(RESTConnection.class);

	private static final String USERS_URI = "/users";

	private static final String TRACKS_URI = "/tracks";

	private String host;
	private ProxyAwareHttpClient client;

	@Override
	public void initialize(URL host) {
		this.host = parseHost(host);
		this.client = new ProxyAwareHttpClient(new SimpleHttpClient());

		verifyREST();
	}

	private String parseHost(URL h) {
		String string = h.toExternalForm();

		if (string.endsWith("/")) {
			return string.substring(0, string.length() - 1);
		}

		return string;
	}

	private void verifyREST() {
		HttpResponse result = null;
		try {
			result = client.executeGet(host);
			String contentType = result.getEntity().getContentType().getValue();

			if (!contentType.startsWith("application/json")) {
				throw new IllegalStateException(
						String.format(
								"Obvisouly, this is not a %s providing %s. Got ContentType %s",
								"REST server", "JSON", contentType));
			}
		} catch (HttpClientException e) {
			throw new RuntimeException(e);
		} finally {
			if (result != null)
				try {
					EntityUtils.consume(result.getEntity());
				} catch (IOException e) {
					logger.warn(e.getMessage(), e);
				}
		}

	}

	@Override
	public Reader getUsersAsStream() throws ConnectionException {
		return prepareStream(USERS_URI);
	}

	@Override
	public Reader getTracksAsStream() throws ConnectionException {
		return prepareStream(TRACKS_URI);
	}

	private Reader prepareStream(String target) throws ConnectionException {
		String uri = host.concat(target);
		HttpResponse result;
		try {
			result = client.executeGet(uri);
			if (result != null && result.getStatusLine().getStatusCode() <= 300) {
				return Channels.newReader(
						Channels.newChannel(result.getEntity().getContent()),
						resolveEncoding(result));
			} else {
				throw new ConnectionException(String.format(
						"Could not connect to host at %s.", uri));
			}
		} catch (HttpClientException e) {
			throw new ConnectionException(e);
		} catch (IllegalStateException e) {
			throw new ConnectionException(e);
		} catch (IOException e) {
			throw new ConnectionException(e);
		}

	}

	private String resolveEncoding(HttpResponse result) {
		if (result.getEntity().getContentEncoding() != null) {
			return result.getEntity().getContentEncoding().getValue();
		}
		return "UTF-8";
	}

}
