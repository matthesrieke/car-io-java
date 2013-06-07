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
