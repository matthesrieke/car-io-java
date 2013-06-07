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
import java.util.ArrayList;
import java.util.Map;

import org.n52.car.io.types.Track;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class Tracks extends ArrayList<Track> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class Adapter extends BasicTypeAdapter<Tracks> {

		private static final String ID = "id";
		private static final String MODIFIED = "modified";
		private static final String NAME = "name";
		private static final String HREF = "href";

		@Override
		public Tracks read(JsonReader in) throws IOException {
			Tracks result = new Tracks();
			in.beginObject();
			in.nextName();

			in.beginArray();

			Track current = null;
			while (in.peek() != JsonToken.END_DOCUMENT) {
				JsonToken token = in.peek();
				System.out.println(token);

				if (token == JsonToken.BEGIN_OBJECT) {
					current = new Track();
					in.beginObject();
				} else if (token == JsonToken.NAME) {
					setProperty(processNamedValue(token, in), current);
				} else if (token == JsonToken.BEGIN_ARRAY) {
					in.beginArray();
				} else if (token == JsonToken.END_ARRAY) {
					in.endArray();
				} else if (token == JsonToken.END_OBJECT) {
					in.endObject();
					result.add(current);
					current = null;
				}
			}
			
			return result;
		}
		
		private void setProperty(Map<String, ?> processNamedValue, Track track) {
			String key = processNamedValue.keySet().iterator().next();
			
			if (key.equals(ID)) {
				track.setId((String) processNamedValue.get(key));
			}
			else if (key.equals(MODIFIED)) {
				track.setModified((String) processNamedValue.get(key));
			}
			else if (key.equals(NAME)) {
				track.setName((String) processNamedValue.get(key));
			}
			else if (key.equals(HREF)) {
				track.setHref((String) processNamedValue.get(key));
			}
		}


	}


}
