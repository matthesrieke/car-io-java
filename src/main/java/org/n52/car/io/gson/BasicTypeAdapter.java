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
import java.util.Collections;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public abstract class BasicTypeAdapter<T> extends TypeAdapter<T> {

	@Override
	public void write(JsonWriter out, T value) throws IOException {
		//TODO implement in subclasses
		throw new UnsupportedOperationException("Writing JSON is currently no supported.");
	}
	
	protected Map<String, ?> processNamedValue(JsonToken current, JsonReader in) throws IOException {
		String key = in.nextName();
		
		JsonToken valueType = in.peek();
		
		switch (valueType) {
			case STRING:
				return Collections.singletonMap(key, in.nextString());
			case BOOLEAN:
				return Collections.singletonMap(key, in.nextBoolean());
			case NUMBER:
				return Collections.singletonMap(key, in.nextDouble());
			case NULL:
				in.nextNull();
				return Collections.singletonMap(key, null);
			default:
				return Collections.singletonMap(key, null);
		}
		
	}
}
